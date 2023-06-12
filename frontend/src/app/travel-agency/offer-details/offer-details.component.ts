import {Component, Injectable, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Offer} from "../model/offer.model";
import {TravelAgencyService} from "../travel-agency.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {HotelModel} from "../model/hotel.model";
import {AuthService} from "../../auth/auth.service";

declare var SockJS;
declare var Stomp;

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
  mes: string;

  numberOfOffers = 1;
  ageRange0NumberOfPeople = 0;
  ageRange1NumberOfPeople = 0;
  ageRange2NumberOfPeople = 0;
  singleRoomsNumber = 0;
  doubleRoomsNumber = 0;
  tripleRoomsNumber = 0;

  userEmail: string;
  chooseFlight: string = '';
  mess = ''

  constructor(private travelService: TravelAgencyService, private route: ActivatedRoute, private router: Router, private userService: AuthService) {
    this.initializeWebSocketConnection();
  }

  public stompClient;
  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:1438/rsww';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    // tslint:disable-next-line:only-arrow-functions
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe('//req7topic/purchase/' + this.offer.id, (message) => {
        if (message.body) {
          this.mess = message.body;
        }
      });
    });
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
    });
  }

  bookOffer() {
    let err = false;
    this.mes = '';
    if (this.offer.numberOfOffers < this.numberOfOffers) {
      this.mes += 'There aren\'t as many offers available. ';
      err = true;
    }
    const all = +this.ageRange0NumberOfPeople + +this.ageRange2NumberOfPeople + +this.ageRange1NumberOfPeople;
    if (all !== this.numberOfOffers) {
      this.mes += 'You need to specify the age group for all. ';
      err = true;
    }
    if (this.chooseFlight === '') {
      this.mes += 'You need to choose flight code. ';
      err = true;
    }
    if (err === false) {
      this.travelService.bookOffer(this.offer, this.offer.id, this.numberOfOffers, this.singleRoomsNumber, this.doubleRoomsNumber, this.tripleRoomsNumber, this.userEmail, this.chooseFlight);
      this.router.navigate(['travel']);
    }
  }

  calculateCost($event: Event) {
    this.offer.suggestedPrice = (this.numberOfOffers * this.basePrice);
    if (this.ageRange0NumberOfPeople !== 0) {
      this.offer.suggestedPrice = this.offer.suggestedPrice - ((this.hotel.ageRange2.pricePerNight - this.hotel.ageRange0.pricePerNight) * this.ageRange0NumberOfPeople);
      console.log(this.offer.suggestedPrice)
    }
    if (this.ageRange1NumberOfPeople !== 0) {
      this.offer.suggestedPrice = this.offer.suggestedPrice - ((this.hotel.ageRange2.pricePerNight - this.hotel.ageRange1.pricePerNight) * this.ageRange1NumberOfPeople);
      console.log(this.offer.suggestedPrice)
    }
  }

  chooseFlightCode(code:string){
    this.chooseFlight = code;
  }
}
