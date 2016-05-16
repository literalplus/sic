package li.l1t.sic.service;

import li.l1t.sic.exception.PersonNotFoundException;
import li.l1t.sic.model.Person;
import li.l1t.sic.model.dto.PersonDto;
import li.l1t.sic.model.repo.PersonRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service handling people, providing a bridge between the controller and the model.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Iterable<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public Person getById(int id) {
        Person person = personRepository.findOne(id);
        if(person == null) {
            throw new PersonNotFoundException(id);
        }
        return person;
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

    public PersonDto toDto(Person entity) {
        PersonDto dto = new PersonDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public Person findByDto(PersonDto personDto) {
        Validate.notNull(personDto, "personDto");
        return getById(personDto.getId());
    }
}
