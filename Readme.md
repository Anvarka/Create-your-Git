#Git

## Requirements

Implement a version control system and CLI (command line interface) to it with the following features (20 points):

* `init` -- initialization of the repository
* `add <files>` -- add a file
* `rm <files>` -- the file is removed from the repository, physically remains
* `status` -- changed/deleted/not added files
* `commit <message>` with date and time
* `reset <to_revision>`. The behavior of `reset` is the same as `git reset --hard`
* `log[from_revision]`
* `checkout <revision>`
     * Possible values for `revision`:
         * `commit hash` -- commit hash
         * `master` -- revert the branch to its original state
         * `HEAD~N`, where `N` is a non-negative integer. `HEAD~N` means _Nth commit before HEAD (`HEAD~0 == HEAD`)
* `checkout -- <files>` -- flush changes to files

Additional teams (+5 extra points):

* `branch-create <branch>` -- create a branch named `<branch>`
* `branch-remove <branch>` -- remove branch `<branch>`
* `show-branches` -- show all available branches
* `merge <branch>` -- merge the `<branch>` branch into the current one

## Asymptotics

All commands have time limits:
* The main requirement is not O(n), where n is the number of all commits in the current branch
* For reset, log and checkout, the maximum asymptotics is the number of commits between the current one and the specified one
* For the rest, O(1) is optimal, but more is possible.

## Notes

* `<smth>` means that the transmitted data is required
* `[smth]` means that the data being passed is optional
* it is allowed to use any auxiliary libraries (except those libraries that provide the functionality of the version control system)
     * It is strongly recommended to use libraries to implement the CLI interface. For example, [CLI commons](http://commons.apache.org/proper/commons-cli/), which is already included in the project dependencies (but another one is possible)
* it is enough to support versioning of text files only
* for each __NOT__ revision, a full copy of the repository must be kept

## Tests

The repository contains the [GitCli](src/main/ru/itmo/mit/git/GitCli) interface, which is only needed to connect your git with a ready-made test infrastructure, which is represented by the [AbstractGitTest](src/test/ru /itmo/mit/git/AbstractGitTest). This class provides a set of different commands to create different scenarios for using your application (there are comments for all commands in the code). See [GitTest](src/test/en/itmo/mit/git/GitTest) for an example of describing tests: in one test, you describe a sequence of commands that can manipulate files or call git commands. When run, the test will print the entire `output` of your application to the console.

`GitTest` has a successor [TestDataGitTest](src/test/ru/itmo/mit/git/TestDataGitTest), which differs from the first one in the execution mode. `TestDataGitTest` does not print the output of the application to the console. Instead, it catches it and compares it with the expected output, which is written in the corresponding file in the [testResources](src/testResources) directory (note the calls like `check("someFile.txt")` in the tests).
