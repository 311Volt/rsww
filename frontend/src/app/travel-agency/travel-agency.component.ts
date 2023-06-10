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
export class TravelAgencyComponent implements OnInit, AfterViewInit {

  offersList: Offer[] = []
  dataSource: MatTableDataSource<Offer>;
  displayedColumns: string[] = ['country', 'departure', 'arrival', 'numberOfOffers', 'details'];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  public searchForm: FormGroup;
  public country = '';
  public startDate = '';
  public numberOfOffers: number;

  constructor(private travelService: TravelAgencyService) {
    this.offersList = this.travelService.getOffers()
    this.dataSource = new MatTableDataSource(this.offersList);
  }

  ngOnInit(): void {
    this.searchFormInit();
    this.dataSource.filterPredicate = this.getFilterPredicate();
  }

  searchFormInit() {
    this.searchForm = new FormGroup({
      country: new FormControl('', Validators.pattern('^[a-zA-Z ]+$')),
      numberOfOffers: new FormControl(''),
      startDate: new FormControl('')
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  applyFilter() {
    const startDate = this.searchForm.get('startDate').value;
    const country = this.searchForm.get('country').value;
    const numberOfOffers = this.searchForm.get('numberOfOffers').value;

    this.startDate = (startDate === null || startDate === '') ? '' : startDate.toDateString();
    this.country = country === null ? '' : country;
    this.numberOfOffers = numberOfOffers === null ? '' : numberOfOffers;

    // create string of our searching values and split if by '$'
    const filterValue = this.country + '$' + this.startDate + '$' + this.numberOfOffers;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


  getFilterPredicate() {
    return (row: Offer, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const country = filterArray[0];
      const startDate = filterArray[1];
      const numberOfOffers = filterArray[2];

      const matchFilter = [];

      // Fetch data from row
      const columnStartDateInDateFormat = new Date(row.startDate);
      const startDateInDateFormat = new Date(startDate);
      const columnCountry = row.hotel.country;
      const columnNumberOfOffers = row.numberOfOffers;

      // verify fetching data by our searching values
      const customFilterStartDate = columnStartDateInDateFormat > startDateInDateFormat;
      const customFilterCountry = columnCountry.toLowerCase().includes(country);
      const customFilterNumberOfOffers = +columnNumberOfOffers > +numberOfOffers;

      // push boolean values into array
      matchFilter.push(customFilterStartDate);
      matchFilter.push(customFilterCountry);
      matchFilter.push(customFilterNumberOfOffers);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

}
