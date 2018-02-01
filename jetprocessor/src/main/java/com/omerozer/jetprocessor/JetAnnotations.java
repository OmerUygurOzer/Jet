package com.omerozer.jetprocessor;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by omerozer on 1/26/18.
 */

class JetAnnotations {

    static Set<String> getAll() {
        return new LinkedHashSet<String>(
                Arrays.asList(
                        "com.omerozer.jetcore.EventSuccess",
                             "com.omerozer.jetcore.EventError"
                ));
    }
}
