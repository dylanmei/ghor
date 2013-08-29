
import ghor.*
import ghor.ast.*

def source = "./src/examples/Git.groovy"
new GroovyClassLoader()
    .parseClass(new File(source))
    .newInstance()
    .start(args)
