package fr.esgi.al.funprog

import scala.io.StdIn.readLine
import progfun.FileReader

object Main extends App {
  // ouvrir et afficher le contenu d'un fichier après demande du path et utiliser la classe FileReader
  println(":> Enter the path of the file: ")

  val path = readLine()
  val fileReader = FileReader(path)
  val content = fileReader.getContent()
  println(s":> $content")

}
