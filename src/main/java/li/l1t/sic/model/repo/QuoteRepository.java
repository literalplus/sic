package li.l1t.sic.model.repo;

import li.l1t.sic.model.Person;
import li.l1t.sic.model.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for sic quotes.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
@Repository
public interface QuoteRepository extends CrudRepository<Quote, Integer> {
    List<Quote> findAllByPersonOrderByVoteCountDesc(Person person);
    List<Quote> findByVoteCountGreaterThan(int parameter);
    List<Quote> findAllByOrderByLastUpdatedDesc(Pageable pageable);
}
