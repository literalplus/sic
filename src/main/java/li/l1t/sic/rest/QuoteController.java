package li.l1t.sic.rest;

import li.l1t.sic.model.GuestUser;
import li.l1t.sic.model.dto.QuoteDto;
import li.l1t.sic.model.dto.QuotesDto;
import li.l1t.sic.service.QuoteDtoService;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Controller providing a REST API for sic quotes.
 *
 * @since 2016-02-14
 */
@RestController
public class QuoteController {
    private final QuoteDtoService quoteDtoService;

    public QuoteController(QuoteDtoService quoteDtoService) {
        this.quoteDtoService = quoteDtoService;
    }

    @GetMapping("/api/quote/by/person/{personId}")
    public QuotesDto byPersonId(
            @PathVariable int personId,
            @RequestParam(defaultValue = "0") int limit,
            Principal user
    ) {
        var quotes = quoteDtoService.getForPerson(personId, user);

        if (limit > 0) {
            quotes.setQuotes(
                    quotes.getQuotes().stream()
                            .limit(limit).toList()
            );
        }

        return quotes;
    }

    @GetMapping("/api/quote/latest/page/{pageId}")
    public List<QuoteDto> latest(
            @PathVariable int pageId,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        Validate.exclusiveBetween(4, 51, pageSize, "invalid pageSize, must be 4<x<51");
        var pageable = PageRequest.of(pageId, pageSize);
        return quoteDtoService.getLatest(pageable);
    }

    @GetMapping("/api/quote/best/gt/{rating}/page/{pageId}")
    public List<QuoteDto> best(
            @PathVariable int pageId,
            @RequestParam(defaultValue = "5") int pageSize,
            @PathVariable int rating
    ) {
        Validate.exclusiveBetween(4, 51, pageSize, "invalid pageSize, must be 4<x<51");
        var pageable = PageRequest.of(pageId, pageSize);
        return quoteDtoService.getBest(rating, pageable);
    }

    @PostMapping("/api/quote/save")
    public QuoteDto save(@RequestBody QuoteDto quote, Principal user) {
        GuestUser.validateNotGuest(user);
        return quoteDtoService.save(quote, user);
    }

    @PostMapping("/api/quote/delete")
    public void deleteById(@RequestBody QuoteDto quoteDto, Principal user) {
        GuestUser.validateNotGuest(user);
        quoteDtoService.deleteById(quoteDto);
    }

    @GetMapping(value = "/api/quotes/count")
    public Map<String, Long> quotesCount() {
        long quoteCount = quoteDtoService.getQuotesCount();
        return Map.of("count", quoteCount);
    }
}
