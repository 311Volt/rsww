import {Time} from "@angular/common";

export class FlyingModel{
  public airportCode:string;
  public date: string;
  public time: string;

  constructor(airportCode: string, date: string, time: string) {
    this.airportCode = airportCode;
    this.date = date;
    this.time = time;
  }
}
