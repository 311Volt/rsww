import {Component, Injectable, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Offer} from "../model/offer.model";
import {TravelAgencyService} from "../travel-agency.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {HotelModel} from "../model/hotel.model";

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit {
  offer: Offer;
  id: string;
  displayedColumns: string[] = ['outboundFlightAirport', 'returnFlightAirport'];
  dataSource: any;
  basePrice: number;
  hotel: HotelModel;

  ageRange0NumberOfPeople: number;
  ageRange1NumberOfPeople: number;
  ageRange2NumberOfPeople: number;
  singleRoomsNumber = 0;
  doubleRoomsNumber = 0;
  tripleRoomsNumber = 0;

  constructor(private travelService: TravelAgencyService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    const id = this.route.params.subscribe(
      (params: Params) => {
        this.id = params['id'];
        this.travelService.getOffers().subscribe(response => {
          this.offer = response.find(offer => offer.id === this.id);
          this.dataSource = this.offer.flights;
          console.log(this.dataSource)
          this.basePrice = this.offer.suggestedPrice;
          this.travelService.getHotel(this.offer.hotelBrief.id).subscribe(hotel => {
            this.hotel = hotel;
          });
          console.log('done')
        })
      }
    );
  }


  bookOffer() {
    this.travelService.bookOffer(this.offer, this.offer.id);
  }

  calculateCost($event: Event) {
    this.offer.suggestedPrice = +(event.target as HTMLInputElement).value * this.basePrice;
  }
}
