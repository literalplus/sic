package li.l1t.sic;

import li.l1t.sic.config.SicProperties;
import li.l1t.sic.model.Person;
import li.l1t.sic.model.Quote;
import li.l1t.sic.service.QuoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for serving sic's main template, aka, the AngularJS client.
 *
 * @since 2016-02-06
 */
@Controller
public class LandingController {
    private final SicProperties configuration;
    private final QuoteService quoteService;

    public LandingController(SicProperties configuration, QuoteService quoteService) {
        this.configuration = configuration;
        this.quoteService = quoteService;
    }

    @GetMapping("")
    public String main(Model model) {
        model.addAttribute("footerText", configuration.getFooterText());
        model.addAttribute("navbarLinks", configuration.getNavbarLinks());
        return "main";
    }

    @GetMapping("/secure/print/quote/by/id/{id}")
    public String quotePrintSingle(Model model, @PathVariable("id") int id) {
        Quote quote = quoteService.getById(id);
        model.addAttribute("quote", quote);
        if (quote != null) {
            Person person = quote.getPerson();
            model.addAttribute("person", person);
        }
        return "quote-print";
    }

    @GetMapping("/secure/print/quotes/by/ids/{ids}")
    public String quotesPrintById(Model model, @PathVariable int[] ids) {
        List<Quote> quotes = Arrays.stream(ids)
                .mapToObj(quoteService::getById)
                .collect(Collectors.toList());

        model.addAttribute("quotes", quotes);
        return "quotes-print";
    }

    @GetMapping(value = "/secure/print/quotes/by/rating/{rating}")
    public String quotesPrintByRating(Model model, @PathVariable int rating) {
        List<Quote> quotes = quoteService.findAllWithVoteCountGreaterThan(rating);
        model.addAttribute("quotes", quotes);
        return "quotes-print";
    }
}
