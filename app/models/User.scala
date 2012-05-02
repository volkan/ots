package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class User(email: String, name: String, password: String)

object User {

  // -- Parsers

  /**
   * Parse a User from a ResultSet
   */
  val simple = {
    get[String]("user.us_email") ~
      get[String]("user.us_login_name") ~
      get[String]("user.us_password_hash") map {
      case email ~ name ~ password => User(email, name, password)
    }
  }

  // -- Queries

  /**
   * Retrieve a User from email.
   */
  def findByEmail(email: String): Option[User] = {
    DB.withConnection {
      implicit connection =>
        SQL("select * from user where us_email = {email}").on(
          'us_email -> email
        ).as(User.simple.singleOpt)
    }
  }

  /**
   * Retrieve all users.
   */
  def findAll: Seq[User] = {
    DB.withConnection {
      implicit connection =>
        SQL("select * from \"user\"").as(User.simple *)
    }
  }

  /**
   * Authenticate a User.
   */
  def authenticate(email: String, password: String): Option[User] = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
           select * from user where
           us_email = {email} and us_password = {password}
          """
        ).on(
          'us_email -> email,
          'us_password_hash -> password
        ).as(User.simple.singleOpt)
    }
  }

  /**
   * Create a User.
   */
  def create(user: User): User = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
            insert into user values (
              {email}, {name}, {password}
            )
          """
        ).on(
          'us_email -> user.email,
          'us_login_name -> user.name,
          'us_password_hash -> user.password
        ).executeUpdate()

        user

    }
  }

}
