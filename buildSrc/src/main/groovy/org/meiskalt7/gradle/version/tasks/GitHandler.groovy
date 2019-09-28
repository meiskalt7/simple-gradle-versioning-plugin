package org.meiskalt7.gradle.version.tasks

import groovy.transform.Immutable
import groovy.transform.TupleConstructor

/**
 * Git handler.
 * Handler interacts with git and obtains all necessary data
 */
@Immutable
@TupleConstructor(includeFields = true)
class GitHandler {

    private OutputStream targetStream
    private File executionContext
    private String changelogFormat

    private String handle(String... args) {
        if (executionContext != null) {
            args.execute(null, executionContext).text.trim()
        } else {
            args.execute().text.trim()
        }
    }

    String getCommitDate(String commit) {
        handle('git', 'log', '-1', '--format=%ai', commit)
    }

    String[] getBaseGitCommand() {
        ['git', 'log', "--pretty=format:${changelogFormat}"]
    }

    String getGitChangelog() {
        handle('git', 'log', "--pretty=format:${changelogFormat}")
    }

    String getGitChangelog(String reference) {
        handle((getBaseGitCommand() + reference) as String[])
    }

    String getGitChangelog(String firstReference, String secondReference) {
        handle((getBaseGitCommand() + "${firstReference}...${secondReference}") as String[])
    }

    String getTagName(String commit) {
        handle('git', 'describe', '--tags', commit)
    }

    String getTagDate(String tag) {
        handle('git', 'log', '-1', '--format=%ai', tag)
    }

    String getLatestCommit() {
        handle('git', 'log', '-1', "--pretty=format:${changelogFormat}")
    }

    String getStatus() {
        handle('git', 'status')
    }

}
