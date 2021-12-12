package li.l1t.sic.service;

import li.l1t.sic.exception.PersonNotFoundException;
import li.l1t.sic.model.Person;
import li.l1t.sic.model.dto.PersonDto;
import li.l1t.sic.model.repo.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handling people, providing a bridge between the controller and the model.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public Person getById(int id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    /**
     * Creates a new person from a DTO and saves it to database.
     * @param dto the dto to create the person from
     * @return the created person
     */
    public Person newPerson(PersonDto dto) {
        Person person = new Person();
        person.setName(dto.getName());
        personRepository.save(person);
        return person;
    }
}
