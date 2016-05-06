package li.l1t.sic.rest;

import li.l1t.sic.model.Person;
import li.l1t.sic.model.Quote;
import li.l1t.sic.model.dto.QuoteDto;
import li.l1t.sic.model.dto.QuotesDto;
import li.l1t.sic.service.QuoteService;
import li.l1t.sic.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
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
    private final QuoteService quoteService;
    private final PersonService personService;

    @Autowired
    public QuoteController(QuoteService quoteService, PersonService personService) {
        this.quoteService = quoteService;
        this.personService = personService;
    }

    @RequestMapping("/api/quote/by/person/{personId}")
    public QuotesDto byPersonId(@PathVariable("personId") int personId,
                                 @RequestParam(name = "limit", defaultValue = "0") int limit,
                                Principal user) {
        Person person = personService.getById(personId);

        List<QuoteDto> fields = StreamSupport.stream(quoteService.getAllQuotesByPerson(person).spliterator(), false)
                .map((quote) -> quoteService.toDto(quote, user))
                .collect(Collectors.toList());

        if(limit > 0) { //If we have a limit, apply it; Using streams for easier API (no bounds checking necessary)
            fields = fields.stream().limit(limit).collect(Collectors.toList());
        }

        return new QuotesDto(personService.toDto(person), fields);
    }

    @RequestMapping(value = "/api/quote/save", method = RequestMethod.POST)
    public QuoteDto save(@RequestBody QuoteDto quote, Principal user) {
        return quoteService.toDto(quoteService.save(quote, user), user);
    }

    @RequestMapping(value = "/api/quote/delete", method = RequestMethod.POST)
    public QuoteDto deleteById(@RequestBody QuoteDto quoteDto, Principal user) {
        Quote quote = quoteService.toEntity(quoteDto);
        quoteService.delete(quote);
        return quoteService.toDto(quote, user);
    }
}
