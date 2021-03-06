package li.l1t.sic.model.dto;

/**
 * Data Transfer Object for a person sic quotes are associated with. Note that these are referred to as people in plural.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
public class PersonDto {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
