## Synopsis

Custom version generation gradle plugin

1) To check the version generation task just type:

    gradle generateVersion

2) You can customize the version generation task by add to your build.gradle file following objects:

    versionGenerator {
        debug = false
        majorKeywords = ['Major']
        featureKeywords = ['Feature', 'AnotherTag']
        majorNumber = 1
        featureNumber = 0
        commitNumber = 1
    }
    
## Example

PRJ-1 [MAJOR]: init commit

version now: 1.0.0

PRJ-2 [FEATURE]: add feature

version now: 1.1.0

FeatureNumberTask look for last major key word and then count all commits from it

PRJ-3 [MINOR]: small fix

version now: 1.1.1

CommitNumberTask look for last major or feature key word and then count all commits from it

## Reference



## License

GNU General Public License v3.0