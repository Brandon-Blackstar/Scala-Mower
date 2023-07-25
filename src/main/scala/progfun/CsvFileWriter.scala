package progfun

object CsvFileWriter {
  def toCsv(lawn: Lawn): String = {
    val header = "numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions"
    val mowers = lawn.mowers.zipWithIndex.map { case (mower, index) =>
      val start = s"${mower.start.x.toString};${mower.start.y.toString};${mower.direction.toString}"
      val end = s"${mower.currentPosition.x.toString};${mower.currentPosition.y.toString};${mower.direction.toString}"
      val instructions = mower.instructions.mkString("")
      s"${(index + 1).toString};$start;$end;$instructions"
    }
    (header :: mowers).mkString("\n")
  }
}
