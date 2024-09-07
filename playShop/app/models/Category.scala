package models;
import scala.collection.mutable.ListBuffer;

class Category(var ids: ListBuffer[Int], var name: String)
{
    def printList(): String = {
        var s: String = "["
        for (i <- this.ids){
            s += i.toString()
            s += ", "
        }
        if(s.length < 3) {
          return s + "]"
        } else {
          s = s.substring(0, s.length - 2)
          s += "]"
          return s
        }
    }
}

object CategoryList {
  var categories = ListBuffer(
    new Category(ListBuffer(1), "Furniture"),
    new Category(ListBuffer(2, 3), "Appliance")
  );

  def get_all(): ListBuffer[Category] ={
    return categories;
  }

  def get_by_name(name: String): Category ={
    for(c <- categories){
      if(name.equals(c.name)){
        return c;
      }
    }
      return null;
  }

  def get_names_by_id(id: Int): ListBuffer[String] ={
    var names = ListBuffer[String]()
    for (c <- categories) {
      if (this.get_by_name(c.name).ids.contains(id)) {
        names = names :+ c.name
      }
    }
    return names
  }

  def get_ids_by_name(name: String): ListBuffer[Int] ={
    for(c <- categories){
      if(name.equals(c.name)){
        return c.ids;
      }
    }
      return null;
  }

  def add(newCategory : Category): String ={
    categories = categories :+ newCategory
    return "Category added"
  }

  def add_id(id: Int, name: String): String ={
    for(c <- categories){
      if(name.equals(c.name)){
        if (c.ids.contains(id)) {
          return "Id" + id.toString() + " already within category " + c.name
        } else {
          c.ids = c.ids :+ id
          return "Id" + id.toString() + " added to category " + c.name
        }
      }
    }
    return "Category not found"
  }

  def rename(old_name: String, new_name: String): String ={
    for(c <- categories){
      if(c.name.equals(old_name)){
        c.name = new_name
        return "Renamed the category"
      }
    }
    return "Category not found"
  }

  def delete(name: String): String ={
    for(c <- categories){
      if(name.equals(c.name)){
        categories = categories -= c
        return "Deleted a Category"
      }
    }
    return "Category not found"
  }

  def delete_all_products_by_id(id: Int): String ={
    var count = 0
    // return "scooby doo"
    for(c <- categories){
      if(c.ids.contains(id)) {
        c.ids = c.ids -= id
        count += 1
      }
    }
    if (count == 0) {
      return "No products with id " + id.toString() + " found in categories."
    } else {
      return "Removed product with id " + id.toString() + " form " + count.toString() + " categories."
    }
  }

  def delete_product_id_from_a_category(id: Int, name: String): String ={
    for(c <- categories){
      if(c.name == name) {
        if(c.ids.contains(id)) {
          c.ids = c.ids -= id
          return "Deleted product with the id " + id.toString() + " from the category " + c.name + "."
        }
        return "No product id " + id.toString() + " found in the category " + name + "."
      }
    }
    return "No such category: " + name + ". No id deleted!"
  }

  def update(name: String, newCategory: Category): String ={
    for(c <- categories){
      if(name.equals(c.name)){
        this.delete(c.name)
        this.add(newCategory)
        return "Updated a Category"
      }
    }
    return "Category not found"
  }

  def add_product(name: String, productId: Int): String = {
    for(c <- categories){
      if(name.equals(c.name)){
        return "Product with the Id of " + productId.toString() + " added to Category " + name
      }
    }
    return "No Category of the name: " + name
  }
}
