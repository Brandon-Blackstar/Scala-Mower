package fr.esgi.al.funprog

import play.api.libs.json._
import play.api.libs.functional.syntax._

object JsonFileWriter {

  implicit val positionReads: Reads[Position] = Reads[Position] { json =>
    for {
      x <- (json \ "x").validate[Int]
      y <- (json \ "y").validate[Int]
    } yield Position(x, y)
  }

  implicit val positionWrites: Writes[Position] = (
    (JsPath \ "x").write[Int] and
      (JsPath \ "y").write[Int]
  )(unlift(Position.unapply))

  implicit val positionFormat: Format[Position] =
    Format(positionReads, positionWrites)

  implicit val directionReads: Reads[Direction] = Reads[Direction] {
    case JsString("N") => JsSuccess(N)
    case JsString("E") => JsSuccess(E)
    case JsString("S") => JsSuccess(S)
    case JsString("W") => JsSuccess(W)
    case _             => JsError("Cannot parse direction")
  }

  implicit val directionWrites: Writes[Direction] = Writes[Direction] {
    case N => JsString("N")
    case E => JsString("E")
    case S => JsString("S")
    case W => JsString("W")
  }

  implicit val directionFormat: Format[Direction] =
    Format(directionReads, directionWrites)

  implicit val instructionReads: Reads[Instruction] = Reads[Instruction] {
    case JsString("A") => JsSuccess(A)
    case JsString("G") => JsSuccess(G)
    case JsString("D") => JsSuccess(D)
    case _             => JsError("Cannot parse instruction")
  }

  implicit val instructionWrites: Writes[Instruction] = Writes[Instruction] {
    case A => JsString("A")
    case G => JsString("G")
    case D => JsString("D")
  }

  implicit val instructionFormat: Format[Instruction] =
    Format(instructionReads, instructionWrites)

  implicit val mowerReads: Reads[Mower] = Reads[Mower] { json =>
    for {
      start        <- (json \ "start").validate[Position]
      direction    <- (json \ "direction").validate[Direction]
      instructions <- (json \ "instructions").validate[List[Instruction]]
      end          <- (json \ "end").validateOpt[Position]
    } yield Mower(start, start, direction, instructions, end)
  }

  implicit val mowerWrites: Writes[Mower] = new Writes[Mower] {
    def writes(mower: Mower) = Json.obj(
      "debut" -> Json.obj(
        "point"     -> Json.toJson(mower.start),
        "direction" -> Json.toJson(mower.direction)
      ),
      "instructions" -> Json.toJson(mower.instructions),
      "fin" -> Json.obj(
        "point"     -> Json.toJson(mower.end.getOrElse(mower.currentPosition)),
        "direction" -> Json.toJson(mower.direction)
      )
    )
  }

  implicit val mowerFormat: Format[Mower] = Format(mowerReads, mowerWrites)

  implicit val lawnReads: Reads[Lawn] = Reads[Lawn] { json =>
    for {
      limit  <- (json \ "limit").validate[Position]
      mowers <- (json \ "mowers").validate[List[Mower]]
    } yield Lawn(limit, mowers)
  }

  implicit val lawnWrites: Writes[Lawn] = (
    (JsPath \ "limite").write[Position] and
      (JsPath \ "tondeuses").write[List[Mower]]
  )(unlift(Lawn.unapply))

  implicit val lawnFormat: Format[Lawn] = Format(lawnReads, lawnWrites)

  def toJson(lawn: Lawn): String = {
    Json.toJson(lawn).toString()
  }
}
