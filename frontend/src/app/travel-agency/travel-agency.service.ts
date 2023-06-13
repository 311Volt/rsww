import {Injectable, OnInit} from "@angular/core";
import {Offer} from "./model/offer.model";
import {User} from "../auth/user.model";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../auth/auth.service";
import {of} from "rxjs";
import {HotelModel} from "./model/hotel.model";


@Injectable({providedIn: 'root'})
export class TravelAgencyService{

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {
  }

  public getOffers() {
    //return this.offersList;
    return this.http.get<Offer[]>('http://localhost:1438/api/offer/offers');
  }

  bookOffer(offer: Offer, id: string, numberOfOffers: number, numberOfSingleRooms: number, numberOfDoubleRooms: number,
            numberOfTrRooms: number, userEmail: string, chooseFlight:string) {

    this.http.post<any>('http://localhost:1438/api/order', {
      offerId: offer.id,
      paid: true,
      clientId: this.authService.getUserId(),
      price: offer.suggestedPrice,
      departureAirportName: chooseFlight,
      nrOfPeople: numberOfOffers,
      numSingleRooms: numberOfSingleRooms,
      numDoubleRooms: numberOfDoubleRooms,
      numTripleRooms: numberOfTrRooms,
      orderStatus: 'CREATED'
    }).subscribe(res => {
      console.log(res)
    });
  }

  getHotel(code: string) {
    return this.http.get<HotelModel>('http://localhost:1438/api/hotel/' + code);
  }

  makeReservation(offer: Offer, id: string, numberOfOffers: number, singleRoomsNumber: number, doubleRoomsNumber: number,
                  tripleRoomsNumber: number, userEmail: string, chooseFlight: string) {
    this.http.post<any>('http://localhost:1438/api/order', {
      offerId: offer.id,
      clientId: this.authService.getUserId(),
      price: offer.suggestedPrice,
      departureAirportName: chooseFlight,
      nrOfPeople: numberOfOffers,
      numSingleRooms: singleRoomsNumber,
      numDoubleRooms: doubleRoomsNumber,
      numTripleRooms: tripleRoomsNumber,
      orderStatus: 'CREATED'
    }).subscribe(res => {
      console.log(res)
    });
  }
}
