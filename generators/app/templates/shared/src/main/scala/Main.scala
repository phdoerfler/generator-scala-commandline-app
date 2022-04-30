<% if (! libs.includes("scalanative")) { %>
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

<% if (libs.includes("scalapy")) { %>
import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.writableSeqElem
import me.shadaj.scalapy.py.SeqConverters
<% } %>


object Main {
  def main(args: Array[String]): Unit = {
    // val terminal: Terminal = TerminalBuilder.builder()
    //     .dumb(true)
    //     .build()
    // val lineReader = LineReaderBuilder.builder()
    //     .terminal(terminal)
    //     .build()
    
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
        opt[String]('u', "user")
          .action((x, c) => c.copy(user = x))
          .text("the user, defaults to admin")
      )
    }

    OParser.parse(parser1, args, Config()) match {
      case Some(config: Config) =>
        run(config)
      case _ =>
        // arguments are bad, error message will have been displayed
    }

    def run(cfg: Config) = {
      println(s"Running with config $cfg")
  <% if (libs.includes("scalapy")) { %>
      val listLengthPython = py.Dynamic.global.len(List(1, 2, 3).toPythonProxy)
      println(listLengthPython)
  <% } %>
    }
  }
}

case class Config(script: File = File("."), user: String = "admin")
<% } %>
<% if (libs.includes("scalanative")) { %>
object Main {
  def main(args: Array[String]): Unit = {
    println("Hello!")
  }
}
<% } %>
