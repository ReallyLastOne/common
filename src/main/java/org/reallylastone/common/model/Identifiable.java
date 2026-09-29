package org.reallylastone.common.model;

import org.reallylastone.common.exception.BadRequestException;

public interface Identifiable<T> {
    T getId();

    static <E extends Enum<E> & Identifiable<T>, T> E getById(Class<E> enumClass, T id) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.getId().equals(id))
                return e;
        }
        throw new BadRequestException("No enum constant with id: " + id);
    }
}
