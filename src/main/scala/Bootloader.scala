import http_server.ServerInit

// Inherit main method from App (defined by the Main object), but use a different object name.
// This means main is a class which overrides def main from App.
object Bootloader extends App {
  override def main(args: Array[String]): Unit = {
    // Initialise HTTP Server
    ServerInit.run(new Array[String](0))
  }
}
