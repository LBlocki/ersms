import { TestBed } from '@angular/core/testing';

import { NotificationHttpInterceptorService } from './notification-http-interceptor.service';

describe('NotificationHttpInterceptorService', () => {
  let service: NotificationHttpInterceptorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotificationHttpInterceptorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
