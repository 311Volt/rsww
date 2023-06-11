export class AgeRangeModel {
  public upperBound:number;
  public pricePerNight: number;

  constructor(upperBound: number, pricePerNight: number) {
    this.upperBound = upperBound;
    this.pricePerNight = pricePerNight;
  }
}
