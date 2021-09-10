package <%= organization %>

import better.files._
import org.jline.reader._
import org.jline.terminal._

import java.io.{File => JFile}
import java.nio.file.Path
import java.nio.file.Paths
import scala.language.postfixOps
import scala.sys.process._

import File._
import scopt.OParser

object Main extends App {
  val terminal: Terminal = TerminalBuilder.builder()
      .dumb(true)
      .build()
  val lineReader = LineReaderBuilder.builder()
      .terminal(terminal)
      .build()
  
  val builder = OParser.builder[Config]
  val parser1 = {
    import builder._
    OParser.sequence(
      programName(BuildInfo.name),
      head(BuildInfo.name, BuildInfo.version),
      opt[JFile]('i', "script")
        .required()
        .action((x, c) => c.copy(script = x.toScala))
        .text("the script to provision with"),
      opt[String]('a', "address")
        .required()
        .action((x, c) => c.copy(address = x))
        .text("the address of the device to provision, be it IP or MAC"),
      opt[String]('u', "user")
        .action((x, c) => c.copy(user = x))
        .text("the user, defaults to admin"),
      opt[JFile]('k', "key")
        .action((x, c) => c.copy(key = x.toScala))
        .text("the ssh key to copy onto the device"),
    )
  }

  OParser.parse(parser1, args, Config()) match {
    case Some(config: Config) =>
      run(config)
    case _ =>
      // arguments are bad, error message will have been displayed
  }

  def run(cfg: Config) = {
    ???
  }
}

case class Config(script: File = File("."), address: String = "", user: String = "admin", key: File = File(scala.sys.env("HOME"), ".ssh", "id_rsa.pub"))