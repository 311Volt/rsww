import {Component, OnInit} from '@angular/core';
import {Offer} from "./offer.model";
import {TravelAgencyService} from "./travel-agency.service";

@Component({
  selector: 'app-travel-agency',
  templateUrl: './travel-agency.component.html',
  styleUrls: ['./travel-agency.component.css']
})
export class TravelAgencyComponent implements OnInit {

  offersList:Offer[] = []

  constructor(private travelService:TravelAgencyService) {
  }

  ngOnInit(): void {
    this.offersList = this.travelService.getOffers()
  }

}
