package models;
import scala.collection.mutable.ListBuffer;

class Product(var id: Int, var name: String, var colour: String);

object ProductList {
  var products = ListBuffer(
    new Product(1, "TV", "Black"),
    new Product(2, "Washing Machine", "White"),
    new Product(3, "Air Fryer", "Grey")
  );

  def get_all(): ListBuffer[Product] ={
    return products;
  }

  def get_by_id(id: Int): Product ={
    for(p <- products){
      if(id.equals(p.id)){
        return p;
      }
    }
    return null;
  }

  def add(newProduct : Product): String ={
    products = products :+ newProduct
    return "Product added"
  }

  def delete(id: Int): String ={
    for(p <- products){
      if(id.equals(p.id)){
        products = products -= p
        return "Deleted a product"
      }
    }
    return "Product not found"
  }

  def update(id: Int, newProduct: Product): String ={
    for(p <- products){
      if(id.equals(p.id)){
        this.delete(id)
        this.add(newProduct)
        return "Updated a product"
      }
    }
    return "Product not found"
  }
}