package http_server

import scala.concurrent.{ExecutionContext, Future}

// Define a new trait of functions to manage a repository of Tasks.
trait TaskRepositories {
  def all(): Future[Seq[Task]]
  def notExecuted(): Future[Seq[Task]]
  def executed(): Future[Seq[Task]]
}

// Defines a http_server.TaskRepository from the http_server.TaskRepositories trait which expects an implicit execution context to exist in scope.
class TaskRepository(initTasks: Seq[Task] = Seq.empty)(implicit ec: ExecutionContext) extends TaskRepositories {
  ///Constructor parameters in scala are automatically fields/properties.
  // Essentially, it's automatically doing a this.tasks = tasks for you
  private var tasks: Vector[Task] = initTasks.toVector // Vectors are an implementation of a sequence (trait) with random access and immutability.
  // Currying example, great tutorial on this here https://www.youtube.com/watch?v=Vriy8D97kwE.
  override def all(): Future[Seq[Task]] = Future.successful(tasks)
  override def notExecuted(): Future[Seq[Task]] = Future.successful(tasks.filter(_.timesExecuted <= 0)) // If times executed is smaller than or equal to 0 then the task has yet to be executed.
  override def executed(): Future[Seq[Task]] = Future.successful(tasks.filterNot(_.timesExecuted <= 0)) // If times executed is not smaller than or equal to 0 then the task has been executed at least once.
}