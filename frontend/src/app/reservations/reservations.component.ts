import { Component, OnInit } from '@angular/core';
import {ReservationService} from "./reservation.service";
import {Offer} from "../travel-agency/model/offer.model";
import {ReservationModel} from "../travel-agency/model/reservation.model";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {
  reservationList: ReservationModel[] = []
  dataSource: MatTableDataSource<ReservationModel>;
  displayedColumns: string[] = ['departureAirportName', 'price', 'nrOfPeople', 'buy'];
  constructor(private reservationService: ReservationService) { }

  ngOnInit(): void {
    this.reservationService.getOffersReserved().subscribe(response => {
      this.reservationList = response;
      console.log(this.reservationList)
      this.dataSource = new MatTableDataSource(this.reservationList);
    })
  }

  buyReservation(id: string) {
    this.reservationService.buyReservation(id);
  }
}
