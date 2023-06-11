import {Time} from "@angular/common";

export class FlyingModel{
  public id:string;
  public departureAirportName: string;
  public time: string;

  constructor(id: string, departureAirportName: string, time: string) {
    this.id = id;
    this.departureAirportName = departureAirportName;
    this.time = time;
  }
}
