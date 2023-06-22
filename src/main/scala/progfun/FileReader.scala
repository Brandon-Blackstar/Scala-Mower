package progfun

import better.files._

// library better-files pour manipuler les fichiers
// val f = File("/User/johndoe/Documents")
//on va récupérer toutes les lignes du fichier
//  f.lines.toList
//
//si on veut récupérer tout le contenu du fichier en String
//  f.contentAsString

//classe de lecture de fichier utilisant la librairie better-files
case class FileReader(filePath: String) {
  // méthode pour récupérer les lignes du fichier
  def getLines(): List[String] = {
    val f = File(filePath)
    f.lines.toList
  }

  // méthode pour récupérer tout le contenu du fichier en String
  def getContent(): String = {
    val f = File(filePath)
    f.contentAsString
  }
}
