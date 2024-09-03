package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.mvc.Result._
import models._

@Singleton
class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def get_all() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.all_products())
  }

  def get_by_id(id: Int) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.fish(ProductList.get_by_id(id)))
  }

  def delete(id: Int) = Action { implicit  request: Request[AnyContent] =>
    ProductList.delete(id)
    Ok("Deleted " + ProductList.get_by_id(id))
  }

  def post() = Action {implicit  request: Request[AnyContent] =>
    val postVals = request.body.asFormUrlEncoded
    val newProduct = new Fish(ProductList.products.last.id + 1, postVals.get("name")(0), postVals.get("colour")(0))
    ProductList.add(newProduct)
    Redirect(routes.ProductController.get_all())
  }

  def update() = Action { implicit request: Request[AnyContent] =>
    val postVals = request.body.asFormUrlEncoded
    val newProduct = new Fish(postVals.get("id")(0).toInt, postVals.get("name")(0), postVals.get("colour")(0))
    ProductList.update(postVals.get("id")(0).toInt, newProduct)
    Redirect(routes.ProductController.get_all())
  }
}