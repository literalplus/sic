package li.l1t.sic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

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
}
