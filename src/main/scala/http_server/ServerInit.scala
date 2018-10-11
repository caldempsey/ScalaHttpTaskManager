package http_server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.util.{Failure, Success}

object ServerInit{
  def run(args: Array[String]){
    val host = "localhost"
    val port = 9000
    // Import Akka ActorSystem and Materializer
    // Import Dispatcher (implicit).
    implicit val system: ActorSystem = ActorSystem(name = "todoapi")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    import system.dispatcher
    // Create a new task repository and assign to the router.
    val router = new TasksRouter(new TaskRepository())
    // Import Akka directive (used to create arbitrarily complex route structures).
    val server = new Server(router, host, port)
    // Acquire a binding from the server.
    val binding = server.bind()
    // Pattern matching when the servers "bind" property completes.
    binding.onComplete {
      case Success(_) => println("Success")
      case Failure(error) => println(s"Failed: ${error.getMessage}")
    }
  }
}
