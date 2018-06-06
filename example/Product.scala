package example

import com.xored.scalajs.react._
import com.xored.scalajs.react.util._
//import example.HelloWorld.Todo
import org.scalajs.dom._
import org.scalajs.dom.extensions.KeyCode

object Product extends TypedReactSpec with TypedEventListeners {

  case class ProductData(expenseType : String, price : Double)


  case class Props(
                    handleCount: Double => Unit
                  )

  case class State(product: String, price: Double)

  def getInitialState(self: This) = State(product = "", price = 0)

  val editField = "editField"

  implicit class Closure(self: This) {

    import self._

    val handleChange = input.onChange(e => {
      self.setState(state.copy(product = e.target.value))
    })

    val handlePriceChange = input.onChange(e => {
      if(e.target.value.nonEmpty) {
        self.setState(state.copy(price = e.target.value.toDouble))

        self.props.handleCount(state.price)
      }
    })


  }


  @scalax
  def render(self: This) = {

    <div className="items">
      <input id="new-product"
             onChange={self.handleChange}
             value={self.state.product}
             autofocus={true}></input>
      <input id="new-price"
             type="number"
             onChange={self.handlePriceChange}></input>
    </div>
  }
}
