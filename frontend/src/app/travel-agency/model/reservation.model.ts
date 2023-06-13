export class ReservationModel {
  public reservationId: string;
  public offerId: string;
  public clientId: string;
  public departureAirportName: string;
  public price: string;
  public nrOfPeople: string;
  public paid: string;


  constructor(reservationId: string, offerId: string, clientId: string, departureAirportName: string, price: string, nrOfPeople: string, paid: string) {
    this.reservationId = reservationId;
    this.offerId = offerId;
    this.clientId = clientId;
    this.departureAirportName = departureAirportName;
    this.price = price;
    this.nrOfPeople = nrOfPeople;
    this.paid = paid;
  }
}
