package com.tink.test.functionals

import com.tink.test.functionals.HttpRequest
import scala.util.parsing.json._
import org.apache.http.impl.client.BasicResponseHandler
import org.scalatest._

class TinkSpec extends FlatSpec with Matchers with HttpRequest {

  var GROUP_NAME = "Переводы"

  val response = getResponse("https://www.tinkoff.ru/api/v1/providers?groups=" + GROUP_NAME)

  "HTTP код состояния" should "соответствует 200" in {
    response.getStatusLine.getStatusCode should be (200)
  }

  val jsonString = new BasicResponseHandler().handleResponse(response)
  val result = JSON.parseFull(jsonString)
  "Возвращается документ в формате" should "json" in {
    val format = result match {
      case Some(map: Map[String, Any]) => "json"
      case None => "Parsing failed"
      case other => "Unknown data structure"
    }

    format should be ("json")
  }

  "Значение resultCode" should "равно OK" in {
    val resultCode = result match {
      case Some(m: Map[String, Any]) => m("resultCode") match {
        case s: String => s
      }
    }

    resultCode should be ("OK")
  }

  "Все значения groupId" should "равны параметру <GROUP_NAME>" in {
    val groupIdIsSameGroupName = result match {
      case Some(m: Map[String,  List[Any]]) => m("payload") match {
        case payload: List[Map[String, Any]] => payload.forall(payloadItem => {
          payloadItem("groupId") match {
            case s: String => s == GROUP_NAME
          }
        })
      }
    }

    groupIdIsSameGroupName should be (true)
  }

  "Для каждого id равного lastName" should "параметр name содержит подстроку Фамилия" in {
    result match {
      case Some(m: Map[String,  List[Any]]) => m("payload") match {
        case payload: List[Map[String, Any]] => payload.foreach(p =>
          p("providerFields") match {
            case providerFields: List[Map[String, Any]] => providerFields.foreach(pf => {
              info(pf("id").asInstanceOf[String] + " " + pf("name").asInstanceOf[String])
              if (pf("id").asInstanceOf[String] == "lastName")
                pf("name").asInstanceOf[String] should be ("Фамилия")
            })
          }
        )
      }
    }
  }


}