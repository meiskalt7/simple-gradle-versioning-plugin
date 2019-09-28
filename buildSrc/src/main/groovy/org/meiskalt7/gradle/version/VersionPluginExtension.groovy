package org.meiskalt7.gradle.version

import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

/**
 * Extensions for version generation
 */
@TypeChecked
@TupleConstructor
class VersionPluginExtension {
    List<String> majorKeywords = ['Major']
    List<String> featureKeywords = ['Feature', 'Minor']
    /**
     * Count every major keyword in commit message
     */
    String majorNumber
    /**
     * Count every feature keyword in commit message after last major keyword
     */
    String featureNumber
    /**
     * Count every commit after last feature or major keyword
     */
    String commitNumber
    String debug
}
