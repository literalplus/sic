package li.l1t.sic.exception;

/**
 * Thrown if a person with an unknown id is requested.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-03-06
 */
public class PersonNotFoundException extends JsonPropagatingException {
    public PersonNotFoundException(long requestedId) {
        super("Unknown person with id " + requestedId);
    }
}
