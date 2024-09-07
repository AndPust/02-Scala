package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._
import scala.collection.mutable.ListBuffer;

@Singleton
class CartController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def get_all() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.all_cart())
  }

  def post() = Action { implicit request: Request[AnyContent] =>
    val postVals = request.body.asFormUrlEncoded

    postVals match {
      case Some(data) =>
        if (data.contains("id")) { 
          var id = postVals.get("id")(0).toIntOption.getOrElse(0)
          var r = CartList.add_id(id)
          if (r.startsWith("No")) {
            BadRequest("Product with the id " + id.toString() + " doesn't exist!")
          } else {
            Ok("Added product id of " + id.toString() + " to the cart")
            Redirect(routes.CartController.get_all())
          }
        } else {
            BadRequest("No id in the request!")
        }
      case None => BadRequest("No id in the request!")
    }
  }

  def delete() = Action { implicit request: Request[AnyContent] =>
    val postVals = request.body.asFormUrlEncoded

    postVals match {
      case Some(data) =>
        if (data.contains("id")) { 
          var id = postVals.get("id")(0).toIntOption.getOrElse(0)
          var r = CartList.delete_id(id)
          if (r.startsWith("No")) {
            BadRequest("Product with the id " + id.toString() + " not found in cart or doesn't exist!")
          } else {
            Ok("Removed one product with id " + id.toString() + " from the cart")
            Redirect(routes.CartController.get_all())
          }
        } else {
            BadRequest("No id in the request!")
        }
      case None => BadRequest("No id in the request!")
    }
  }

  def delete_all() = Action { implicit request: Request[AnyContent] =>
    val postVals = request.body.asFormUrlEncoded

    postVals match {
      case Some(data) =>
        if (data.contains("id")) { 
          var id = postVals.get("id")(0).toIntOption.getOrElse(0)
          var r = CartList.delete_all_id(id)
          if (r.startsWith("No")) {
            BadRequest("Product with the id " + id.toString() + " not found in cart or doesn't exist!")
          } else {
            Ok("Removed all products with id " + id.toString() + " from the cart")
            Redirect(routes.CartController.get_all())
          }
        } else {
            BadRequest("No id in the request!")
        }
      case None => BadRequest("No id in the request!")
    }
  }
}