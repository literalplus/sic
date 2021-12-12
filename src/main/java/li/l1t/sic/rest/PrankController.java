package li.l1t.sic.rest;

import li.l1t.sic.config.SicProperties;
import li.l1t.sic.model.RegisteredUser;
import li.l1t.sic.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls pranks and trolls hidden across the application.
 *
 * @since 2016-05-08
 */
@RestController
public class PrankController {
    private final SicProperties configuration;
    private final UserService userService;

    public PrankController(SicProperties configuration, UserService userService) {
        this.configuration = configuration;
        this.userService = userService;
    }

    @GetMapping("/api/prank/video/url")
    public Map<String, Object> checkVideoPrank(Principal principal) {
        Map<String, Object> result = new HashMap<>();
        RegisteredUser user = userService.fromRegisteredNullable(principal);
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

    @GetMapping("/api/prank/video/reset")
    public String resetVideoPrank(Principal principal) {
        RegisteredUser user = userService.fromRegistered(principal);
        userService.setSeenVideo(user, false);
        return "You asked for it.";
    }
}
