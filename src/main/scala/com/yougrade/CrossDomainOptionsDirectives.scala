package com.yougrade

import spray.http._
import spray.http.HttpHeaders._
import spray.http.StatusCodes._
import spray.routing._
import spray.routing.directives._


trait CrossDomainOptionsDirectives extends RouteDirectives {
  implicit def withOptions(origin: String, methods: HttpMethod*) = {
    val allowMethods = methods.mkString(",")
    new CompletionMagnet {
      def route: StandardRoute =
        new CompletionRoute(OK,
          RawHeader("Access-Control-Allow-Origin", origin) ::
            RawHeader("Access-Control-Allow-Methods", allowMethods) ::
            RawHeader("Access-Control-Allow-Headers", "content-type,x-requested-with") ::
            Nil)
    }
  }

  private class CompletionRoute(status: StatusCode, headers: List[HttpHeader])
    extends StandardRoute {

    def apply(ctx: RequestContext) {
      ctx.complete(status, headers, "")
    }
  }

}