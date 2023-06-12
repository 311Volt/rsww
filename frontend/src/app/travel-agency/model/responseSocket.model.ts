import {HotelModel} from "./hotel.model";

export class ResponseSocketModel{
  public reservationId: string;
  public offerId: string;
  public numberOfOffers: number;


  constructor(reservationId: string, offerId: string, numberOfOffers: number) {
    this.reservationId = reservationId;
    this.offerId = offerId;
    this.numberOfOffers = numberOfOffers;
  }
}
