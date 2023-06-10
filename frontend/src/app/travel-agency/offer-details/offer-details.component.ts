import {Component, Injectable, Input, OnInit} from '@angular/core';
import {Offer} from "../model/offer.model";
import {TravelAgencyService} from "../travel-agency.service";
import {ActivatedRoute, Params, Router} from "@angular/router";

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit {
  offer: Offer;
  id: string;
  displayedColumns: string[] = ['id', 'departureAirportName'];
  dataSource: any;

  constructor(private travelService: TravelAgencyService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    const id = this.route.params.subscribe(
      (params: Params) => {
        this.id = params['id'];
        this.travelService.getOffers().subscribe(response => {
          this.offer = response.find(offer => offer.id === this.id);
          this.dataSource = this.offer.flights;
        })
      }
    );
  }

  bookOffer() {

  }
}
