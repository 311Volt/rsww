export class AgeRangeModel {
  public upperBound:number;
  public pricePerNight: number;
  public lowerBound:number;


  constructor(upperBound: number, pricePerNight: number, lowerBound: number) {
    this.upperBound = upperBound;
    this.pricePerNight = pricePerNight;
    this.lowerBound = lowerBound;
  }
}
