package li.l1t.sic.model.repo;

import li.l1t.sic.model.Person;
import li.l1t.sic.model.Quote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for sic quotes.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
@org.springframework.stereotype.Repository
public interface QuoteRepository extends CrudRepository<Quote, Integer> {
    List<Quote> findAllByPerson(Person person);
}
