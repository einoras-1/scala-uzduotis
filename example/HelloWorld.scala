package example

import org.scalajs.dom._
import com.xored.scalajs.react._
import com.xored.scalajs.react.util.TypedEventListeners

import com.xored.scalajs.react.util._

import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom.extensions.KeyCode

object HelloWorld extends TypedReactSpec with TypedEventListeners{
  case class ItemData(uuid: String, price: Double)
  case class Props()
  case class State(secondsElapsed: Int, interval: Option[Int], items:Vector[ItemData], text:String, totalPrice:Double)

  def getInitialState(self: This) = State(secondsElapsed = 0, interval = None, items = Vector(), text = "Hello worrtld!", totalPrice = 0)

  implicit class Closure(self: This) {
    import self._

    val tick = () => {
      setState(state.copy(secondsElapsed = state.secondsElapsed + 1))
    }
//
    val onChange = input.onChange(e => {
      setState(state.copy(text = e.target.value))
    })
//
    val onClick = button.onClick(e => {
        setState(state.copy(text = "asdasd"))

      val todo = ItemData(uuid = java.util.UUID.randomUUID().toString, price = 0)

      setState(state.copy(
        items = todo +: state.items
      ))
    })

    def handleCount(item : ItemData, number : Double) = {
      val idx = state.items.indexOf(item)
      console.log("TOTOTOTnumber: " + number)
      val newItems = state.items.updated(idx, item.copy(price = number))
      val count : Double = countTotalPrice()
      setState(state.copy(items = newItems, totalPrice = count))

    }

    def countTotalPrice() : Double = {

      var count : Double = 0
      for (elem <- state.items) {
        console.log("totalprice: " + state.totalPrice)
        count += elem.price
        console.log("count: " + count)
      }
      return count
    }




  }

  override def componentDidMount(self: This) = {
    val interval = window.setInterval(self.tick, 1000)

    self.setState(self.state.copy(interval = Some(interval)))
  }

  override def componentWillUnmount(self: This) = {
    self.state.interval.foreach(window.clearInterval)
  }


  @scalax
  def render(self: This) = {
    <div>
      <footer id="footer">
        <ul id="todo-list">
          {
            for {
              items <- self.state.items
            } yield {
              {Item(Item.Props(
                handleCount = number => self.handleCount(items, number)
              ))}
            }
          }
        </ul>
        <p>Total: {self.state.totalPrice}</p>
        <button onClick={self.onClick}>Add Receipt</button>
      </footer>
    </div>
  }
}
