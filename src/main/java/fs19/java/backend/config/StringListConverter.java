package fs19.java.backend.config;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream().collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return Arrays.asList(dbData.split(DELIMITER));
    }
}