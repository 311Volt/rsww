import {Component, Injectable, Input, OnInit} from '@angular/core';
import {Offer} from "../offer.model";
import {TravelAgencyService} from "../travel-agency.service";

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit {
  offersList: Offer[] = []
  offer: Offer;

  constructor(private travelService: TravelAgencyService) {
  }

  ngOnInit(): void {
    this.offersList = this.travelService.getOffers();
    this.offer = this.offersList[0];
  }

}
