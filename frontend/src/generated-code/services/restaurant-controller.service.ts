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

import { ChangeMenuItemStateByRestaurantRequest } from '../models/change-menu-item-state-by-restaurant-request';
import { CreateNewMenuItemRequest } from '../models/create-new-menu-item-request';
import { CreateNewRestaurantRequest } from '../models/create-new-restaurant-request';
import { FetchRestaurantMenuItemsRequest } from '../models/fetch-restaurant-menu-items-request';
import { MenuItemDto } from '../models/menu-item-dto';
import { RestaurantDto } from '../models/restaurant-dto';

@Injectable({
  providedIn: 'root',
})
export class RestaurantControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation fetchRestaurantMenuItems
   */
  static readonly FetchRestaurantMenuItemsPath = '/api/restaurant/menu-items/fetch';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `fetchRestaurantMenuItems()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  fetchRestaurantMenuItems$Response(params: {
    body: FetchRestaurantMenuItemsRequest
  },
  context?: HttpContext

): Observable<StrictHttpResponse<Array<MenuItemDto>>> {

    const rb = new RequestBuilder(this.rootUrl, RestaurantControllerService.FetchRestaurantMenuItemsPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json',
      context: context
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<MenuItemDto>>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `fetchRestaurantMenuItems$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  fetchRestaurantMenuItems(params: {
    body: FetchRestaurantMenuItemsRequest
  },
  context?: HttpContext

): Observable<Array<MenuItemDto>> {

    return this.fetchRestaurantMenuItems$Response(params,context).pipe(
      map((r: StrictHttpResponse<Array<MenuItemDto>>) => r.body as Array<MenuItemDto>)
    );
  }

  /**
   * Path part for operation createNewMenuItem
   */
  static readonly CreateNewMenuItemPath = '/api/restaurant/menu-items/create';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createNewMenuItem()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createNewMenuItem$Response(params: {
    body: CreateNewMenuItemRequest
  },
  context?: HttpContext

): Observable<StrictHttpResponse<MenuItemDto>> {

    const rb = new RequestBuilder(this.rootUrl, RestaurantControllerService.CreateNewMenuItemPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json',
      context: context
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<MenuItemDto>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createNewMenuItem$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createNewMenuItem(params: {
    body: CreateNewMenuItemRequest
  },
  context?: HttpContext

): Observable<MenuItemDto> {

    return this.createNewMenuItem$Response(params,context).pipe(
      map((r: StrictHttpResponse<MenuItemDto>) => r.body as MenuItemDto)
    );
  }

  /**
   * Path part for operation changeMenuItemStateByRestaurant
   */
  static readonly ChangeMenuItemStateByRestaurantPath = '/api/restaurant/menu-items/change-state';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `changeMenuItemStateByRestaurant()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  changeMenuItemStateByRestaurant$Response(params: {
    body: ChangeMenuItemStateByRestaurantRequest
  },
  context?: HttpContext

): Observable<StrictHttpResponse<MenuItemDto>> {

    const rb = new RequestBuilder(this.rootUrl, RestaurantControllerService.ChangeMenuItemStateByRestaurantPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json',
      context: context
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<MenuItemDto>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `changeMenuItemStateByRestaurant$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  changeMenuItemStateByRestaurant(params: {
    body: ChangeMenuItemStateByRestaurantRequest
  },
  context?: HttpContext

): Observable<MenuItemDto> {

    return this.changeMenuItemStateByRestaurant$Response(params,context).pipe(
      map((r: StrictHttpResponse<MenuItemDto>) => r.body as MenuItemDto)
    );
  }

  /**
   * Path part for operation createNewRestaurant
   */
  static readonly CreateNewRestaurantPath = '/api/restaurant/create';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createNewRestaurant()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createNewRestaurant$Response(params: {
    body: CreateNewRestaurantRequest
  },
  context?: HttpContext

): Observable<StrictHttpResponse<RestaurantDto>> {

    const rb = new RequestBuilder(this.rootUrl, RestaurantControllerService.CreateNewRestaurantPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json',
      context: context
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<RestaurantDto>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createNewRestaurant$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createNewRestaurant(params: {
    body: CreateNewRestaurantRequest
  },
  context?: HttpContext

): Observable<RestaurantDto> {

    return this.createNewRestaurant$Response(params,context).pipe(
      map((r: StrictHttpResponse<RestaurantDto>) => r.body as RestaurantDto)
    );
  }

  /**
   * Path part for operation fetchOwnRestaurant
   */
  static readonly FetchOwnRestaurantPath = '/api/restaurant/fetch';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `fetchOwnRestaurant()` instead.
   *
   * This method doesn't expect any request body.
   */
  fetchOwnRestaurant$Response(params?: {
  },
  context?: HttpContext

): Observable<StrictHttpResponse<RestaurantDto>> {

    const rb = new RequestBuilder(this.rootUrl, RestaurantControllerService.FetchOwnRestaurantPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json',
      context: context
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<RestaurantDto>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `fetchOwnRestaurant$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  fetchOwnRestaurant(params?: {
  },
  context?: HttpContext

): Observable<RestaurantDto> {

    return this.fetchOwnRestaurant$Response(params,context).pipe(
      map((r: StrictHttpResponse<RestaurantDto>) => r.body as RestaurantDto)
    );
  }

  /**
   * Path part for operation deleteMenuItem
   */
  static readonly DeleteMenuItemPath = '/api/restaurant/menu-items/delete/{menuItemId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteMenuItem()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteMenuItem$Response(params: {
    menuItemId: number;
  },
  context?: HttpContext

): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, RestaurantControllerService.DeleteMenuItemPath, 'delete');
    if (params) {
      rb.path('menuItemId', params.menuItemId, {});
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
   * To access the full response (for headers, for example), `deleteMenuItem$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteMenuItem(params: {
    menuItemId: number;
  },
  context?: HttpContext

): Observable<void> {

    return this.deleteMenuItem$Response(params,context).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation deleteRestaurant
   */
  static readonly DeleteRestaurantPath = '/api/restaurant/delete';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteRestaurant()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteRestaurant$Response(params?: {
  },
  context?: HttpContext

): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, RestaurantControllerService.DeleteRestaurantPath, 'delete');
    if (params) {
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
   * To access the full response (for headers, for example), `deleteRestaurant$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteRestaurant(params?: {
  },
  context?: HttpContext

): Observable<void> {

    return this.deleteRestaurant$Response(params,context).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
