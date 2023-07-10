package progfun

import better.files._

import scala.util.Try

case class FileReader(filePath: String) {
  // méthode pour récupérer les lignes du fichier
  def getLines(): Try[List[String]] = Try {
    val f = File(filePath)
    f.lines.toList
  }

  // Méthode pour récupérer tout le contenu du fichier en String
  def getContent(): Try[String] = Try {
    val f = File(filePath)
    f.contentAsString
  }
}
