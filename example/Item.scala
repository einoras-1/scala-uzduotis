package example

import com.xored.scalajs.react._
import com.xored.scalajs.react.util._
import org.scalajs.dom._
import org.scalajs.dom.extensions.KeyCode

object Item extends TypedReactSpec with TypedEventListeners {

  case class ProductData(uuid: String, expenseType : String, price : Double)


  case class Props(
                    handleCount: Double => Unit
                  )

  case class State(expenseType: String, products:Vector[ProductData], price: Double, totalPrice: Double)

  def getInitialState(self: This) = State(expenseType = "rwe", products = Vector(), price = 0, totalPrice = 0)

  val editField = "editField"

  implicit class Closure(self: This) {

    import self._

    val handleChange = input.onChange(e => {
      self.setState(state.copy(expenseType = e.target.value))
      console.log(self.state.expenseType)
    })

    val handleClick = button.onClick(e => {
//      self.setState(state.copy(expenseType = e.target.value))
      val product = ProductData(uuid = java.util.UUID.randomUUID().toString, expenseType = state.expenseType, price = 0)

      setState(state.copy(
        products = product +: state.products
      ))
    })

    def handleCount(product : ProductData, number : Double) = {
      val idx = state.products.indexOf(product)
//      console.log("number: " + number)
      val newProducts = state.products.updated(idx, product.copy(price = number))
//      var count : Double = 0
      val count : Double = countTotalPrice()
      setState(state.copy(products = newProducts, totalPrice = count))
      handlePriceChange()
    }

    def countTotalPrice() : Double = {

      var count : Double = 0
      for (elem <- state.products) {
        //        console.log(elem.price)
//        console.log("totalprice: " + state.totalPrice)
//        count += elem.price
//        console.log("count: " + count)
        ////        setState(state.copy(
        ////          totalPrice = elem.price + state.totalPrice
        ////        ))
        ////        countTotalPrice(elem)
      }

      return count
    }

    def handlePriceChange() = {

//        self.setState(state.copy(price = e.target.value.toDouble))

        self.props.handleCount(state.totalPrice)

    }

  }


  @scalax
  def render(self: This) = {

    <div className="items">
      <select onChange={self.handleChange} value={self.state.expenseType}>
        <option value="houseware">Houseware</option>
        <option value="food">Food</option>
        <option value="entertainment">entertainment</option>
      </select>
      <button onClick={self.handleClick}>Add expense</button>
      <div>
        <ul id="prodcut-list">
          {
            for {
              product <- self.state.products
            } yield {
              {Product(Product.Props(
                handleCount = number => self.handleCount(product, number)
              ))}
            }
          }
        </ul>
      </div>
      <div>
        <p>Total: {self.state.totalPrice}</p>
      </div>
    </div>
  }
}
