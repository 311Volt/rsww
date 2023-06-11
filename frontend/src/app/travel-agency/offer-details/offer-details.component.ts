import {Component, Injectable, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Offer} from "../model/offer.model";
import {TravelAgencyService} from "../travel-agency.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {HotelModel} from "../model/hotel.model";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit {
  offer: Offer;
  id: string;
  displayedColumns: string[] = ['outboundFlightAirport', 'returnFlightAirport', 'choose'];
  dataSource: any;
  basePrice: number;
  hotel: HotelModel;

  numberOfOffers = 1;
  ageRange0NumberOfPeople: number;
  ageRange1NumberOfPeople: number;
  ageRange2NumberOfPeople: number;
  singleRoomsNumber = 0;
  doubleRoomsNumber = 0;
  tripleRoomsNumber = 0;

  userEmail: string;
  chooseFlight: string = '';

  constructor(private travelService: TravelAgencyService, private route: ActivatedRoute, private router: Router, private userService: AuthService) {
  }

  ngOnInit(): void {
    const id = this.route.params.subscribe(
      (params: Params) => {
        this.id = params['id'];
        this.travelService.getOffers().subscribe(response => {
          this.offer = response.find(offer => offer.id === this.id);
          this.dataSource = this.offer.flights;
          this.basePrice = this.offer.suggestedPrice;
          this.travelService.getHotel(this.offer.hotelBrief.id).subscribe(hotel => {
            this.hotel = hotel;
            console.log(hotel)
          });
        })
      }
    );
    this.userService.user.subscribe(user => {
      this.userEmail = user.email;
    })
  }

  bookOffer() {
    this.travelService.bookOffer(this.offer, this.offer.id, this.numberOfOffers, this.singleRoomsNumber, this.doubleRoomsNumber, this.tripleRoomsNumber, this.userEmail, this.chooseFlight);
    this.router.navigate(['travel']);
  }

  calculateCost($event: Event) {
    this.offer.suggestedPrice = +(event.target as HTMLInputElement).value * this.basePrice;
  }

  calculateCostRange2($event: Event) {
    this.offer.suggestedPrice = this.offer.suggestedPrice - (this.hotel.ageRange2.pricePerNight - this.hotel.ageRange1.pricePerNight) * +(event.target as HTMLInputElement).value;
  }

  calculateCostRange1($event: Event) {
    this.offer.suggestedPrice = this.offer.suggestedPrice - (this.hotel.ageRange2.pricePerNight - this.hotel.ageRange0.pricePerNight) * +(event.target as HTMLInputElement).value;
  }

  chooseFlightCode(code:string){
    this.chooseFlight = code;
  }
}
