export class AgeRangeModel {
  public lowerBound:number;
  public upperBound:number;
  public pricePerNight: number;


  constructor(lowerBound: number, upperBound: number, pricePerNight: number) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.pricePerNight = pricePerNight;
  }
}
