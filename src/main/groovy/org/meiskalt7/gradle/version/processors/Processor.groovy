package org.meiskalt7.gradle.version.processors

/**
 * Processor of version information
 */
interface Processor {

    /**
     * Parse major version from git commit log
     * @param majorKeywords valuable for major version majorKeywords counting
     * @return major version
     */
    String parseMajorLog(List majorKeywords)

    /**
     * Parse release version from git commit log
     * @param countKeywords valuable for release version majorKeywords counting
     * @param resetKeywords valuable for release version majorKeywords reset
     * @return release version
     */
    String parseFeatureLog(List countKeywords, List resetKeywords)

    /**
     * Parse build version from git commit log
     * @param keywords valuable for release version majorKeywords reset
     * @return build version
     */
    String parseCommitLog(List keywords)

    /**
     * Debug, report runtime process info
     */
    def report()

}