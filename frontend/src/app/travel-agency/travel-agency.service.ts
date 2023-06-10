import {Injectable, OnInit} from "@angular/core";
import {Offer} from "./model/offer.model";
import {User} from "../auth/user.model";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";


@Injectable({providedIn: 'root'})
export class TravelAgencyService{

  offersList: Offer[] = []
  offersJson = {
    "offer1": {
      "id": "f50efe4c-130d-4123-8228-dd151bee76da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA1"
      },
      "price": 10.0,
      "numberOfOffers": 8,
      "startDate": "2023-06-01T08:00:00",
      "endDate": "2023-06-10T12:00:00",
      "flights": [
        {
          "id": "flight789",
          "departureAirportName": "John F. Kennedy International Airport"
        },
        {
          "id": "flight987",
          "departureAirportName": "Los Angeles International Airport"
        }
      ]
    },
    "offer2": {
      "id": "f50efe4c-130d-4123-8228-dd151bee77da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA2"
      },
      "price": 10.0,
      "numberOfOffers": 1,
      "startDate": "2023-06-01T08:00:00",
      "endDate": "2023-06-10T12:00:00",
      "flights": [
        {
          "id": "flight789",
          "departureAirportName": "John F. Kennedy International Airport"
        },
        {
          "id": "flight987",
          "departureAirportName": "Los Angeles International Airport"
        }
      ]
    },
    "offer3": {
      "id": "f50efe4c-130d-4123-8228-dd151bee78da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA3"
      },
      "price": 10.0,
      "numberOfOffers": 2,
      "startDate": "2023-06-01T08:00:00",
      "endDate": "2023-06-10T12:00:00",
      "flights": [
        {
          "id": "flight789",
          "departureAirportName": "John F. Kennedy International Airport"
        },
        {
          "id": "flight987",
          "departureAirportName": "Los Angeles International Airport"
        }
      ]
    },
    "offer5": {
      "id": "f50efe4c-130d-4123-8228-dd151bee71da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA4"
      },
      "price": 10.0,
      "numberOfOffers": 3,
      "startDate": "2023-06-01T08:00:00",
      "endDate": "2023-06-10T12:00:00",
      "flights": [
        {
          "id": "flight789",
          "departureAirportName": "John F. Kennedy International Airport"
        },
        {
          "id": "flight987",
          "departureAirportName": "Los Angeles International Airport"
        }
      ]
    },
    "offer4": {
      "id": "f50efe4c-130d-4123-8228-dd151bee72da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA5"
      },
      "price": 10.0,
      "numberOfOffers": 44,
      "startDate": "2020-06-01T08:00:00",
      "endDate": "2020-06-10T12:00:00",
      "flights": [
        {
          "id": "flight789",
          "departureAirportName": "John F. Kennedy International Airport"
        },
        {
          "id": "flight987",
          "departureAirportName": "Los Angeles International Airport"
        }
      ]
    },
    "offer6": {
      "id": "f50efe4c-130d-4123-8228-dd151bee73da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA6"
      },
      "price": 10.0,
      "numberOfOffers": 1,
      "startDate": "2026-06-01T08:00:00",
      "endDate": "2026-06-10T12:00:00",
      "flights": [
        {
          "id": "flight789",
          "departureAirportName": "John F. Kennedy International Airport"
        },
        {
          "id": "flight987",
          "departureAirportName": "Los Angeles International Airport"
        }
      ]
    },
    "offer7": {
      "id": "f50efe4c-130d-4123-8228-dd151bee445da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA7"
      },
      "price": 10.0,
      "numberOfOffers": 8,
      "startDate": "2024-06-01T08:00:00",
      "endDate": "2024-06-10T12:00:00",
      "flights": [
        {
          "id": "flight789",
          "departureAirportName": "John F. Kennedy International Airport"
        },
        {
          "id": "flight987",
          "departureAirportName": "Los Angeles International Airport"
        }
      ]
    }
  };

  constructor(private http: HttpClient, private router: Router) {
  }

  public getOffers() {
    //return this.offersList;
    return this.http.get<Offer[]>('http://localhost:8099/offer');
  }
}
