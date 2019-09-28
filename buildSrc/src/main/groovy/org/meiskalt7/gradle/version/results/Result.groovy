package org.meiskalt7.gradle.version.results

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

/**
 * The result of task execution
 */
@TupleConstructor(includeFields = true)
@EqualsAndHashCode
@ToString
class Result {

    private List log = new LinkedList<>()

    /**
     * Print result log
     * @param targetStream target stream
     * return
     */
    def print(OutputStream targetStream) {
        if (!targetStream) {
            return
        }
        targetStream.println(log)
    }
}
