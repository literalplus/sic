package li.l1t.sic.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-21
 */
@Entity
@Table(name = "sic_user")
public class User {
    @Valid
    @Length(min = 2, max = 20)
    @Column(name = "username")
    @Id
    private String name;

    @Valid
    @Length(min = 60, max = 60)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserAuthority> authorities = new ArrayList<>();

    @Column(name = "seen_video")
    private boolean seenVideo = false;

    @Column
    private boolean enabled;

    public User() {}

    public User(String name, String password, boolean enabled) {
        this.name = name;
        this.password = password;
        this.enabled = enabled;
    }

    public List<UserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<UserAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the hashed password chosen by this user
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return whether this user has seen an "introductory" video "explaining the app" (a troll)
     */
    public boolean hasSeenVideo() {
        return seenVideo;
    }

    public void setSeenVideo(boolean seenVideo) {
        this.seenVideo = seenVideo;
    }
}
