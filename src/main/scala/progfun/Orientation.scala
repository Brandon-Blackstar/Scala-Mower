package progfun

sealed trait Orientation {
  def turnRight: Orientation
  def turnLeft: Orientation
}

case object North extends Orientation {
  def turnRight: Orientation = East
  def turnLeft: Orientation = West
}

case object East extends Orientation {
  def turnRight: Orientation = South
  def turnLeft: Orientation = North
}

case object South extends Orientation {
  def turnRight: Orientation = West
  def turnLeft: Orientation = East
}

case object West extends Orientation {
  def turnRight: Orientation = North
  def turnLeft: Orientation = South
}
