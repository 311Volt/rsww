import {AfterViewChecked, AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Offer} from "./model/offer.model";
import {TravelAgencyService} from "./travel-agency.service";
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ResponseSocketModel} from "./model/responseSocket.model";
import {DestinationPopularityModel} from "./model/destination-popularity.model";
import {AirportPopularityModel} from "./model/airport-popularity.model";
import {HotelPopularityModel} from "./model/hotel-popularity.model";
import {RoomPopularityModel} from "./model/room-popularity.model";

declare var SockJS;
declare var Stomp;


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
  public airportCode = '';
  public numberOfOffers: number;
  public stompClient0;
  public stompClient1;
  public stompClient2;
  public stompClient3;

  public table1: DestinationPopularityModel[];
  public table2: AirportPopularityModel[];
  public table3: HotelPopularityModel[];
  public table4: RoomPopularityModel[];

  constructor(private travelService: TravelAgencyService) {
  }

  initializeWebSocketConnection() {
    const ws0 = new SockJS('http://localhost:1438/rsww');
    this.stompClient0 = Stomp.over(ws0);
    const that = this;
    // tslint:disable-next-line:only-arrow-functions
    this.stompClient0.connect({}, function (frame) {
      that.stompClient0.subscribe('/req7topic/popularDestinations', (message) => {
        if (message.body) {
          let responseModel = []
          responseModel = JSON.parse(message.body);
          that.table1 = responseModel;
        }
      });
    });
    const ws1 = new SockJS('http://localhost:1438/rsww');
    this.stompClient1 = Stomp.over(ws1);
    this.stompClient1.connect({}, function (frame) {
      that.stompClient1.subscribe('/req7topic/popularAirports', (message) => {
        if (message.body) {
          let responseModel = []
          responseModel = JSON.parse(message.body);
          that.table2 = responseModel;
        }
      });
    });

    const ws2 = new SockJS('http://localhost:1438/rsww');
    this.stompClient2 = Stomp.over(ws2);
    this.stompClient2.connect({}, function (frame) {
      that.stompClient2.subscribe('/req7topic/popularHotels', (message) => {
        if (message.body) {
          let responseModel = []
          responseModel = JSON.parse(message.body);
          that.table3 = responseModel;
        }
      });
    });

    const ws3 = new SockJS('http://localhost:1438/rsww');
    this.stompClient3 = Stomp.over(ws3);
    this.stompClient3.connect({}, function (frame) {
      that.stompClient3.subscribe('/req7topic/popularRoomTypes', (message) => {
        if (message.body) {
          let responseModel = []
          responseModel = JSON.parse(message.body);
          that.table4 = responseModel;
        }
      });
    });
  }

  ngOnInit(): void {
    this.travelService.getOffers().subscribe(response => {
      this.offersList = response;
      this.dataSource = new MatTableDataSource(this.offersList);
      this.dataSource.paginator = this.paginator;
      this.dataSource.filterPredicate = this.getFilterPredicate();
      this.initializeWebSocketConnection()
    })
    this.searchFormInit();
  }

  searchFormInit() {
    this.searchForm = new FormGroup({
      country: new FormControl('', Validators.pattern('^[a-zA-Z ]+$')),
      numberOfOffers: new FormControl('', Validators.pattern(/^-?(0|[1-9]\d*)?$/)),
      airportCode: new FormControl('', Validators.pattern('^[a-zA-Z ]+$')),
      startDate: new FormControl('')
    });
  }

  applyFilter() {
    const startDate = this.searchForm.get('startDate').value;
    const country = this.searchForm.get('country').value;
    const numberOfOffers = this.searchForm.get('numberOfOffers').value;
    const airportCode = this.searchForm.get('airportCode').value;

    this.startDate = (startDate === null || startDate === '') ? '10.06.1000' : startDate.toDateString();
    this.country = country === null ? '' : country;
    this.numberOfOffers = numberOfOffers === null ? '' : numberOfOffers;
    this.airportCode = airportCode === null ? '' : airportCode;

    // create string of our searching values and split if by '$'
    const filterValue = this.country + '$' + this.startDate + '$' + this.numberOfOffers + '$' + this.airportCode;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  getFilterPredicate() {
    return (row: Offer, filters: string) => {
      const filterArray = filters.split('$');
      const country = filterArray[0];
      const startDate = filterArray[1];
      const numberOfOffers = filterArray[2];
      const airportCode = filterArray[3];

      const matchFilter = [];

      const columnStartDateInDateFormat = new Date(row.startDate);
      const startDateInDateFormat = new Date(startDate);
      const columnCountry = row.hotelBrief.country;
      const columnNumberOfOffers = row.numberOfOffers;
      const airPortCodeArrayColum = [];
      for (let i = 0; i < row.flights.length; i++) {
        airPortCodeArrayColum[i] = row.flights[i].outboundFlight.departureAirportName.toLowerCase();
      }

      const customFilterStartDate = columnStartDateInDateFormat >= startDateInDateFormat;
      const customFilterCountry = columnCountry.toLowerCase().includes(country);
      const customFilterNumberOfOffers = +columnNumberOfOffers >= +numberOfOffers;
      let customFilterAirPortCodeArray = airPortCodeArrayColum.includes(airportCode.toLowerCase());

      matchFilter.push(customFilterStartDate);
      matchFilter.push(customFilterCountry);
      matchFilter.push(customFilterNumberOfOffers);
      if (airportCode !== '') {
        matchFilter.push(customFilterAirPortCodeArray);
      }

      return matchFilter.every(Boolean);
    };
  }

}
