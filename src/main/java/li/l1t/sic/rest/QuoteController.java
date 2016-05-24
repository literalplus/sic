package li.l1t.sic.rest;

import li.l1t.sic.model.GuestUser;
import li.l1t.sic.model.Person;
import li.l1t.sic.model.Quote;
import li.l1t.sic.model.dto.QuoteDto;
import li.l1t.sic.model.dto.QuotesDto;
import li.l1t.sic.service.PersonService;
import li.l1t.sic.service.QuoteService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.dropwizard.DropwizardMetricServices;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Controller providing a REST API for sic quotes.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
@RestController
public class QuoteController {
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private PersonService personService;
    @Autowired
    private DropwizardMetricServices metricServices;

    @RequestMapping("/api/quote/by/person/{personId}")
    public QuotesDto byPersonId(@PathVariable int personId,
                                @RequestParam(defaultValue = "0") int limit,
                                Principal user) {
        Person person = personService.getById(personId);

        List<QuoteDto> fields = StreamSupport.stream(quoteService.getAllQuotesByPerson(person).spliterator(), false)
                .map((quote) -> quoteService.toDto(quote, user))
                .collect(Collectors.toList());

        if (limit > 0){ //If we have a rating, apply it; Using streams for easier API (no bounds checking necessary)
            fields = fields.stream().limit(limit).collect(Collectors.toList());
        }

        return new QuotesDto(personService.toDto(person), fields);
    }

    @RequestMapping("/api/quote/latest/page/{pageId}")
    public List<QuoteDto> latest(@PathVariable int pageId,
                                 @RequestParam(defaultValue = "5") int pageSize) {
        Validate.exclusiveBetween(4, 51, pageSize, "invalid pageSize, must be 4<x<51");
        return quoteService.findLatest(pageId, pageSize).stream()
                .map(quoteService::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping("/api/quote/best/gt/{rating}/page/{pageId}")
    public List<QuoteDto> best(@PathVariable int pageId,
                               @RequestParam(defaultValue = "5") int pageSize,
                               @PathVariable int rating) {
        Validate.exclusiveBetween(4, 51, pageSize, "invalid pageSize, must be 4<x<51");
        return quoteService.findAllWithVoteCountGreaterThan(rating, pageId, pageSize).stream()
                .map(quoteService::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/api/quote/save", method = RequestMethod.POST)
    public QuoteDto save(@RequestBody QuoteDto quote, Principal user) {
        GuestUser.validateNotGuest(user);
        return quoteService.toDto(quoteService.save(quote, user), user);
    }

    @RequestMapping(value = "/api/quote/delete", method = RequestMethod.POST)
    public QuoteDto deleteById(@RequestBody QuoteDto quoteDto, Principal user) {
        GuestUser.validateNotGuest(user);
        Quote quote = quoteService.toEntity(quoteDto);
        quoteService.delete(quote);
        return quoteService.toDto(quote, user);
    }

    @RequestMapping(value = "/api/quotes/count")
    public Map<String, Long> quotesCount() {
        long quoteCount = quoteService.getQuoteCount();
        metricServices.submit("auth.login.guest", quoteCount);
        return Collections.singletonMap("count", quoteCount);
    }
}
