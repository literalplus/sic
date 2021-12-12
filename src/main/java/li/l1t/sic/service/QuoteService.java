package li.l1t.sic.service;

import li.l1t.sic.exception.QuoteNotFoundException;
import li.l1t.sic.model.Person;
import li.l1t.sic.model.Quote;
import li.l1t.sic.model.repo.QuoteRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * Service handling sic quotes, specifically finding quotes by person, adding, creating and deleting quotes.
 *
 * @since 2016-02-14
 */
@Service
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final UserService userService;

    public QuoteService(
            QuoteRepository quoteRepository, UserService userService
    ) {
        this.quoteRepository = quoteRepository;
        this.userService = userService;
    }

    public List<Quote> getAllQuotesByPerson(Person person) {
        return quoteRepository.findAllByPersonOrderByVoteCountDesc(person);
    }

    public Quote getById(int quoteId) {
        return quoteRepository.findById(quoteId)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new QuoteNotFoundException(quoteId));
    }

    public Quote getOrCreate(int quoteId, Person person, Principal user) {
        return quoteRepository.findById(quoteId)
                .orElseGet(() -> {
                    var newQuote = new Quote(person);
                    if (user != null) {
                        newQuote.setCreator(userService.fromRegistered(user));
                    }
                    return newQuote;
                });
    }

    public Quote save(Quote quote) {
        return quoteRepository.save(quote);
    }

    public Optional<Quote> deleteById(int quoteId) {
        var existingQuote = quoteRepository.findById(quoteId);
        existingQuote.ifPresent(quote -> {
            quote.setDeleted(true);
            save(quote);
        });
        return existingQuote;
    }

    public List<Quote> findAllWithVoteCountGreaterThan(int voteCount) {
        return quoteRepository.findByVoteCountGreaterThan(voteCount);
    }

    public List<Quote> findAllWithVoteCountGreaterThan(int voteCount, Pageable pageable) {
        return quoteRepository.findByVoteCountGreaterThan(
                voteCount, pageable
        );
    }

    public long getQuoteCount() {
        return quoteRepository.count();
    }

    public List<Quote> findLatest(Pageable pageable) {
        return quoteRepository.findAllByOrderByCreationDateDesc(pageable);
    }
}
