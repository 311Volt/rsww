import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Offer} from "./model/offer.model";
import {TravelAgencyService} from "./travel-agency.service";
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';

@Component({
  selector: 'app-travel-agency',
  templateUrl: './travel-agency.component.html',
  styleUrls: ['./travel-agency.component.css']
})
export class TravelAgencyComponent implements OnInit, AfterViewInit  {

  offersList:Offer[] = []
  dataSource: MatTableDataSource<Offer>;
  displayedColumns: string[] = ['country', 'departure', 'arrival', 'details'];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private travelService:TravelAgencyService) {
    this.offersList = this.travelService.getOffers()
    this.dataSource = new MatTableDataSource(this.offersList);
  }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilterCountry(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

}
