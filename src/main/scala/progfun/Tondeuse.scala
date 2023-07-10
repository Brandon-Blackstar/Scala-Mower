package progfun

case class Tondeuse(position: Position, orientation: Orientation, instructions: List[Char]) {
  def pivoterDroite: Tondeuse = copy(orientation = orientation.turnRight)
  def pivoterGauche: Tondeuse = copy(orientation = orientation.turnLeft)
  def avancer: Tondeuse = {
    val newPosition = orientation match {
      case North => position.moveUp
      case East => position.moveRight
      case West => position.moveLeft
      case South => position.moveDown
    }
    copy(position = newPosition)
  }

}


