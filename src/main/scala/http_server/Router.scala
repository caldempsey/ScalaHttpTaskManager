package http_server

import akka.http.scaladsl.server.{Directives, Route}

trait Router {
  def route: Route
}

// Define a new http_server.TasksRouter which takes a TasksRepository as input (which we will enumerate).
class TasksRouter(tasksRepo: TaskRepository) extends Router with Directives {
  // Libraries required to serialise our models into JSON.
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._
  override def route: Route = pathPrefix("tasks"){
    pathEndOrSingleSlash{
      get{
        complete(tasksRepo.all())
      }
    }
  }
}

