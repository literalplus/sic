package li.l1t.sic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Controls displaying of error messages.
 *
 * @since 7.4.16
 */
@Controller
public class SicErrorController implements ErrorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SicErrorController.class);

    @RequestMapping("/error")
    public String errorHtml(HttpServletRequest request, Model model) {
        Object errCodeObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int errorCode = errCodeObj == null ? 0 : (int) errCodeObj;
        model.addAttribute("errorCode", errCodeObj);
        Object obj = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (obj instanceof Throwable throwable) {
            LOGGER.info(
                    "User-facing exception: {} // {} - {}",
                    request.getRequestURI(), throwable.getClass(), throwable.getMessage()
            );
            LOGGER.debug("Stack trace", throwable);
            model.addAttribute("errorType", throwable.getClass().getSimpleName());
        }

        return switch (errorCode) {
            case 401 -> "error/401";
            default -> "error/generic";
        };
    }
}
