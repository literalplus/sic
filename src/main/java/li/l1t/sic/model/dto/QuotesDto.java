package li.l1t.sic.model.dto;

import java.util.List;

/**
 * Data Transfer Object for representing sic quotes by person, with count.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 18.2.16
 */
public class QuotesDto {
    private PersonDto teacher;
    private List<QuoteDto> quotes;

    public QuotesDto(PersonDto teacher, List<QuoteDto> quotes) {
        this.teacher = teacher;
        this.quotes = quotes;
    }

    public PersonDto getTeacher() {
        return teacher;
    }

    public void setTeacher(PersonDto teacher) {
        this.teacher = teacher;
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
