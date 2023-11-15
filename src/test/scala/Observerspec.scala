import blokus.util.{Observable, Observer}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ObservableSpec extends AnyWordSpec with Matchers {

  "An Observable" should {

    "correctly add and notify observers" in {
      val observable = new Observable[String]()
      val observer1 = new TestObserver[String]()
      val observer2 = new TestObserver[String]()

      observable.addObserver(observer1)
      observable.addObserver(observer2)

      val testString = "test"
      observable.notifyObservers(testString)

      observer1.receivedUpdates should contain(testString)
      observer2.receivedUpdates should contain(testString)
    }

    "not notify removed observers" in {
      val observable = new Observable[String]()
      val observer1 = new TestObserver[String]()
      val observer2 = new TestObserver[String]()

      observable.addObserver(observer1)
      observable.addObserver(observer2)
      observable.removeObserver(observer1)

      val testString = "test"
      observable.notifyObservers(testString)

      observer1.receivedUpdates shouldNot contain(testString)
      observer2.receivedUpdates should contain(testString)
    }
  }

  // Helper class for testing
  class TestObserver[E] extends Observer[E] {
    var receivedUpdates: Vector[E] = Vector()

    def update(e: E): Unit = {
      receivedUpdates = receivedUpdates :+ e
    }
  }
}
