package org.reallylastone.common.service;

public interface DeletableMessages {

    String notFound();

    static DeletableMessages of(String message) {
        return () -> message;
    }
}
