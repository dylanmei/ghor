
import ghor.*

class Git extends Ghor {
    @Description("Fetches named heads or tags from one or more other repositories, along with the objects necessary to complete them.")
    //@Alias('f')
    @Option(name = 'all', description = 'Fetch all remotes.')
    @Option(name = 'multiple', description = 'Allow several <repository> and <group> arguments to be specified. No <refspec>s may be specified.')
    @Option(name = 'append', alias = 'a', description = 'Append ref names and object names of fetched refs to the existing contents of .git/FETCH_HEAD. Without this option old data in .git/FETCH_HEAD will be overwritten.')
    public void fetch(repository, String... refspec) {
        println 'Fetching repository ' + repository
    }

    // @Description
    // @Default command if not specified (ie, 'git remote' runs 'git remote show')
    class GitRemote extends Ghor {

    }
}

