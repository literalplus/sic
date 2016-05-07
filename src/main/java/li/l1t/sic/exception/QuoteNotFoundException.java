package li.l1t.sic.exception;

/**
 * Thrown if a field with an unknown id is requested.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-03-06
 */
public class QuoteNotFoundException extends JsonPropagatingException {
    public QuoteNotFoundException(int requestedId) {
        super("Unknown field with id " + requestedId);
    }
}
