import {Component, Input, OnInit} from '@angular/core';
import {Offer} from "../model/offer.model";

@Component({
  selector: 'app-offer-item',
  templateUrl: './offer-item.component.html',
  styleUrls: ['./offer-item.component.css']
})
export class OfferItemComponent implements OnInit {
  @Input() index: number;
  @Input() offer: Offer;
  constructor() { }

  ngOnInit(): void {
  }

}
