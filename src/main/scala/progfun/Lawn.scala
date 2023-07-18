package progfun

case class Lawn(limit: Position, mowers: List[Mower]) {
  def executeAllMowers(): Lawn = {
    val finalMowers = mowers.map { mower =>
      Mower.executeInstructions(mower, this)
    }
    this.copy(mowers = finalMowers)
  }
}

