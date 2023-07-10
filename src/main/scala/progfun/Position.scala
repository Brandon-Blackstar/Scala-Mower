package progfun

case class Position(x: Int, y: Int) {
  def moveUp: Position = copy(y = y + 1)
  def moveDown: Position = copy(y = y - 1)
  def moveLeft: Position = copy(x = x - 1)
  def moveRight: Position = copy(x = x + 1)

}
