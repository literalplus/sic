package li.l1t.sic;

import li.l1t.sic.config.SicConfiguration;
import li.l1t.sic.model.Person;
import li.l1t.sic.model.Quote;
import li.l1t.sic.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for serving sic's main template, aka, the AngularJS client.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-06
 */
@Controller
public class LandingController {
    @Autowired
    private SicConfiguration configuration;
    @Autowired
    private QuoteService quoteService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String main(Model model) {
        model.addAttribute("footerText", configuration.getFooterText());
        return "main";
    }

    @RequestMapping(value = "/secure/print/quote/by/id/{id}", method = RequestMethod.GET)
    public String teacherPrint(Model model, @PathVariable("id") int id) {
        Quote quote = quoteService.findById(id);
        model.addAttribute("quote", quote);
        if (quote != null){
            Person person = quote.getPerson();
            model.addAttribute("person", person);
        }
        return "quote-print";
    }

    @RequestMapping(value = "/secure/print/quotes/by/ids/{ids}", method = RequestMethod.GET)
    public String teacherPrint(Model model, @PathVariable int[] ids) {
        List<Quote> quotes = Arrays.stream(ids)
                .mapToObj(quoteService::findById)
                .collect(Collectors.toList());

        model.addAttribute("quotes", quotes);
        return "quotes-print";
    }
}
