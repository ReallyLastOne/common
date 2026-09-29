package org.reallylastone.common.model;

import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnumAttributeConverter<E extends Enum<E> & Identifiable<T>, T>
        implements AttributeConverter<E, T> {
    private final Class<E> type;

    @Override
    public T convertToDatabaseColumn(E attribute) {
        return attribute.getId();
    }

    @Override
    public E convertToEntityAttribute(T dbData) {
        return Identifiable.getById(type, dbData);
    }
}
