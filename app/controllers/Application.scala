package controllers

import java.util.concurrent.{ConcurrentSkipListSet, TimeUnit}

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.collection.JavaConversions._

object Application extends Controller {

  val contentForm = Form(single("content" -> text))

  val contentCache = new ConcurrentSkipListSet[String]

  def main = Action {
    Ok(views.html.index("Gordon传送", contentCache.toList))
  }

  def index = Action { implicit request =>
    contentForm.bindFromRequest().fold(
      hasErrors => {
        Redirect(routes.Application.main())
      },
      content => {
        contentCache.clear()
        contentCache.add(content)
        Redirect(routes.Application.main())
      })
  }
}