package org.meiskalt7.gradle.version


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.meiskalt7.gradle.version.tasks.VersionTask

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Version plugin.
 * Plugin registers generation version task
 * @see Plugin
 * @see Project
 */
class VersionPlugin implements Plugin<Project> {

    private static final String EXTENSION_NAME = 'versionGenerator'
    private static final String TASK_NAME = 'generateVersion'
    private static final String TASK_DESCRIPTION = 'Generates custom artifact version.'
    private static final String VERSION_FILENAME = 'version.txt'

    /**
     * apply plugin
     * @param project gradle project
     */
    @Override
    void apply(Project project) {
        def extension = project.getExtensions().create(EXTENSION_NAME, VersionPluginExtension)
        try {
            project.task(TASK_NAME){
                description = TASK_DESCRIPTION
                doFirst{
                    println "${this.TASK_NAME}:: start task"
                    new VersionTask(targetStream: System.out, extension: extension).doAction()

                    Path versionPath = Paths.get("$project.rootProject.projectDir/" + VERSION_FILENAME)
                    if (!Files.exists(versionPath)) {
                        Files.write(versionPath, Collections.singletonList(project.rootProject.version))
                    }
                    String startVersion = Files.readAllLines(versionPath).first()
                    Integer[] numbers = startVersion.tokenize('.')*.toInteger()
                    extension.majorNumber = numbers[0] + extension.majorNumber.toInteger()
                    extension.featureNumber = numbers[1] + extension.featureNumber.toInteger()
                    extension.commitNumber = numbers[2]+ extension.commitNumber.toInteger()
                    project.version = "${extension.majorNumber}.${extension.featureNumber}.${extension.commitNumber}"
                    Files.write(versionPath, Collections.singletonList("$project.version"))

                    println 'MAJOR version: ' + extension.majorNumber
                    println 'FEATURE version: ' + extension.featureNumber
                    println 'BUILD version: ' + extension.commitNumber
                    println "${this.TASK_NAME}:: end task"
                }
            }
        } catch (MissingMethodException e) {
            // Maybe we're running with an old Gradle version, let's try tasks.add
            //project.tasks.add(TASK_NAME, VersionGenerator)
            project.tasks.add(TASK_NAME, VersionTask)
        }
    }

}
