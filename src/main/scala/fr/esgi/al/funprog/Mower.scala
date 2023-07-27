package fr.esgi.al.funprog

case class Mower(
    start: Position,
    currentPosition: Position,
    direction: Direction,
    instructions: List[Instruction],
    end: Option[Position])

object Mower {

  def withEnd(
      start: Position,
      direction: Direction,
      instructions: List[Instruction],
      end: Position): Mower =
    Mower(start, start, direction, instructions, Some(end))

  def withoutEnd(
      start: Position,
      direction: Direction,
      instructions: List[Instruction]): Mower =
    Mower(start, start, direction, instructions, None)

  private def applyInstruction(
      mower: Mower,
      instruction: Instruction,
      lawn: Lawn): Mower = instruction match {
    case A =>
      mower.copy(currentPosition =
        moveForward(mower.currentPosition, mower.direction, lawn)
      )
    case G => mower.copy(direction = turnLeft(mower.direction))
    case D => mower.copy(direction = turnRight(mower.direction))
  }

  private def moveForward(
      point: Position,
      direction: Direction,
      lawn: Lawn): Position = direction match {
    case N => if (point.y < lawn.limit.y) point.copy(y = point.y + 1) else point
    case E => if (point.x < lawn.limit.x) point.copy(x = point.x + 1) else point
    case S => if (point.y > 0) point.copy(y = point.y - 1) else point
    case W => if (point.x > 0) point.copy(x = point.x - 1) else point
  }

  private def turnLeft(direction: Direction): Direction = direction match {
    case N => W
    case W => S
    case S => E
    case E => N
  }

  private def turnRight(direction: Direction): Direction = direction match {
    case N => E
    case E => S
    case S => W
    case W => N
  }

  def executeInstructions(mower: Mower, lawn: Lawn): Mower = {
    mower.instructions.foldLeft(mower) { (mower, instruction) =>
      applyInstruction(mower, instruction, lawn)
    }
  }
}
