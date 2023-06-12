import {Injectable, OnInit} from "@angular/core";
import {Offer} from "./model/offer.model";
import {User} from "../auth/user.model";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../auth/auth.service";
import {of} from "rxjs";
import {HotelModel} from "./model/hotel.model";
import { RswwConfig } from "../config";


@Injectable({providedIn: 'root'})
export class TravelAgencyService{

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {
  }

  public getOffers() {
    //return this.offersList;
    return this.http.get<Offer[]>(`${RswwConfig.backendAddress}/api/offer/offers`);
  }

  bookOffer(offer: Offer, id: string, numberOfOffers: number, numberOfSingleRooms: number, numberOfDoubleRooms: number,
            numberOfTrRooms: number, userEmail: string, chooseFlight:string) {

    console.log(offer.id)

    console.log(userEmail)
    console.log(offer.suggestedPrice)
    console.log(offer.flights[0].outboundFlight.departureAirportName)
    console.log(numberOfOffers)
    console.log(numberOfSingleRooms)
    console.log(numberOfDoubleRooms)
    console.log(numberOfTrRooms)


    this.http.post<any>(`${RswwConfig.backendAddress}/api/order`, {
      offerId: offer.id,
      clientId: userEmail,
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
    return this.http.get<HotelModel>(`${RswwConfig.backendAddress}/api/hotel/` + code);
  }
}
