/* tslint:disable */
/* eslint-disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpContext } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { RequestBuilder } from '../request-builder';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';


@Injectable({
  providedIn: 'root',
})
export class AdminControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation approveRestaurant
   */
  static readonly ApproveRestaurantPath = '/api/admin/restaurant/approve/{restaurantId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `approveRestaurant()` instead.
   *
   * This method doesn't expect any request body.
   */
  approveRestaurant$Response(params: {
    restaurantId: number;
  },
  context?: HttpContext

): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, AdminControllerService.ApproveRestaurantPath, 'post');
    if (params) {
      rb.path('restaurantId', params.restaurantId, {});
    }

    return this.http.request(rb.build({
      responseType: 'text',
      accept: '*/*',
      context: context
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return (r as HttpResponse<any>).clone({ body: undefined }) as StrictHttpResponse<void>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `approveRestaurant$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  approveRestaurant(params: {
    restaurantId: number;
  },
  context?: HttpContext

): Observable<void> {

    return this.approveRestaurant$Response(params,context).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation deleteRestaurant1
   */
  static readonly DeleteRestaurant1Path = '/api/admin/restaurant/delete/{restaurantId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteRestaurant1()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteRestaurant1$Response(params: {
    restaurantId: number;
  },
  context?: HttpContext

): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, AdminControllerService.DeleteRestaurant1Path, 'delete');
    if (params) {
      rb.path('restaurantId', params.restaurantId, {});
    }

    return this.http.request(rb.build({
      responseType: 'text',
      accept: '*/*',
      context: context
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return (r as HttpResponse<any>).clone({ body: undefined }) as StrictHttpResponse<void>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteRestaurant1$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteRestaurant1(params: {
    restaurantId: number;
  },
  context?: HttpContext

): Observable<void> {

    return this.deleteRestaurant1$Response(params,context).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
