import {HotelModel} from "./hotel.model";
import {FlightModel} from "./flight.model";

export class Offer {
  public id: string;
  public hotel: HotelModel;
  public price: number;
  public numberOfOffers: number
  public startDate: string;
  public endDate: string;
  public flights: FlightModel[];


  constructor(id: string, hotel: HotelModel, price: number, numberOfOffers: number, startDate: string, endDate: string, flights: FlightModel[]) {
    this.id = id;
    this.hotel = hotel;
    this.price = price;
    this.numberOfOffers = numberOfOffers;
    this.startDate = startDate;
    this.endDate = endDate;
    this.flights = flights;
  }
}
