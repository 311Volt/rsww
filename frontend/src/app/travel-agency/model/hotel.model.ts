import {AgeRangeModel} from "./age-range.model";

export class HotelModel {
  public id: string;
  public name: string;
  public standard: number;
  public latitude: number;
  public longitude: number;
  public airportCode: string;
  public country: string;
  public numSingleRooms: number;
  public numDoubleRooms: number;
  public numTripleRooms: number;
  public numQuadRooms: number;
  public ageRange0: AgeRangeModel;
  public ageRange1: AgeRangeModel;
  public ageRange2: AgeRangeModel;


  constructor(id: string, name: string, standard: number, latitude: number, longitude: number, airportCode: string, country: string, numSingleRooms: number, numDoubleRooms: number, numTripleRooms: number, numQuadRooms: number, ageRange0: AgeRangeModel, ageRange1: AgeRangeModel, ageRange2: AgeRangeModel) {
    this.id = id;
    this.name = name;
    this.standard = standard;
    this.latitude = latitude;
    this.longitude = longitude;
    this.airportCode = airportCode;
    this.country = country;
    this.numSingleRooms = numSingleRooms;
    this.numDoubleRooms = numDoubleRooms;
    this.numTripleRooms = numTripleRooms;
    this.numQuadRooms = numQuadRooms;
    this.ageRange0 = ageRange0;
    this.ageRange1 = ageRange1;
    this.ageRange2 = ageRange2;
  }
}
