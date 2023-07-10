package fr.esgi.al.funprog


import progfun.{East, FileReader, North, Orientation, Pelouse, Position, South, Tondeuse, West}

import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}


object Main {
  def main(args: Array[String]): Unit = {
    println(":> Enter the path of the file: ")
    val filePathIn = readLine()

    val result = for {
      lines <- FileReader(filePathIn).getLines()
      pelouseInfos <- Try(lines(0).split(" "))
      limiteX <- Try(pelouseInfos(0).toInt)
      limiteY <- Try(pelouseInfos(1).toInt)
      limite = Position(limiteX, limiteY)
      tondeusesData = lines.drop(1).grouped(2).toList
      tondeuses <- Try {
        tondeusesData.map { data =>
          val tondeusePosition = data(0).split(" ")
          val tondeuseX = tondeusePosition(0).toInt
          val tondeuseY = tondeusePosition(1).toInt
          val orientation: Orientation = tondeusePosition(2) match {
            case "N" => North
            case "E" => East
            case "W" => West
            case "S" => South
            case _ => sys.error("Orientation invalide")
          }
          val tondeuseInstructions = data(1).toList
          Tondeuse(Position(tondeuseX, tondeuseY), orientation, tondeuseInstructions)
        }
      }
      pelouse = Pelouse(limite, tondeuses)
      rapport <- pelouse.executeInstructions()

    } yield rapport

    result match {
      case Success(rapport) => println(rapport)
      case Failure(exception) =>
        println(s"Une erreur s'est produite : ${exception.getMessage}")
        sys.exit(1)
    }
  }
}

