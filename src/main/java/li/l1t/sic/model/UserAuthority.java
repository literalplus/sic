package li.l1t.sic.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents an authority assigned to a user. Necessary because Spring doesn't accept users
 * without authorities as existing for some reason.
 *
 * @since 2016-02-22
 */
@Entity
@Table(name = "sic_authority")
public class UserAuthority implements GrantedAuthority {
    @Id
    private int id;

    @JoinColumn(name = "username")
    @ManyToOne
    private RegisteredUser user;

    @Column
    private String authority;

    public UserAuthority() {
    }

    public UserAuthority(RegisteredUser user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public String getName() {
        return getUser().getName();
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
