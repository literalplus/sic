package li.l1t.sic.service;

import li.l1t.sic.exception.QuoteNotFoundException;
import li.l1t.sic.model.Person;
import li.l1t.sic.model.Quote;
import li.l1t.sic.model.dto.QuoteDto;
import li.l1t.sic.model.repo.QuoteRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * Service handling sic quotes, specifically finding quotes by person,
 * adding, creating and deleting quotes.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
@Service
public class QuoteService {
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuoteVoteService voteService;

    public List<Quote> getAllQuotesByPerson(Person person) {
        return quoteRepository.findAllByPersonOrderByVoteCountDesc(person);
    }

    /**
     * Maps a model quote to a Data Transfer Object.
     *
     * @param quote the quote to map
     * @return a DTO containing copied data from the quote
     */
    public QuoteDto toDto(Quote quote) {
        return toDto(quote, null);
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
        quoteDto.setPerson(personService.toDto(quote.getPerson()));
        quoteDto.setCreatorName(quote.getCreator() == null ? "???" : quote.getCreator().getName());
        quoteDto.setSubText(quote.getSubText());
        quoteDto.setVoteCount(quote.getVoteCount());
        if (user != null){
            quoteDto.setOwnVote(voteService.findVoteStatus(quote, user));
        }
        return quoteDto;
    }

    public Quote adaptFromDto(Quote quote, QuoteDto dto) {
        quote.setText(dto.getText());
        quote.setSubText(dto.getSubText());
        quote.setVoteCount(dto.getVoteCount());
        return quote;
    }

    public Quote toEntity(QuoteDto dto) {
        Person person = personService.findByDto(dto.getPerson());
        Quote quote = new Quote(dto.getId(), person);
        return adaptFromDto(quote, dto);
    }

    public Quote findById(int quoteId) {
        Quote quote = quoteRepository.findOne(quoteId);
        if (quote == null || quote.isDeleted()){
            throw new QuoteNotFoundException(quoteId);
        } else {
            return quote;
        }
    }

    public void save(Quote quote) {
        quoteRepository.save(quote);
    }

    public Quote save(QuoteDto spec) {
        return save(spec, null);
    }

    public Quote save(QuoteDto spec, Principal user) {
        Person person = personService.findByDto(spec.getPerson());
        Quote quote = quoteRepository.findOne(spec.getId());
        if (quote == null){
            quote = new Quote(person);
            if (user != null){
                quote.setCreator(userService.fromRegistered(user));
            }
        }
        adaptFromDto(quote, spec);
        return quoteRepository.save(quote);
    }

    public void delete(Quote quote) {
        quote.setDeleted(true);
        save(quote);
        voteService.deleteAllVotesOn(quote);
    }

    public List<Quote> findAllWithVoteCountGreaterThan(int voteCount) {
        return quoteRepository.findByVoteCountGreaterThan(voteCount);
    }

    public List<Quote> findAllWithVoteCountGreaterThan(int voteCount, int pageId, int pageSize) {
        return quoteRepository.findByVoteCountGreaterThan(
                voteCount,
                new PageRequest(pageId, pageSize)
        );
    }

    public long getQuoteCount() {
        return quoteRepository.count();
    }

    public List<Quote> findLatest(int pageId, int pageSize) {
        Pageable pageable = new PageRequest(pageId, pageSize);
        return quoteRepository.findAllByOrderByLastUpdatedDesc(pageable);
    }
}
