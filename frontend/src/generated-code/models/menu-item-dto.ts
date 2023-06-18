/* tslint:disable */
/* eslint-disable */
export interface MenuItemDto {
  description?: string;
  id?: number;
  name?: string;
  price?: number;
  restaurantId?: number;
  state?: 'AVAILABLE' | 'PENDING' | 'RESERVED' | 'FINISHED';
}
