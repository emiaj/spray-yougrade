package com.yougrade

import spray.http._
import spray.http.HttpHeaders._
import spray.http.StatusCodes._
import spray.httpx.marshalling._
import spray.routing._
import spray.routing.directives._

trait CrossLocationRouteDirectives extends RouteDirectives {
  implicit def fromObjectCross[T: Marshaller](origin: String)(obj: T) =
    new CompletionMagnet {
      def route: StandardRoute = 
        new CompletionRoute(OK, 
              RawHeader("Access-Control-Allow-Origin", origin) :: Nil, obj)
    }

  private class CompletionRoute[T: Marshaller](status: StatusCode, 
                                               headers: List[HttpHeader], 
                                               obj: T)
    extends StandardRoute {

    def apply(ctx: RequestContext) {
      ctx.complete(status, headers, obj)
    }
  }
}