import {FlyingModel} from "./flying.model";

export class FlightModel {

  public departure: FlyingModel;
  public arrival: FlyingModel;


  constructor(departure: FlyingModel, arrival: FlyingModel) {
    this.departure = departure;
    this.arrival = arrival;
  }
}
