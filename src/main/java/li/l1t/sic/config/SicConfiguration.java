package li.l1t.sic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores configuration values for the whole application.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-21
 */
@Service
@ConfigurationProperties(prefix = "sic")
public class SicConfiguration {
    private String registerSecret;
    private String footerText;
    private String videoUrl;
    private boolean enablePranks;
    private String guestCode;
    private List<NavbarLink> navbarLinks;
    private boolean statsdEnabled;
    private String statsdHost;
    private int statsdPort;

    public String getRegisterSecret() {
        return registerSecret;
    }

    public void setRegisterSecret(String registerSecret) {
        this.registerSecret = registerSecret;
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public boolean isEnablePranks() {
        return enablePranks;
    }

    public void setEnablePranks(boolean enablePranks) {
        this.enablePranks = enablePranks;
    }

    public String getGuestCode() {
        return guestCode;
    }

    public void setGuestCode(String guestCode) {
        this.guestCode = guestCode;
    }

    public List<NavbarLink> getNavbarLinks() {
        if(navbarLinks == null) {
            navbarLinks = new ArrayList<>();
        }
        return navbarLinks;
    }

    public void setNavbarLinks(List<NavbarLink> navbarLinks) {
        this.navbarLinks = navbarLinks;
    }

    public boolean isStatsdEnabled() {
        return statsdEnabled;
    }

    public void setStatsdEnabled(boolean statsdEnabled) {
        this.statsdEnabled = statsdEnabled;
    }

    public String getStatsdHost() {
        return statsdHost;
    }

    public void setStatsdHost(String statsdHost) {
        this.statsdHost = statsdHost;
    }

    public int getStatsdPort() {
        return statsdPort;
    }

    public void setStatsdPort(int statsdPort) {
        this.statsdPort = statsdPort;
    }
}
