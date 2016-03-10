package li.l1t.tingo.service;

import li.l1t.tingo.model.Teacher;
import li.l1t.tingo.model.TingoField;
import li.l1t.tingo.model.dto.FieldDto;
import li.l1t.tingo.model.repo.FieldRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handling Tingo fields, specifically finding fields by teacher,
 * adding, creating and deleting fields.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
@Service
public class FieldService {
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private TeacherService teacherService;

    public List<TingoField> getAllFieldsByTeacher(Teacher teacher) {
        return fieldRepository.findAllByTeacher(teacher);
    }

    /**
     * Maps a model field to a Data Transfer Object.
     * @param field the field to map
     * @return a DTO containing copied data from the field
     */
    public FieldDto toDto(TingoField field) {
        Validate.notNull(field, "field");
        Validate.notNull(field.getTeacher(), "field.getTeacher()");
        FieldDto fieldDto = new FieldDto();
        fieldDto.setId(field.getId());
        fieldDto.setText(field.getText());
        fieldDto.setTeacherId(field.getTeacher().getId());
        return fieldDto;
    }

    public TingoField adaptFromDto(TingoField field, FieldDto dto) {
        field.setText(dto.getText());
        return field;
    }

    public TingoField save(FieldDto spec) {
        Teacher teacher = teacherService.getById(spec.getTeacherId());
        TingoField tingoField = fieldRepository.findOne(spec.getId());
        if (tingoField == null) {
            tingoField = new TingoField(teacher, spec.getText());
        } else {
            adaptFromDto(tingoField, spec);
        }
        return fieldRepository.save(tingoField);
    }
}
