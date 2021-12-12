package li.l1t.sic.rest;

import li.l1t.sic.model.GuestUser;
import li.l1t.sic.model.dto.PersonDto;
import li.l1t.sic.service.PersonMapper;
import li.l1t.sic.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * REST controller providing an API for getting information about people and modifying it.
 *
 * @since 2016-02-14
 */
@RestController
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonController(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }


    @GetMapping("/api/person/list")
    public List<PersonDto> personList() {
        return personService.getAllPeople().stream()
                .map(personMapper::toDto)
                .toList();
    }

    @GetMapping("/api/person/by/id/{id}")
    public PersonDto singlePerson(@PathVariable("id") int id) {
        return personMapper.toDto(personService.getById(id));
    }

    @PostMapping("/api/person/new")
    public PersonDto newPerson(@RequestBody PersonDto personDto, Principal user) {
        GuestUser.validateNotGuest(user);
        return personMapper.toDto(personService.newPerson(personDto));
    }
}
