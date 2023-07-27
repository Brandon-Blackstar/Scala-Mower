package fr.esgi.al.funprog

import better.files._
import scala.util.Try

// Define a case class for errors
case class LawnError(message: String)

case class FileReader(filePath: String) {
  def parseInput(filePath: String): Either[LawnError, Lawn] = {
    val file = File(filePath)
    if (!file.exists) {
      Left(LawnError(s"File $filePath does not exist"))
    } else {
      val lines = file.lines.toList
      lines match {
        case limit :: mowerLines =>
          for {
            limitPosition <- parsePosition(limit)
            mowers        <- parseMowers(mowerLines)
          } yield Lawn(limitPosition, mowers)
        case _ =>
          Left(LawnError("Invalid input file"))
      }
    }
  }

  private def parsePosition(line: String): Either[LawnError, Position] = {
    val parts = line.trim.split(" ")
    if (parts.length == 2) {
      val x = parts(0)
      val y = parts(1)
      for {
        xPosition <- Try(x.toInt).toEither.left.map(_ =>
          LawnError(s"Invalid x coordinate: $x")
        )
        yPosition <- Try(y.toInt).toEither.left.map(_ =>
          LawnError(s"Invalid y coordinate: $y")
        )
      } yield Position(xPosition, yPosition)
    } else {
      Left(LawnError(s"Invalid Position: $line"))
    }
  }

  private def parseDirection(dir: String): Either[LawnError, Direction] =
    dir match {
      case "N" => Right(N)
      case "E" => Right(E)
      case "S" => Right(S)
      case "W" => Right(W)
      case _   => Left(LawnError(s"Invalid direction: $dir"))
    }

  private def parseInstruction(inst: Char): Either[LawnError, Instruction] =
    inst match {
      case 'A' => Right(A)
      case 'G' => Right(G)
      case 'D' => Right(D)
      case _   => Left(LawnError(s"Invalid instruction: ${inst.toString}"))
    }

  private def sequence(xs: List[Either[LawnError, Instruction]])
      : Either[LawnError, List[Instruction]] = xs match {
    case Nil    => Right(Nil)
    case h :: t => h.flatMap(v => sequence(t).map(v :: _))
  }

  private def parseMowers(lines: List[String]): Either[LawnError, List[Mower]] =
    lines
      .grouped(2)
      .toList
      .foldRight(Right(Nil): Either[LawnError, List[Mower]]) {
        case (List(pos, inst), acc) =>
          val posParts = pos.split(" ")
          val startPosString =
            s"${posParts(0)} ${posParts(1)}" // Concatenate the first two parts as position
          val directionString = posParts(2) // Use the third part as direction
          for {
            mowers       <- acc
            startPos     <- parsePosition(startPosString)
            startDir     <- parseDirection(directionString)
            instructions <- sequence(inst.toList.map(parseInstruction))
          } yield Mower.withoutEnd(startPos, startDir, instructions) :: mowers
        case _ =>
          Left(LawnError("Invalid input format: incomplete mower data"))
      }

}
