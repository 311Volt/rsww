import {Injectable} from "@angular/core";
import {Offer} from "./model/offer.model";

@Injectable({providedIn: 'root'})
export class TravelAgencyService {

  offersList: Offer[] = []
  offersJson = {
    "offer1": {
      "id": "f50efe4c-130d-4123-8228-dd151bee75da",
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
      "id": "f50efe4c-130d-4123-8228-dd151bee75da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA2"
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
    "offer3": {
      "id": "f50efe4c-130d-4123-8228-dd151bee75da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA3"
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
    "offer5": {
      "id": "f50efe4c-130d-4123-8228-dd151bee75da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA4"
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
    "offer4": {
      "id": "f50efe4c-130d-4123-8228-dd151bee75da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA5"
      },
      "price": 10.0,
      "numberOfOffers": 8,
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
      "id": "f50efe4c-130d-4123-8228-dd151bee75da",
      "hotel": {
        "id": "hotel456",
        "name": "Grand Hotel",
        "standard": 5.0,
        "country": "USA6"
      },
      "price": 10.0,
      "numberOfOffers": 8,
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
      "id": "f50efe4c-130d-4123-8228-dd151bee75da",
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

  constructor() {
    for (const prop in this.offersJson) {
      this.offersList.push(this.offersJson[prop])
    }
  }

  // public initOffers() {
  //   for (const prop in this.offersJson) {
  //     this.offersList.push(this.offersJson[prop])
  //   }
  // }

  public getOffers(): Offer[] {
    return this.offersList;
  }

  public getOfferById(index: number): Offer {
    return this.offersList[index];
  }
}
