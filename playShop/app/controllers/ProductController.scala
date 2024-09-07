package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._

@Singleton
class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def get_all() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.all_products())
  }

  def get_by_id(id: Int) = Action { implicit request: Request[AnyContent] =>
    var p = ProductList.get_by_id(id)
    if (p == null) {
      NotFound(views.html.product_not_found(id))
    } else {
      Ok(views.html.product(p))
    }
  }

  def delete(id: Int) = Action { implicit  request: Request[AnyContent] =>
    var r = ProductList.delete(id)
    if (r == "Product not found") {
      NotFound("Product not found. No deletion happened!")
    } else {
      CategoryList.delete_all_products_by_id(id)
      CartList.delete_all_id(id)
      Ok("Deleted Product with id of " + id)
    }
  }

  def post() = Action {implicit  request: Request[AnyContent] =>
    val postVals = request.body.asFormUrlEncoded
    postVals match {
      case Some(data) =>
        if (data.contains("name")) { 
          var name = postVals.get("name")(0)
          var colour = ""
          if (data.contains("colour")) { colour = postVals.get("colour")(0) } else { colour = "default_colour" }
          if (colour.length == 0 ) { colour = "default_colour" }
          val newProduct = new Product(ProductList.get_first_available_id(), name, colour)
          ProductList.add(newProduct)
          Ok("Added Product with id of " + newProduct.id)
          Redirect(routes.ProductController.get_all())
        } else {
          BadRequest("No product name in the request!")
        }
      case None => BadRequest("No name in the request!")
    }
  }

  def update() = Action { implicit request: Request[AnyContent] =>
    val postVals = request.body.asFormUrlEncoded
    postVals match {
      case Some(data) =>
        if (data.contains("name") && (data.contains("id"))) { 
          var name = postVals.get("name")(0)
          var id = postVals.get("id")(0).toIntOption.getOrElse(0)
          var colour = ""
          if (data.contains("colour")) { colour = postVals.get("colour")(0) } else { colour = "default_colour" }
          if (colour.length == 0 ) { colour = "default_colour" }
          val newProduct = new Product(id, name, colour)
          var r = ProductList.update(id, newProduct)
          if (r == "Product not found") {
            NotFound("Product not found. No update happened!")
          } else {
            Ok("Updated Product with id of " + postVals.get("id")(0).toInt)
          }
        } else {
          BadRequest("No product name or id in the request!")
        }
      case None => BadRequest("No name or id in the request!")
    }
  }
}
