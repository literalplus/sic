package li.l1t.sic.service;

import li.l1t.sic.model.dto.QuoteDto;
import li.l1t.sic.model.dto.QuotesDto;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

/**
 * Intermediate layer for use-case dispatch related to quotes.
 *
 * @since 2021-12-12
 */
@Service
public class QuoteDtoService {
    private final PersonService personService;
    private final QuoteService quoteService;
    private final QuoteVoteService quoteVoteService;
    private final QuoteMapper quoteMapper;
    private final PersonMapper personMapper;

    @Autowired
    public QuoteDtoService(
            PersonService personService, QuoteService quoteService, QuoteVoteService quoteVoteService,
            QuoteMapper quoteMapper, PersonMapper personMapper
    ) {
        this.personService = personService;
        this.quoteService = quoteService;
        this.quoteVoteService = quoteVoteService;
        this.quoteMapper = quoteMapper;
        this.personMapper = personMapper;
    }

    @Transactional
    public QuotesDto getForPerson(int personId, Principal user) {
        var person = personService.getById(personId);

        var quotes = quoteService.getAllQuotesByPerson(person).stream()
                .map(quote -> quoteMapper.toDto(quote, user))
                .toList();

        return new QuotesDto(personMapper.toDto(person), quotes);
    }

    @Transactional
    public List<QuoteDto> getLatest(Pageable pageable) {
        return quoteService.findLatest(pageable).stream()
                .map(quote -> quoteMapper.toDto(quote, null))
                .toList();
    }

    @Transactional
    public List<QuoteDto> getBest(int minRating, Pageable pageable) {
        return quoteService.findAllWithVoteCountGreaterThan(minRating, pageable).stream()
                .map(quote -> quoteMapper.toDto(quote, null))
                .toList();
    }

    @Transactional
    public QuoteDto save(QuoteDto quoteTemplate, Principal user) {
        Validate.notNull(quoteTemplate.getPerson(), "template.person");
        var person = personService.getById(quoteTemplate.getPerson().getId());
        var quote = quoteService.getOrCreate(quoteTemplate.getId(), person, user);
        var saved = quoteService.save(quote);
        return quoteMapper.toDto(saved, user);
    }

    @Transactional
    public void deleteById(QuoteDto toDelete) {
        quoteService.deleteById(toDelete.getId())
                .ifPresent(quoteVoteService::deleteAllVotesOn);
    }

    @Transactional
    public long getQuotesCount() {
        return quoteService.getQuoteCount();
    }
}
