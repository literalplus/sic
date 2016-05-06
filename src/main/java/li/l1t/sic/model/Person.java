package li.l1t.sic.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 * Represents a person sic quotes are associated with. Note that these are referred to as people in plural.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
@Entity
@Table(name = "sic_person")
public class Person extends IdentifiableEntity {
    @Valid
    @Length(max = 100)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
