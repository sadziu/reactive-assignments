package suggestions
package observablex

import scala.concurrent.{ Future, ExecutionContext }
import scala.util._
import scala.util.Success
import scala.util.Failure
import java.lang.Throwable
import rx.lang.scala.Observable
import rx.lang.scala.Scheduler
import rx.lang.scala.subscriptions._
import rx.lang.scala.subjects.ReplaySubject

object ObservableEx {

  /**
   * Returns an observable stream of values produced by the given future.
   * If the future fails, the observable will fail as well.
   *
   * @param f future whose values end up in the resulting observable
   * @return an observable completed after producing the value of the future, or with an exception
   */
  def apply[T](f: Future[T])(implicit execContext: ExecutionContext): Observable[T] = {
    
    /*Observable { observer =>

      f.onSuccess {
        case s => {
          observer.onNext(s);
          observer.onCompleted()
        }
      }

      f.onFailure {
        case e => {
          observer.onError(e)
        }
      }

      Subscription {}
    }*/
    
    // Note - Hint says use ReplaySubject
    
    val subject = ReplaySubject[T]()

     f.onSuccess{
       case s => {
        subject.onNext(s);
        subject.onCompleted()
        }
     }

     f.onFailure{
       case e => {
        subject.onError(e)
       }
     }

     subject
  }

}