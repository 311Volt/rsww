import {Injectable} from "@angular/core";
import {Offer} from "./offer.model";

@Injectable({providedIn: 'root'})
export class TravelAgencyService {

  offersList: Offer[] = []
  offersJson = {
    "offer1": {
      "hotel": {
        "code": "LCA20064",
        "name": "Loutsiana Hotel Apts",
        "standard": 3.0,
        "latitude": 34.986752,
        "longitude": 33.947699,
        "airportCode": "LCA",
        "country": "Cypr",
        "numSingleRooms": 12,
        "numDoubleRooms": 33,
        "numTripleRooms": 11,
        "numQuadRooms": 11,
        "ageRange0": {
          "upperBound": 3,
          "pricePerNight": 6.0
        },
        "ageRange1": {
          "upperBound": 10,
          "pricePerNight": 285.0
        },
        "ageRange2": {
          "upperBound": 18,
          "pricePerNight": 267.0
        }
      },
      "departure": {
        "flightNumber": 1424,
        "departure": {
          "airportCode": "RZE",
          "date": "2023-01-01",
          "time": "02:00:00"
        },
        "arrival": {
          "airportCode": "DBV",
          "date": "2023-01-01",
          "time": "05:11:00"
        }
      },
      "arrival": {
        "flightNumber": 1425,
        "departure": {
          "airportCode": "KRK",
          "date": "2023-01-01",
          "time": "04:02:00"
        },
        "arrival": {
          "airportCode": "LCA",
          "date": "2023-01-01",
          "time": "08:05:00"
        }
      }
    },
    "offer2": {
      "hotel": {
        "code": "LCA20064",
        "name": "Loutsiana Hotel Apts",
        "standard": 3.0,
        "latitude": 34.986752,
        "longitude": 33.947699,
        "airportCode": "LCA",
        "country": "Cypr",
        "numSingleRooms": 12,
        "numDoubleRooms": 33,
        "numTripleRooms": 11,
        "numQuadRooms": 11,
        "ageRange0": {
          "upperBound": 3,
          "pricePerNight": 6.0
        },
        "ageRange1": {
          "upperBound": 10,
          "pricePerNight": 285.0
        },
        "ageRange2": {
          "upperBound": 18,
          "pricePerNight": 267.0
        }
      },
      "departure": {
        "flightNumber": 1424,
        "departure": {
          "airportCode": "RZE",
          "date": "2023-01-01",
          "time": "02:00:00"
        },
        "arrival": {
          "airportCode": "DBV",
          "date": "2023-01-01",
          "time": "05:11:00"
        }
      },
      "arrival": {
        "flightNumber": 1425,
        "departure": {
          "airportCode": "KRK",
          "date": "2023-01-01",
          "time": "04:02:00"
        },
        "arrival": {
          "airportCode": "LCA",
          "date": "2023-01-01",
          "time": "08:05:00"
        }
      }
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
