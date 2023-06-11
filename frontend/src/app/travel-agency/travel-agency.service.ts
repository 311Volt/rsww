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
  userEmail: string;
  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {
  }

  public getOffers() {
    //return this.offersList;
    return this.http.get<Offer[]>('http://localhost:1444/offer');
  }

  bookOffer(offer: Offer, id: string, numberOfOffers: number, numberOfSingleRooms: number, numberOfDoubleRooms: number, numberOfTrRooms: number) {
    this.authService.user.subscribe(user => {
      this.userEmail = user.email;
    })

    console.log(offer)

    this.http.post<Offer>('http://localhost:9998/order', {
      offerId: offer.id,
      clientId: this.userEmail,
      price: offer.suggestedPrice,
      departureAirportName: offer.flights[0].outboundFlight.departureAirportName,
      nrOfPeople: numberOfOffers,
      numSingleRooms: numberOfSingleRooms,
      numDoubleRooms: numberOfDoubleRooms,
      numTripleRooms: numberOfTrRooms
    }).subscribe();
  }

  getHotel(code: string) {
    return this.http.get<HotelModel>('http://localhost:1439/hotel-availability/hotel?id=' + code);
  }
}
