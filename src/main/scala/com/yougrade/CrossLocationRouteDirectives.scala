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

trait CrossDomainOptionsDirectives extends RouteDirectives {
  implicit def withOptions(origin: String,allowMethods: String) =
    new CompletionMagnet {
      def route: StandardRoute = 
        new CompletionRoute(OK, 
              RawHeader("Access-Control-Allow-Origin", origin) :: 
              RawHeader("Access-Control-Allow-Methods", allowMethods) ::
              RawHeader("Access-Control-Allow-Headers","content-type,x-requested-with") ::              
              Nil)
    }

  private class CompletionRoute(status: StatusCode, headers: List[HttpHeader])
    extends StandardRoute {

    def apply(ctx: RequestContext) {
      ctx.complete(status, headers,"")
    }
  }
}
