package uk.co.blackcell.dao.converter;

import uk.co.blackcell.entity.type.TemplateType;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA converter implementation to map between the {@link TemplateType} enumeration and its corresponding string type
 *
 * @author James Bishop
 *
 */
@Converter
@Slf4j
public class TemplateTypeConverter implements AttributeConverter<TemplateType, String> {

    @Override
    public String convertToDatabaseColumn(final TemplateType type) {
        log.debug("convertToDatabaseColumn('{}') method called.", type);

        return String.valueOf(type);
    }

    @Override
    public TemplateType convertToEntityAttribute(final String value) {
        log.debug("convertToEntityAttribute('{}') method called.", value);

        return TemplateType.valueOf(value);
    }
}
