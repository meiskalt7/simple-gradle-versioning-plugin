package org.meiskalt7.gradle.version.tasks

import groovy.transform.Immutable
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked
import org.meiskalt7.gradle.version.VersionPluginExtension
import org.meiskalt7.gradle.version.processors.VersionProcessor
import org.meiskalt7.gradle.version.results.Result

/**
 * Task version generation and saves it into extension
 */
@Immutable
@TypeChecked
@TupleConstructor
class VersionTask {

    private VersionPluginExtension extension
    private OutputStream targetStream

    /**
     * do version generation task
     * return
     */
    def doAction() {
        String allCommits = new GitHandler(changelogFormat: '%s').getGitChangelog()
        VersionProcessor processor = new VersionProcessor(targetStream: targetStream, result: new Result())
        processor.result.log = (allCommits.split('\n') as LinkedList)
        if (extension?.debug) {
            processor.result.print(targetStream)
        }
        if (extension != null) {
            if (extension.majorNumber == null) {
                extension.majorNumber = processor.parseMajorLog(extension.majorKeywords)
            }
            if (extension.featureNumber == null) {
                extension.featureNumber = processor.parseFeatureLog(extension.featureKeywords, extension.majorKeywords)
            }
            if (extension.commitNumber == null) {
                extension.commitNumber = processor.parseCommitLog([extension.majorKeywords, extension.featureKeywords])
            }
        } else {
            extension = new VersionPluginExtension()
            extension.majorNumber = processor.parseMajorLog(extension.majorKeywords)
            extension.featureNumber = processor.parseFeatureLog(extension.featureKeywords, extension.majorKeywords)
            extension.commitNumber = processor.parseCommitLog([extension.majorKeywords, extension.featureKeywords])
        }
    }

}
