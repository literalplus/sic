package li.l1t.sic.rest;

import li.l1t.sic.model.Person;
import li.l1t.sic.model.dto.PersonDto;
import li.l1t.sic.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller providing an API for getting information about people and modifying it.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
    PersonController(PersonService personService) {
        this.personService = personService;
    }


    @RequestMapping("/api/person/list")
    public List<PersonDto> personList() {
        return StreamSupport.stream(personService.getAllPeople().spliterator(), false)
                .map(personService::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping("/api/person/by/id/{id}")
    public Person singlePerson(@PathVariable("id") int id) {
        return personService.getById(id);
    }
}
