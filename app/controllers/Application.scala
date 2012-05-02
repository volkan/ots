package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.i18n.Messages;
import jsmessages.JsMessages
import anorm._

import models._
import views._

object Application extends Controller {

  val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) verifying ("Invalid email or password", result => result match {
      case (email, password) => true
    })
  )

  def login2 = Action { implicit request =>
    session +("success" -> "TESTTTTT");
    Redirect(routes.Application.login).withNewSession.flashing(
      "success" -> "Yönlendirme ile mesaj çıkıyor"
    )
  }
  /**
   * Login page.
   */
  def login = Action { implicit request =>
    session +("success" -> "TESTTTTT");
    flash +("success" -> "TESTTTTT");
    Ok(html.login(loginForm))
  }

  def authenticate = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def index2 = Action {
    Ok(views.html.index("Your new application is ready."))
  }


  def index = Action { implicit request =>
    //flash.+("success" -> Messages.get("youve.been.logged.out"));
    //flash("success" -> Messages.get("youve.been.logged.out"));
    val message = User.findAll.last.email
    Ok(views.html.index( "mesaj" + message ))
  }

  def jsMessages = Action { implicit request =>
    Ok(JsMessages("Messages")).as(JAVASCRIPT)
  }
  
}