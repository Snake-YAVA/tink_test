package com.tink.test.functionals

import org.apache.http.client.methods.{HttpGet}
import org.apache.http.impl.client.DefaultHttpClient

trait HttpRequest  {

  def getResponse(url: String) = {
    val httpclient = new DefaultHttpClient
    val httpget = new HttpGet(url)

    val response = httpclient.execute(httpget)
    response
  }

}