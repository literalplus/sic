package li.l1t.sic.service;

import li.l1t.sic.config.SicConfiguration;
import li.l1t.sic.exception.JsonPropagatingException;
import li.l1t.sic.model.GuestUser;
import li.l1t.sic.model.RegisteredUser;
import li.l1t.sic.model.User;
import li.l1t.sic.model.UserAuthority;
import li.l1t.sic.model.repo.AuthorityRepository;
import li.l1t.sic.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public RegisteredUser createUser(String username, String password, String registerSecret) {
        if(username.startsWith("*")) { //for guest code
            throw new JsonPropagatingException("Sorry, dein Benutzername darf nicht mit einem Sternchen beginnen.");
        }
        if (!sicConfiguration.getRegisterSecret().equalsIgnoreCase(registerSecret)){
            if(sicConfiguration.getGuestCode().equalsIgnoreCase(registerSecret)) {
                throw new JsonPropagatingException("Pssst, falsches Formular! Das ist ein Zugangscode, kein Geheimcode.");
            }
            throw new JsonPropagatingException("Falscher Geheimcode!");
        }

        if (userRepository.findByName(username) != null){
            throw new JsonPropagatingException("Benutzername schon vergeben!");
        }

        RegisteredUser user = userRepository.save(
                new RegisteredUser(username, passwordEncoder.encode(password), true)
        );
        authorityRepository.save(new UserAuthority(user, "default"));
        return user;
    }

    /**
     * Gets a User object from a principal. If the principal is an authenticated
     * guest, a {@link li.l1t.sic.model.GuestUser} is returned.
     *
     * @param principal the principal to convert
     * @return a user object
     * @throws UsernameNotFoundException if no user with that name exists
     */
    public User from(Principal principal) {
        if (GuestUser.isGuest(principal)){
            return GuestUser.INSTANCE;
        } else {
            return fromRegistered(principal);
        }
    }

    /**
     * Gets a registered user from a principal. Does not take into account authenticated
     * guests.
     *
     * @param principal the principal to convert
     * @return a user object
     * @throws UsernameNotFoundException if no user with that name exists
     */
    public RegisteredUser fromRegistered(Principal principal) {
        Validate.notNull(principal, "principal");
        RegisteredUser user = fromRegisteredNullable(principal);
        if (user == null){
            throw new UsernameNotFoundException(
                    "No user found for name " + principal.getName()
            );
        }
        return user;
    }

    /**
     * Gets a registered user from a principal. Does not take into account authenticated
     * guests.
     *
     * @param principal the principal to convert
     * @return a user object, or null if none found
     */
    public RegisteredUser fromRegisteredNullable(Principal principal) {
        if (principal == null){
            return null;
        }
        return userRepository.findByName(principal.getName());
    }

    public void setSeenVideo(RegisteredUser user, boolean seenVideo) {
        user.setSeenVideo(seenVideo);
        userRepository.save(user);
    }
}
