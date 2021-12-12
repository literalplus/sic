package li.l1t.sic.model.repo;

import li.l1t.sic.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for person data.
 *
 * @since 2016-02-14
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByName(String name);

    @Query(
            value = "SELECT * FROM sic_person WHERE name LIKE CONCAT('%', :nameSearch, '%')",
            nativeQuery = true
    )
    Person findByNameFuzzy(String nameSearch);
}
