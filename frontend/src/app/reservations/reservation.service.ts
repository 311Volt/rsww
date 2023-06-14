import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../auth/auth.service";
import {Offer} from "../travel-agency/model/offer.model";
import {ReservationModel} from "../travel-agency/model/reservation.model";

@Injectable({providedIn: 'root'})
export class ReservationService{

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {
  }



  public getOffersReserved() {
    //return this.offersList;
    console.log(this.authService.getUserId())
    return this.http.get<ReservationModel[]>('/api/order/' + this.authService.getUserId());
  }

  buyReservation(id: string) {


    console.log('Buy reservation')
    this.http.post<ReservationModel[]>('/api/order/pay?id=' + id, null).subscribe(res => {
      this.router.navigate(['travel']);
    });
  }
}
