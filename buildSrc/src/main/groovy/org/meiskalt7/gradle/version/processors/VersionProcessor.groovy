package org.meiskalt7.gradle.version.processors

import groovy.transform.Immutable
import groovy.transform.TupleConstructor
import org.meiskalt7.gradle.version.results.Result

/**
 * Version processor implementation
 * @see Processor
 */
@Immutable
@TupleConstructor(callSuper = true, includeSuperProperties = true, includeSuperFields = true)
class VersionProcessor implements Processor {

    private OutputStream targetStream
    private Result result

    /**
     * Implementation of {@link org.meiskalt7.gradle.version.processors.Processor#parseMajorLog}
     */
    @Override
    String parseMajorLog(List majorKeywords) {
        if (majorKeywords?.isEmpty() || result?.log?.isEmpty()) {
            return '0'
        }
        Integer idx = 0
        for (String line : result.log) {
            for (String keyword : majorKeywords) {
                if (line =~ /(?i)(\[${keyword}\]|\(${keyword}\))/) {
                    idx++
                    break
                }
            }
        }
        return idx
    }

    /**
     * Implementation of {@link org.meiskalt7.gradle.version.processors.Processor#parseFeatureLog}
     */
    @Override
    String parseFeatureLog(List featureKeywords, List resetKeywords) {
        if (featureKeywords?.isEmpty() || result?.log?.isEmpty()) {
            return '0'
        }
        if (resetKeywords?.isEmpty()) {
            resetKeywords = []
        }

        Boolean isFoundLastMajorKeyword = false
        List<String> linesFromLast = []
        outerloop:
        for (String line : result.log) {
            for (String keyword : resetKeywords) {
                if (line =~ /(?i)(\[${keyword}\]|\(${keyword}\))/) {
                    isFoundLastMajorKeyword = true
                    break outerloop
                }
                linesFromLast.add(line)
            }
        }

        if (!isFoundLastMajorKeyword) {
            return 0
        }

        Integer idx = 0
        for (String line : linesFromLast) {
            if (!isFoundLastMajorKeyword) {
                continue
            }
            for (String keyword : featureKeywords) {
                if (line =~ /(?i)(\[${keyword}\]|\(${keyword}\))/) {
                    idx++
                    break
                }
            }
        }
        return idx
    }

    /**
     * Implementation of {@link org.meiskalt7.gradle.version.processors.Processor#parseCommitLog}
     */
    @Override
    String parseCommitLog(List keywords) {
        if (keywords?.isEmpty() || result?.log?.isEmpty()) {
            return '0'
        }

        Boolean isFoundLastMajorKeyword = false
        List<String> linesFromLast = []
        outerloop:
        for (String line : result.log) {
            for (String keyword : keywords) {
                if (line =~ /(?i)(\[${keyword}\]|\(${keyword}\))/) {
                    isFoundLastMajorKeyword = true
                    break outerloop
                }
                linesFromLast.add(line)
            }
        }

        if (!isFoundLastMajorKeyword) {
            return 0
        }

        Integer idx = 0
        for (String line : linesFromLast) {
                idx++
        }
        return idx
    }

    /**
     * Implementation of {@link org.meiskalt7.gradle.version.processors.Processor#count}
     */
    @Override
    Integer count(String keyword) {
        if (keyword?.isEmpty() || result?.log?.isEmpty()) {
            return 0
        }
        return (result.log.join() =~ /(?i)(\[${keyword}\]|\(${keyword}\))/).count
    }

    /**
     * Implementation of {@link org.meiskalt7.gradle.version.processors.Processor#report}
     */
    @Override
    def report() {
        if (result == null) {
            return
        }
        result.print(targetStream)
    }

}
