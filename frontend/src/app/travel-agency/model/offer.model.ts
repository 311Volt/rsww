import {HotelModel} from "./hotel.model";
import {FlightModel} from "./flight.model";

export class Offer {
  public hotel: HotelModel;
  public departure: FlightModel;
  public arrival: FlightModel;


  constructor(hotel: HotelModel, departure: FlightModel, arrival: FlightModel) {
    this.hotel = hotel;
    this.departure = departure;
    this.arrival = arrival;
  }
}
