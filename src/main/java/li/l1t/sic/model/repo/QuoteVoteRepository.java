package li.l1t.sic.model.repo;

import li.l1t.sic.model.QuoteVote;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for votes on quotes.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-05-07
 */
public interface QuoteVoteRepository extends CrudRepository<QuoteVote, QuoteVote.QuoteVoteId> {
}
