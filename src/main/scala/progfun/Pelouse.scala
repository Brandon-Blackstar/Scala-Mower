package progfun

import play.api.libs.json._

import scala.util.Try
case class Pelouse(limite: Position, tondeuses: List[Tondeuse]) {

  def executeInstructions(): Try[String] = Try {
    val tondeusesRapportFinal = tondeuses.map { tondeuse =>
      val instructions = tondeuse.instructions.map(_.toString)
      val nouvelleTondeuse = instructions.foldLeft(tondeuse) { (t, instruction) =>
        instruction match {
          case "G" => t.pivoterGauche
          case "D" => t.pivoterDroite
          case "A" => t.avancer
          case _ => t // Ignorer les instructions invalides
        }
      }
      val debut = tondeuse.position
      val fin = nouvelleTondeuse.position
      val rapport = Json.obj(
        "debut" -> Json.obj(
          "point" -> Json.obj(
            "x" -> debut.x,
            "y" -> debut.y
          ),
          "direction" -> tondeuse.orientation.toString.head.toString
        ),
        "instructions" -> instructions,
        "fin" -> Json.obj(
          "point" -> Json.obj(
            "x" -> fin.x,
            "y" -> fin.y
          ),
          "direction" -> nouvelleTondeuse.orientation.toString.head.toString
        )
      )
      rapport
    }

    val rapportFinal = Json.obj(
      "limite" -> Json.obj(
        "x" -> limite.x,
        "y" -> limite.y
      ),
      "tondeuses" -> tondeusesRapportFinal
    )

    Json.prettyPrint(rapportFinal)
  }
}

