import { Component } from '@angular/core';

@Component({
  selector: 'app-restaurant-list',
  templateUrl: './restaurant-list.component.html',
  styleUrls: ['./restaurant-list.component.scss']
})
export class RestaurantListComponent {
  restaurants = [
    {
      id: 1,
      name: "pensjonat Bekas",
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc dapibus tortor eu dolor tempus venenatis. Proin commodo justo augue. Integer tempor, dolor tempus ullamcorper interdum, lorem",
      open: "4:20 - 21:37",
      lowestPrice: "30zł",
      highestPrice: "60zł",
      count: 15,
      packs: [{id: 6, name: "Bułeczki", price: "20zł"},
        {id: 6, name: "Bułeczki", price: "20zł"},
        {id: 6, name: "Bułeczki", price: "20zł"}
      ]
    }
  ]

}
