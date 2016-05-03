package li.l1t.sic.model.repo;

import li.l1t.sic.model.UserAuthority;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for authorities.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-22
 */

public interface AuthorityRepository extends CrudRepository<UserAuthority, Integer> {
}
