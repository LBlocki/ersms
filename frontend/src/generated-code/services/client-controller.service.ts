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

import { ChangeMenuItemStateByClientRequest } from '../models/change-menu-item-state-by-client-request';
import { FetchClientMenuItemsRequest } from '../models/fetch-client-menu-items-request';
import { MenuItemDto } from '../models/menu-item-dto';

@Injectable({
  providedIn: 'root',
})
export class ClientControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation fetchClientMenuItemsByState
   */
  static readonly FetchClientMenuItemsByStatePath = '/api/client/menu-items/fetch-by-state';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `fetchClientMenuItemsByState()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  fetchClientMenuItemsByState$Response(params: {
    body: FetchClientMenuItemsRequest
  },
  context?: HttpContext

): Observable<StrictHttpResponse<Array<MenuItemDto>>> {

    const rb = new RequestBuilder(this.rootUrl, ClientControllerService.FetchClientMenuItemsByStatePath, 'post');
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
   * To access the full response (for headers, for example), `fetchClientMenuItemsByState$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  fetchClientMenuItemsByState(params: {
    body: FetchClientMenuItemsRequest
  },
  context?: HttpContext

): Observable<Array<MenuItemDto>> {

    return this.fetchClientMenuItemsByState$Response(params,context).pipe(
      map((r: StrictHttpResponse<Array<MenuItemDto>>) => r.body as Array<MenuItemDto>)
    );
  }

  /**
   * Path part for operation changeMenuItemStateByClient
   */
  static readonly ChangeMenuItemStateByClientPath = '/api/client/menu-items/change-state';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `changeMenuItemStateByClient()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  changeMenuItemStateByClient$Response(params: {
    body: ChangeMenuItemStateByClientRequest
  },
  context?: HttpContext

): Observable<StrictHttpResponse<MenuItemDto>> {

    const rb = new RequestBuilder(this.rootUrl, ClientControllerService.ChangeMenuItemStateByClientPath, 'post');
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
   * To access the full response (for headers, for example), `changeMenuItemStateByClient$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  changeMenuItemStateByClient(params: {
    body: ChangeMenuItemStateByClientRequest
  },
  context?: HttpContext

): Observable<MenuItemDto> {

    return this.changeMenuItemStateByClient$Response(params,context).pipe(
      map((r: StrictHttpResponse<MenuItemDto>) => r.body as MenuItemDto)
    );
  }

  /**
   * Path part for operation fetchAllPendingMenuItems
   */
  static readonly FetchAllPendingMenuItemsPath = '/api/client/menu-items/fetch';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `fetchAllPendingMenuItems()` instead.
   *
   * This method doesn't expect any request body.
   */
  fetchAllPendingMenuItems$Response(params?: {
  },
  context?: HttpContext

): Observable<StrictHttpResponse<Array<MenuItemDto>>> {

    const rb = new RequestBuilder(this.rootUrl, ClientControllerService.FetchAllPendingMenuItemsPath, 'get');
    if (params) {
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
   * To access the full response (for headers, for example), `fetchAllPendingMenuItems$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  fetchAllPendingMenuItems(params?: {
  },
  context?: HttpContext

): Observable<Array<MenuItemDto>> {

    return this.fetchAllPendingMenuItems$Response(params,context).pipe(
      map((r: StrictHttpResponse<Array<MenuItemDto>>) => r.body as Array<MenuItemDto>)
    );
  }

}
