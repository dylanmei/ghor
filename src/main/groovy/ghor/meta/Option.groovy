
package ghor.meta

class Option {
  def name
  def alias
  def description

  public String toString() {
    !alias ? "--$name" : "--$name or -$alias"
  }  
}