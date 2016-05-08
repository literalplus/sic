package li.l1t.sic.service;

import li.l1t.sic.config.SicConfiguration;
import li.l1t.sic.exception.JsonPropagatingException;
import li.l1t.sic.model.User;
import li.l1t.sic.model.UserAuthority;
import li.l1t.sic.model.repo.AuthorityRepository;
import li.l1t.sic.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.Validate;

import java.security.Principal;

/**
 * Service handling principal registration.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-21
 */
@Service
public class UserService {
    private UserRepository userRepository;
    private SicConfiguration sicConfiguration;
    private AuthorityRepository authorityRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository, SicConfiguration sicConfiguration,
                       AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.sicConfiguration = sicConfiguration;
        this.authorityRepository = authorityRepository;
    }

    public User createUser(String username, String password, String registerSecret) {
        if (!sicConfiguration.getRegisterSecret().equalsIgnoreCase(registerSecret)) {
            throw new JsonPropagatingException("Falscher Geheimcode!");
        }

        if (userRepository.findByName(username) != null) {
            throw new JsonPropagatingException("Benutzername schon vergeben!");
        }

        User user = userRepository.save(new User(username, passwordEncoder.encode(password), true));
        authorityRepository.save(new UserAuthority(user, "default"));
        return user;
    }

    public User fromPrincipal(Principal principal) {
        Validate.notNull(principal, "principal");
        User user = fromPrincipalNullable(principal);
        Validate.notNull(user, "No user found for name " + principal.getName());
        return user;
    }

    public User fromPrincipalNullable(Principal principal) {
        if(principal == null) {
            return null;
        }
        return userRepository.findByName(principal.getName());
    }

    public void setSeenVideo(User user, boolean seenVideo) {
        user.setSeenVideo(seenVideo);
        userRepository.save(user);
    }
}
