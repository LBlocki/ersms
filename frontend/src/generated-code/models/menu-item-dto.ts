/* tslint:disable */
/* eslint-disable */
export interface MenuItemDto {
  description?: string;
  id?: number;
  name?: string;
  price?: number;
  restaurantName?: string;
  restaurantId?: number;
  state?: 'AVAILABLE' | 'PENDING' | 'RESERVED' | 'FINISHED';
}
