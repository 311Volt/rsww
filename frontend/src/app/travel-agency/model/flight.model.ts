import {FlyingModel} from "./flying.model";

export class FlightModel {

  public outboundFlight: FlyingModel;
  public returnFlight: FlyingModel;


  constructor(outboundFlight: FlyingModel, returnFlight: FlyingModel) {
    this.outboundFlight = outboundFlight;
    this.returnFlight = returnFlight;
  }
}
