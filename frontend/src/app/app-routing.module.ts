import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {AuthComponent} from "./auth/auth.component";
import {TravelAgencyComponent} from "./travel-agency/travel-agency.component";
import {OfferDetailsComponent} from "./travel-agency/offer-details/offer-details.component";
import {ReservationsComponent} from "./reservations/reservations.component";

const routes = [
  {path: '', redirectTo: '/auth', pathMatch: 'full'},
  {path: 'auth', component: AuthComponent},
  {path: 'travel', component: TravelAgencyComponent},
  {path: 'reservations', component: ReservationsComponent},
  {path: 'travel/:id', component: OfferDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
