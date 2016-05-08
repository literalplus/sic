package li.l1t.sic.rest;

import li.l1t.sic.config.SicConfiguration;
import li.l1t.sic.model.User;
import li.l1t.sic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls pranks and trolls hidden across the application.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-05-08
 */
@RestController
public class PrankController {
    @Autowired
    private SicConfiguration configuration;
    @Autowired
    private UserService userService;

    @RequestMapping("/api/prank/video/url")
    public Map<String, Object> checkVideoPrank(Principal principal) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.fromPrincipalNullable(principal);
        boolean showVideo = false;
        if (configuration.isEnablePranks() && user != null && !user.hasSeenVideo()){
            result.put("url",
                    "https://www.youtube-nocookie.com/embed/" +
                            configuration.getVideoUrl() +
                            "?rel=0&controls=0&showinfo=0&autoplay=1"
            );
            showVideo = true;
            userService.setSeenVideo(user, true);
        }
        result.put("showVideo", showVideo);
        return result;
    }

    @RequestMapping("/api/prank/video/reset")
    public String resetVideoPrank(Principal principal) {
        User user = userService.fromPrincipal(principal);
        userService.setSeenVideo(user, false);
        return "You asked for it.";
    }
}
