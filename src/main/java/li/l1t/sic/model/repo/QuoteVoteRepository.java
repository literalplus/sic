package li.l1t.sic.model.repo;

import li.l1t.sic.model.Quote;
import li.l1t.sic.model.QuoteVote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for votes on quotes.
 *
 * @since 2016-05-07
 */
@Repository
public interface QuoteVoteRepository extends CrudRepository<QuoteVote, QuoteVote.QuoteVoteId> {
    List<QuoteVote> findAllByIdQuote(Quote quote);
    void deleteByIdQuote(Quote quote);
}
