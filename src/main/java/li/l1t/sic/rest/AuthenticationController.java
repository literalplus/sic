package li.l1t.sic.rest;

import li.l1t.sic.config.SicConfiguration;
import li.l1t.sic.exception.AuthException;
import li.l1t.sic.model.dto.AuthenticationDto;
import li.l1t.sic.security.auth.TokenHandler;
import li.l1t.sic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.dropwizard.DropwizardMetricServices;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

/**
 * REST Controller providing an API for authentication. That API supports logging in, logging out and checking login
 * status.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-09
 */
@RestController
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SicConfiguration configuration;
    @Autowired
    private DropwizardMetricServices metricServices;

    @RequestMapping("/auth/status")
    public Principal user(Principal user) { //Spring throws a 401 if not logged in (explicitly only authed users in security config)
        return user;
    }

    @RequestMapping("/auth/token")
    public Map<String, String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public Map<String, Boolean> register(@RequestBody AuthenticationDto request) {
        try {
            userService.createUser(request.getUsername(), request.getPassword(), request.getRegisterToken());
        } catch (Exception e) {
            metricServices.increment("auth.register.failure");
            throw e;
        }
        metricServices.increment("auth.register.success");
        return Collections.singletonMap("success", true);
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public Map<String, String> login(@RequestBody AuthenticationDto request) {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Principal user;
        try {
            user = authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            metricServices.increment("auth.login.failure.credentials");
            throw new AuthException("Benutzername und/oder Passwort falsch!");
        } catch (AuthenticationException e) {
            metricServices.increment("auth.login.failure.auth");
            throw new AuthException("Fehler beim Login: " + e.getClass().getSimpleName());
        } catch (Exception e) {
            metricServices.increment("auth.login.failure.misc");
            throw e;
        }
        String token = tokenHandler.createTokenForUser(user);
        metricServices.increment("auth.login.success");
        return Collections.singletonMap("token", token);
    }

    @RequestMapping(value = "/auth/guest", method = RequestMethod.POST)
    public Map<String, String> guestAuth(@RequestBody AuthenticationDto request) {
        if (configuration.getGuestCode().equalsIgnoreCase(request.getPassword())) {
            metricServices.increment("auth.login.guest.success");
            return Collections.singletonMap("token", tokenHandler.createGuestToken());
        } else if(configuration.getRegisterSecret().equalsIgnoreCase(request.getPassword())) {
            throw new AuthException("Du bist zu Größerem bestimmt! Das ist ein VIP-Code, " +
                    "du kannst dir damit rechts einen Account anlegen.");
        } else {
            metricServices.increment("auth.login.guest.failure");
            throw new AuthException("Invalider Zugangscode!");
        }
    }
}
