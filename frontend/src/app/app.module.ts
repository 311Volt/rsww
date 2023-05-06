import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { AuthComponent } from './auth/auth.component';
import {AppRoutingModule} from "./app-routing.module";
import {FormsModule} from "@angular/forms";
import { TravelAgencyComponent } from './travel-agency/travel-agency.component';
import {HttpClientModule} from "@angular/common/http";
import { OfferDetailsComponent } from './travel-agency/offer-details/offer-details.component';
import { OfferItemComponent } from './travel-agency/offer-item/offer-item.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    AuthComponent,
    TravelAgencyComponent,
    OfferDetailsComponent,
    OfferItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
