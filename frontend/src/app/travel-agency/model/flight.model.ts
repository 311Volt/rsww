import {FlyingModel} from "./flying.model";

export class FlightModel {
  public flightNumber:number;
  public departure: FlyingModel;
  public arrival: FlyingModel;

  constructor(flightNumber: number, departure: FlyingModel, arrival: FlyingModel) {
    this.flightNumber = flightNumber;
    this.departure = departure;
    this.arrival = arrival;
  }
}
