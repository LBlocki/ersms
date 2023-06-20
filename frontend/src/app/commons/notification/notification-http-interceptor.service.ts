import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NotificationHttpInterceptorService implements HttpInterceptor {

  constructor(private toastrService: ToastrService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status !== 401) {
          this.showError(error.status + ': Error during server communication', error.message);
        }
        return throwError(error);
      })
    );
  }

  private showError(title: string, message: string) {
    this.toastrService.error(message, title, {
      disableTimeOut: true,
      tapToDismiss: true,
      newestOnTop: true
    });
  }
}
