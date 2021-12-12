package li.l1t.sic.service;

import li.l1t.sic.exception.JsonPropagatingException;
import li.l1t.sic.model.GuestUser;
import li.l1t.sic.model.Quote;
import li.l1t.sic.model.QuoteVote;
import li.l1t.sic.model.repo.QuoteVoteRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * Service managing votes on quotes.
 *
 * @since 2016-05-07
 */
@Service
public class QuoteVoteService {
    private final QuoteVoteRepository repository;
    private final UserService userService;
    private final QuoteService quoteService;

    public QuoteVoteService(QuoteVoteRepository repository, UserService userService, QuoteService quoteService) {
        this.repository = repository;
        this.userService = userService;
        this.quoteService = quoteService;
    }

    public int findVoteStatus(Quote quote, Principal principal) {
        if (GuestUser.isGuest(principal)) {
            return 0;
        }
        QuoteVote vote = findVote(quote, principal);
        if (vote == null) {
            return 0;
        } else {
            return vote.getModifier();
        }
    }

    public QuoteVote findVote(Quote quote, Principal principal) {
        return repository.findById(new QuoteVote.QuoteVoteId(
                userService.fromRegistered(principal),
                quote
        )).orElse(null);
    }

    public void setVote(Quote quote, Principal principal, boolean isUpvote) {
        QuoteVote vote = findVote(quote, principal);
        if (vote == null) {
            vote = new QuoteVote(quote, userService.fromRegistered(principal), isUpvote);
        } else if (vote.isUpvote() == isUpvote) {
            throw new JsonPropagatingException("Already voted on that quote!");
        }
        vote.setUpvote(isUpvote);
        repository.save(vote);
        recalculateVoteCount(quote);
    }

    public void unsetVote(Quote quote, Principal principal) {
        QuoteVote vote = findVote(quote, principal);
        if (vote != null) {
            repository.delete(vote);
            recalculateVoteCount(quote);
        } //else, no-op
    }

    public void recalculateVoteCount(Quote quote) {
        List<QuoteVote> votes = repository.findAllByIdQuote(quote);
        int voteCount = votes.stream()
                .mapToInt(QuoteVote::getModifier)
                .sum();
        quote.setVoteCount(voteCount);
        quoteService.save(quote);
    }

    public void deleteAllVotesOn(Quote quote) {
        repository.deleteByIdQuote(quote);
    }
}
