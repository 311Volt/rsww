import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Offer} from "./model/offer.model";
import {TravelAgencyService} from "./travel-agency.service";
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-travel-agency',
  templateUrl: './travel-agency.component.html',
  styleUrls: ['./travel-agency.component.css']
})
export class TravelAgencyComponent implements OnInit {

  offersList: Offer[] = []
  dataSource: MatTableDataSource<Offer>;
  displayedColumns: string[] = ['country', 'departure', 'arrival', 'numberOfOffers', 'details'];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  public searchForm: FormGroup;
  public country = '';
  public startDate = '';
  public numberOfOffers: number;

  constructor(private travelService: TravelAgencyService) {
  }

  ngOnInit(): void {
    this.travelService.getOffers().subscribe(response =>{
      this.offersList = response;
      this.dataSource = new MatTableDataSource(this.offersList);
      this.dataSource.paginator = this.paginator;
      this.dataSource.filterPredicate = this.getFilterPredicate();
    })
    this.searchFormInit();
  }

  searchFormInit() {
    this.searchForm = new FormGroup({
      country: new FormControl('', Validators.pattern('^[a-zA-Z ]+$')),
      numberOfOffers: new FormControl(''),
      startDate: new FormControl('')
    });
  }

  applyFilter() {
    const startDate = this.searchForm.get('startDate').value;
    const country = this.searchForm.get('country').value;
    const numberOfOffers = this.searchForm.get('numberOfOffers').value;

    this.startDate = (startDate === null || startDate === '') ? '10.06.1023' : startDate.toDateString();
    this.country = country === null ? '' : country;
    this.numberOfOffers = numberOfOffers === null ? '' : numberOfOffers;

    // create string of our searching values and split if by '$'
    const filterValue = this.country + '$' + this.startDate + '$' + this.numberOfOffers;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  getFilterPredicate() {
    return (row: Offer, filters: string) => {
      const filterArray = filters.split('$');
      const country = filterArray[0];
      const startDate = filterArray[1];
      const numberOfOffers = filterArray[2];

      const matchFilter = [];

      const columnStartDateInDateFormat = new Date(row.startDate);
      const startDateInDateFormat = new Date(startDate);
      const columnCountry = row.hotel.country;
      const columnNumberOfOffers = row.numberOfOffers;

      const customFilterStartDate = columnStartDateInDateFormat > startDateInDateFormat;
      const customFilterCountry = columnCountry.toLowerCase().includes(country);
      const customFilterNumberOfOffers = +columnNumberOfOffers > +numberOfOffers;

      matchFilter.push(customFilterStartDate);
      matchFilter.push(customFilterCountry);
      matchFilter.push(customFilterNumberOfOffers);

      return matchFilter.every(Boolean);
    };
  }

}
