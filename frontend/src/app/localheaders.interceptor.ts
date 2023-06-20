import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocalheadersInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const modifiedRequest = request.clone({
      setHeaders: {
        'Local-roles': 'ROLE_RESTAURANT_OWNER',
        'Local-username': 'ef7c8173-1a59-4920-bb41-baf1ed62611a'
      }
    });

    return next.handle(modifiedRequest);
  }
}
