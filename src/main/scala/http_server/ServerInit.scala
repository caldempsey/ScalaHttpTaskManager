package http_server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import scala.util.{Failure, Success}

object ServerInit{
  def run(args: Array[String]): String = {
    val host = "localhost"
    val port = 9000
    // Import Akka ActorSystem and Materializer
    // Import Dispatcher (implicit).
    implicit val system: ActorSystem = ActorSystem(name = "TaskManagerAPI")
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
      case Success(_) => println("API Server initialised successfully")
      case Failure(error) => println(s"Failed to start API Server: ${error.getMessage}")
    }
    // By uncommenting these lines, if the calling thread is the main thread of the application then it will be paused until the future `binding` is completed.
    // Implements an implicit call to Java's TimeUnit.
    // Block the calling thread (any executable of ServerInit) for three seconds until we get a return from server.bind.
    // import scala.concurrent.Await
    // import scala.concurrent.duration._
    // Await.result(binding, 3.seconds)
  }
}
