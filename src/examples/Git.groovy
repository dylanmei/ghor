
import ghor.*

class Git extends Ghor {
  @Description("Fetches named heads or tags from one or more other repositories, along with the objects necessary to complete them.")
  @Option(name = 'all', description = 'Fetch all remotes.')
  @Option(name = 'multiple', description = 'Allow several <repository> and <group> arguments to be specified. No <refspec>s may be specified.')
  @Option(name = 'append', alias = 'a', description = 'Append ref names and object names of fetched refs to the existing contents of .git/FETCH_HEAD. Without this option old data in .git/FETCH_HEAD will be overwritten.')
  @Command def fetch(repository, String... refspec) {
      println 'Fetching repository ' + repository

      if (remote == null) println "REMOTE IS NULL"
      else println "REMOTE IS NOT NULL :)"
  }

//    @Description("Manage the set of repositories ('remotes') whose branches you track.")
//    @Alias('r')
//    @Default('show')
  @Command GitRemote remote
}

class GitRemote extends Ghor {

}