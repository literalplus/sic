package li.l1t.sic.service;

import li.l1t.sic.model.Person;
import li.l1t.sic.model.dto.PersonDto;
import org.springframework.stereotype.Service;

/**
 * Handles DTO<->model mapping for persons.
 *
 * @since 2021-12-12
 */
@Service
public class PersonMapper {
    public PersonDto toDto(Person entity) {
        PersonDto dto = new PersonDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
