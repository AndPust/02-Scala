package models;
import scala.collection.mutable.ListBuffer;

object CartList {
  var items: ListBuffer[(Int, Int)] = ListBuffer[(Int, Int)]()

  def get_all(): ListBuffer[(Int, Int)] = {
    return items;
  }

  def add_id(id: Int): String ={
    if (ProductList.get_by_id(id) == null) {
      return "No product with id " + id.toString() + " !"
    }

    for (i <- items.indices) {
      if (items(i)._1 == id) {
      items(i) = (items(i)._1, items(i)._2 + 1)
      return  "Incremented number of products with id " + id.toString()
      }
    }
    items.append((id, 1))
    return "Product with id " + id.toString() + " added to cart."
  }

  def delete_id(id: Int): String ={
    if (ProductList.get_by_id(id) == null) {
      return "No product with id " + id.toString() + " !"
    }

    for (i <- items.indices) {
      if (items(i)._1 == id) {
        val count = items(i)._2
        items(i) = (items(i)._1, count - 1)
        if (count == 1) {
          items.remove(i)
          return "Removed product id " + id.toString() + " from the cart."
        }
        return "Removed one product of id " + id.toString() + " from the cart."
      }
    }
    return "No product with id " + id.toString() + " found in the cart!"
  }

  def delete_all_id(id: Int): String ={
    for (i <- items.indices) {
      if (items(i)._1 == id) {
        items.remove(i)
        return "Removed all products with id " + id.toString() + " from the cart."
      }
    }
    return "No product with id " + id.toString() + " found in the cart!"
  }


}
