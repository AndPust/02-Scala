package models;
import scala.collection.mutable.ListBuffer;

class Product(var id: Int, var name: String, var colour: String);

object ProductList {
  var products = ListBuffer(
    new Product(1, "Armchair", "Red"),
    new Product(2, "TV", "Black"),
    new Product(3, "Washing Machine", "White")
  );

  def get_all(): ListBuffer[Product] = {
    return products;
  }

  def get_first_available_id(): Int = {
    var id: Int = 1
    while (this.get_by_id(id) != null) {
      id += 1
    }
    return id
  }

  def get_by_id(id: Int): Product = {
    for(p <- products){
      if(id.equals(p.id)){
        return p;
      }
    }
      return null;
  }

  def add(newProduct : Product): String = {
    products = products :+ newProduct
    return "Product added"
  }

  def delete(id: Int): String ={
    for(p <- products){
      if(id.equals(p.id)){
        products = products -= p
        return "Deleted a Product"
      }
    }
    return "Product not found"
  }

  def update(id: Int, newProduct: Product): String = {
    for(p <- products){
      if(id.equals(p.id)){
        this.delete(id)
        this.add(newProduct)
        return "Updated a Product"
      }
    }
    return "Product not found"
  }
}
