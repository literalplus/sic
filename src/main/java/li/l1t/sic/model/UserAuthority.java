package li.l1t.sic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 * Represents an authority assigned to a user. Necessary because Spring doesn't accept users
 * without authorities as existing for some reason.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-22
 */
@Entity
@Table(name = "sic_authority")
public class UserAuthority {
    @Id
    private int id;

    @JoinColumn(name = "username")
    @ManyToOne
    private User user;

    private String authority;

    public UserAuthority(User user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
