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

  bookOffer(offer: Offer, id: string) {
    this.authService.user.subscribe(user => {
      this.userEmail = user.email;
    })

    this.http.post<Offer>('http://localhost:8098/order', {
      offerId: offer.id,
      clientId: this.userEmail,
      price: offer.suggestedPrice,
      //departureAirportName: offer.flights,
      nrOfPeople: offer.numberOfOffers,
      numSingleRooms: offer.hotelBrief.numSingleRooms,
      numDoubleRooms: offer.hotelBrief.numDoubleRooms,
      numTripleRooms: offer.hotelBrief.numTripleRooms
    }).subscribe();

  }

  getHotel(code: string) {
    return this.http.get<HotelModel>('http://localhost:1439/hotel-availability/hotel?id=' + code);
  }
}
