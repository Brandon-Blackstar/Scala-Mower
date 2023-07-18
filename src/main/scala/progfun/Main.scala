package progfun

import better.files.File
import scala.util.{Try, Failure, Success}

object Main extends App {

  // Ask for the input file path
  println("Please enter the path to the input file:")
  val inputFilePath = scala.io.StdIn.readLine()

  // Ask for the output file path
  println("Please enter the path to the output file:")
  val outputFilePath = scala.io.StdIn.readLine()

  val fileReader = FileReader(inputFilePath)

  // Read and parse the input file
  val lawnResult = fileReader.parseInput(inputFilePath)

  lawnResult match {
    case Right(lawn) =>
      // Execute all mowers
      val finalLawn = lawn.executeAllMowers()

      // Convert the result to JSON
      val json = JsonFileWriter.toJson(finalLawn)

      // Write the result to the output file
      val outputFileTry = Try(File(outputFilePath).overwrite(json))
      outputFileTry match {
        case Success(_) => println(s"Successfully wrote to the file $outputFilePath")
        case Failure(e) => println(s"Failed to write to the file $outputFilePath: ${e.getMessage}")
      }

    case Left(error) =>
      println(s"Error parsing the input file: ${error.message}")
  }
}
