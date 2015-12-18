package com.clouway.jdbc.task1.util;

import com.clouway.jdbc.task1.Provider;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Providers {

    public static <T> Provider<T> of(final T value) {
        return new Provider<T>() {
            @Override
            public T get() {
                return value;
            }
        };
    }
}
