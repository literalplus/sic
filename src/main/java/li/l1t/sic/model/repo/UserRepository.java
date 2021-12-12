package li.l1t.sic.model.repo;

import li.l1t.sic.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for user data.
 *
 * @since 2016-02-21
 */
@Repository
public interface UserRepository extends JpaRepository<RegisteredUser, String> {
    Optional<RegisteredUser> findByName(String name);
}
