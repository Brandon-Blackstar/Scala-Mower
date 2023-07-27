package fr.esgi.al.funprog

import better.files.File
import scala.util.{Failure, Success, Try}

import com.typesafe.config.{Config, ConfigFactory}

object Main extends App {

  val config: Config = Try(ConfigFactory.load()) match {
    case Success(config) => config
    case Failure(_) =>
      println("Failed to load the config file")
      sys.exit(1)
  }

  val inputFilePath: String = Try(
    config.getString("application.input-file")
  ) match {
    case Success(path) => path
    case Failure(_) =>
      println("Failed to load the input file path")
      sys.exit(1)
  }

  val outputJsonFilePath = Try(
    config.getString("application.output-json-file")
  ) match {
    case Success(path) => path
    case Failure(_) =>
      println("Failed to load the output json file path")
      sys.exit(1)
  }

  val outputCsvFilePath = Try(
    config.getString("application.output-csv-file")
  ) match {
    case Success(path) => path
    case Failure(_) =>
      println("Failed to load the output csv file path")
      sys.exit(1)
  }

  // Code without config file (ask for input and output file paths)
  // println("Please enter the path to the input file:")
  // val inputFilePath = scala.io.StdIn.readLine()
  // println("Please enter the path to the output files: (no file extensions)")
  // val outputFilePath = scala.io.StdIn.readLine()

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
      val outputFileTry = Try(File(outputJsonFilePath).overwrite(json))
      outputFileTry match {
        case Success(_) =>
          println(s"Successfully wrote to the file $outputJsonFilePath")
        case Failure(e) =>
          println(
            s"Failed to write to the file $outputJsonFilePath: ${e.getMessage}"
          )
      }

      val csv = CsvFileWriter.toCsv(finalLawn)

      val outputFileTryCsv = Try(File(outputCsvFilePath).overwrite(csv))
      outputFileTryCsv match {
        case Success(_) =>
          println(s"Successfully wrote to the file $outputCsvFilePath")
        case Failure(e) =>
          println(
            s"Failed to write to the file $outputCsvFilePath: ${e.getMessage}"
          )
      }

    case Left(error) =>
      println(s"Error parsing the input file: ${error.message}")
  }
}
