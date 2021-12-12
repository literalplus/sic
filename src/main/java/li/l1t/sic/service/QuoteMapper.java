package li.l1t.sic.service;

import li.l1t.sic.model.Person;
import li.l1t.sic.model.Quote;
import li.l1t.sic.model.dto.QuoteDto;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Handles mapping of DTOs to models and back in relation to quotes.
 *
 * @since 2021-12-12
 */
@Service
public class QuoteMapper {
    private final QuoteVoteService quoteVoteService;
    private final PersonMapper personMapper;

    public QuoteMapper(QuoteVoteService quoteVoteService, PersonMapper personMapper) {
        this.quoteVoteService = quoteVoteService;
        this.personMapper = personMapper;
    }

    /**
     * Maps a model quote to a Data Transfer Object.
     *
     * @param quote the quote to map
     * @param user  the user to use for user-specific properties
     * @return a DTO containing copied data from the quote
     */
    public QuoteDto toDto(Quote quote, Principal user) {
        Validate.notNull(quote, "quote");
        Validate.notNull(quote.getPerson(), "quote.getPerson()");
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setId(quote.getId());
        quoteDto.setText(quote.getText());
        quoteDto.setPerson(personMapper.toDto(quote.getPerson()));
        quoteDto.setCreatorName(quote.getCreator() == null ? "???" : quote.getCreator().getName());
        quoteDto.setSubText(quote.getSubText());
        quoteDto.setVoteCount(quote.getVoteCount());
        if (user != null) {
            quoteDto.setOwnVote(quoteVoteService.findVoteStatus(quote, user));
        }
        return quoteDto;
    }

    public Quote toEntity(QuoteDto dto, Person person) {
        var quote = new Quote(dto.getId(), person);
        return adaptFromDto(quote, dto);
    }

    private Quote adaptFromDto(Quote quote, QuoteDto dto) {
        quote.setText(dto.getText());
        quote.setSubText(dto.getSubText());
        quote.setVoteCount(dto.getVoteCount());
        return quote;
    }
}
