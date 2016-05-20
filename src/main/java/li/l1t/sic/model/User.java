package li.l1t.sic.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Represents a user of the application which may be registered or an authenticated guest.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-05-20
 */
public interface User {
    /**
     * @return the collection of authorities this user has been granted
     */
    Collection<? extends GrantedAuthority> getAuthorities();

    /**
     * @return this user's name, if they are registered
     */
    String getName();

    /**
     * @return the hashed password chosen by this user, if they are registered, null otherwise
     */
    String getPassword();

    /**
     * @return whether this user's account is enabled
     */
    boolean isEnabled();

    /**
     * @return whether this user has seen an "introductory" video "explaining the app" (a troll)
     */
    boolean hasSeenVideo();

    /**
     * @return whether this user is registered, or an authenticated guest
     */
    boolean isRegistered();
}
