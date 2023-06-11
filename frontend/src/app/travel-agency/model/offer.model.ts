import {HotelModel} from "./hotel.model";
import {FlightModel} from "./flight.model";

export class Offer {
  public id: string;
  public hotelBrief: HotelModel;
  public suggestedPrice: number;
  public numberOfOffers: number
  public startDate: string;
  public endDate: string;
  public flights: FlightModel[];


  constructor(id: string, hotelBrief: HotelModel, suggestedPrice: number, numberOfOffers: number, startDate: string, endDate: string, flights: FlightModel[]) {
    this.id = id;
    this.hotelBrief = hotelBrief;
    this.suggestedPrice = suggestedPrice;
    this.numberOfOffers = numberOfOffers;
    this.startDate = startDate;
    this.endDate = endDate;
    this.flights = flights;
  }
}
