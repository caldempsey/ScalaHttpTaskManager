package http_server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import scala.util.{Failure, Success}

object ServerInit{

  private def argIsUnsignedInteger(suspectString: Int): Boolean =
    if (suspectString.toInt < 0 || Integer.MAX_VALUE < suspectString.toInt)
      false
    else
      true

  private def isAllDigits(x: String): Boolean = x forall Character.isDigit


  def run(args: Array[String]): Unit = {
    // Compile time guarantee that args length is 1 and is going to be an unsigned integer.
    assert(args.length == 1 && isAllDigits(args(0)) && argIsUnsignedInteger(args(0).toInt))
    // Runtime
    val timeout = args(0).toInt
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
    // Note Akka does not follow an idiomatic implementation... https://docs.scala-lang.org/overviews/core/futures.html
    binding.onComplete {
      // If binding.onComplete future sends a success object then print a success message. Otherwise display the error.
      // This is permissible with futures since futures hold an execution context we can pattern match against.
      case Success(_) => println("API Server initialised successfully")
      case Failure(error) => println(s"Failed to start API Server: ${error.getMessage}")
    }
    // By uncommenting these lines, if the calling thread is the main thread of the application then it will be paused until the future `binding` is completed. The result of the binding, an object, is then returned.
    // Unfortunately, with Scala's futures this is the _only_ way to get the result of the future. A Future is a placeholder for an object that does not yet exist. The future is a unit since the type cannot be enumerated (unlike Elixir where all streams are enumerable): https://stackoverflow.com/questions/17713642/accessing-value-returned-by-scala-futures
    // Block the calling thread (any executable of ServerInit) for three seconds until we get a return from server.bind.
    import scala.concurrent.Await
    import scala.concurrent.duration._
    // Awaits the result of the binding for 3 seconds, after which the Future is completed.
    // Will throw an exception if execution time takes any longer.
    Await.result(binding, timeout.seconds)
  }
}
