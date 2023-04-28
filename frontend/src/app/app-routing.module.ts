import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {AuthComponent} from "./auth/auth.component";
import {TravelAgencyComponent} from "./travel-agency/travel-agency.component";

const routes = [
  {path: '', redirectTo: '/auth', pathMatch: 'full'},
  {path:'auth', component: AuthComponent},
  {path:'travel', component: TravelAgencyComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
