package li.l1t.sic.rest;

import li.l1t.sic.model.GuestUser;
import li.l1t.sic.model.Quote;
import li.l1t.sic.service.QuoteService;
import li.l1t.sic.service.QuoteVoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Controls REST access to quote votes.
 *
 * @since 2016-05-07
 */
@RestController
public class QuoteVoteController {
    private final QuoteVoteService quoteVoteService;
    private final QuoteService quoteService;

    public QuoteVoteController(QuoteVoteService quoteVoteService, QuoteService quoteService) {
        this.quoteVoteService = quoteVoteService;
        this.quoteService = quoteService;
    }


    @GetMapping(value = "/api/quote/by/id/{id}/vote/up")
    public int upVote(@PathVariable("id") int quoteId, Principal user) {
        GuestUser.validateNotGuest(user);
        Quote quote = quoteService.getById(quoteId);
        quoteVoteService.setVote(quote, user, true);
        return quote.getVoteCount();
    }

    @GetMapping(value = "/api/quote/by/id/{id}/vote/down")
    public int downVote(@PathVariable("id") int quoteId, Principal user) {
        GuestUser.validateNotGuest(user);
        Quote quote = quoteService.getById(quoteId);
        quoteVoteService.setVote(quote, user, false);
        return quote.getVoteCount();
    }

    @GetMapping(value = "/api/quote/by/id/{id}/vote/reset")
    public int resetVote(@PathVariable("id") int quoteId, Principal user) {
        GuestUser.validateNotGuest(user);
        Quote quote = quoteService.getById(quoteId);
        quoteVoteService.unsetVote(quote, user);
        return quote.getVoteCount();
    }
}
