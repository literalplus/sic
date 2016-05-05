package li.l1t.sic.model.dto;

import java.util.List;

/**
 * Data Transfer Object for representing sic quotes by person, with count.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 18.2.16
 */
public class QuotesDto {
    private PersonDto person;
    private List<QuoteDto> quotes;

    public QuotesDto(PersonDto person, List<QuoteDto> quotes) {
        this.person = person;
        this.quotes = quotes;
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public List<QuoteDto> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<QuoteDto> quotes) {
        this.quotes = quotes;
    }

    public int getCount() {
        return quotes.size();
    }
}
