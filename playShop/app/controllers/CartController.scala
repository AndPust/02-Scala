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

  // def get_by_name(name: String) = Action { implicit request: Request[AnyContent] =>
  //   var c = CategoryList.get_by_name(name)
  //   if (c != null) {
  //     Ok(views.html.category(c))
  //   } else {
  //     NotFound(views.html.category_not_found(name))
  //   }
  // }

  // def add_category(name: String) = Action { implicit request: Request[AnyContent] =>
  //   var c = CategoryList.get_by_name(name)
  //   if (c == null) {
  //     BadRequest("Category with the name " + c.name + " already exists!")
  //   } else {
  //     CategoryList.add(new Category(ListBuffer[Int](), name))
  //     Ok("Added Category with the name of " + name)
  //   }
  // }

  // def add_id() = Action { implicit request: Request[AnyContent] =>
  //   val postVals = request.body.asFormUrlEncoded

  //   postVals match {
  //     case Some(data) =>
  //       if (data.contains("name") && data.contains("id")) { 
  //         var name = postVals.get("name")(0)
  //         var id = postVals.get("id")(0).toIntOption.getOrElse(0)
  //         var r = CategoryList.add_id(id, name)
  //         var p = ProductList.get_by_id(id)
  //         if (r.startsWith("Category not found")) {
  //           BadRequest("Category with the name " + name + " doesn't exist!")
  //         } else if (p == null) {
  //           BadRequest("Product with the id " + id + " doesn't exist!")
  //         } else {
  //           Ok("Added product id of " + id.toString() + " with the name of " + name)
  //           Redirect(routes.CategoryController.get_all())
  //         }
  //       } else {
  //           BadRequest("No category name or id in the request!")
  //       }
  //     case None => BadRequest("No category name or id in the request!")
  //   }
  // }

  // def delete_product_by_id(id: Int) = Action { implicit  request: Request[AnyContent] =>
  //   val postVals = request.body.asFormUrlEncoded

  //   if (postVals.get("id")(0) == null) {
  //     BadRequest("No product id provided in the request!")
  //   } else {
  //     if (postVals.get("name")(0).length > 0) {
  //       var r = CategoryList.delete_product_id_from_a_category(postVals.get("id")(0).toIntOption.getOrElse(0), postVals.get("name")(0))
  //       if (r.startsWith("No")) {
  //         BadRequest(r)
  //       } else {
  //         Ok(r)
  //       }
  //     } else {
  //       var r = CategoryList.delete_all_products_by_id(postVals.get("id")(0).toIntOption.getOrElse(0))
  //       if (r.startsWith("No")) {
  //         BadRequest(r)
  //       } else {
  //         Ok(r)
  //       }
  //     }
  //   }
  // }

  // def post() = Action {implicit  request: Request[AnyContent] =>
  //   val postVals = request.body.asFormUrlEncoded

  //   postVals match {
  //     case Some(data) =>
  //       if (data.contains("name")) { 
  //         var name = postVals.get("name")(0)
  //         var c = CategoryList.get_ids_by_name(postVals.get("name")(0))
  //         if (c != null) {
  //             BadRequest("Category already exists!")
  //           } else {
  //             var cat = new Category(new ListBuffer[Int](), name)
  //             CategoryList.add(cat)
  //             Ok("Added category " + name)
  //         }
  //       } else {
  //           BadRequest("No category name in the request!")
  //       }
  //     case None => BadRequest("No name in the request!")
  //   }
  // }

  // def update() = Action { implicit request: Request[AnyContent] =>
  //   val postVals = request.body.asFormUrlEncoded

  //   postVals match {
  //     case Some(data) =>
  //       if (data.contains("name") && data.contains("new_name")) { 
  //         var name = postVals.get("name")(0)
  //         var new_name = postVals.get("new_name")(0)

  //         if(new_name.length < 1) {
  //           BadRequest("Bad new name format!")
  //         } else {
  //           var r = CategoryList.rename(name, new_name)
  //           if (r.startsWith("Category")) {
  //             BadRequest("Category " + name + " Doesn't exist!")
  //           } else {
  //             Ok("Category renamed.")
  //           }
  //         }

  //       } else {
  //           BadRequest("No category name in the request!")
  //       }
  //     case None => BadRequest("No name nor new name in the request!")
  //   }
  // }


  // def delete() = Action { implicit  request: Request[AnyContent] =>
  //   val postVals = request.body.asFormUrlEncoded

  //   postVals match {
  //     case Some(data) =>
  //       if (data.contains("id") && data.contains("name")) {
  //         var id = postVals.get("id")(0).toIntOption.getOrElse(0)
  //         var name = postVals.get("name")(0)
  //         var r = CategoryList.delete_product_id_from_a_category(id, name)
  //         if (r.startsWith("No")) {
  //           BadRequest(r)
  //         } else {
  //           Ok(r)
  //         }
  //       } else if (data.contains("id")) {
  //         var id = postVals.get("id")(0).toIntOption.getOrElse(0)
  //         var r = CategoryList.delete_all_products_by_id(id)
  //         if (r.startsWith("No")) {
  //           BadRequest(r)
  //         } else {
  //           Ok(r)
  //         }
  //       } else if (data.contains("name")) {
  //         var name = postVals.get("name")(0)
  //         var r = CategoryList.delete(name)
  //         if (r.startsWith("Category not found")) {
  //           BadRequest("Category to be deleted does not exist!")
  //         } else {
  //           Ok("Deleted category " + name)
  //         }
  //       } else {
  //         BadRequest("No product id provided!")
  //       }
  //     case None => BadRequest("No id nor name in the request!")
  //   }
  // }
}