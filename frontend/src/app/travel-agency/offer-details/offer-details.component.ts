import {Component, Injectable, Input, OnInit} from '@angular/core';
import {Offer} from "../offer.model";
import {TravelAgencyService} from "../travel-agency.service";
import {ActivatedRoute, Params, Router} from "@angular/router";

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit {
  offersList: Offer[] = []
  offer: Offer;
  id: number;

  constructor(private travelService: TravelAgencyService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    this.offersList = this.travelService.getOffers();
    this.offer = this.offersList[0];
    const id = this.route.params.subscribe(
      (params: Params) => {
        this.id = +params['id'];
        this.offer = this.travelService.getOfferById(this.id);
      }
    );
  }

}