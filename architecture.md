# RSWW Project Architecture

- [RSWW Project Architecture](#rsww-project-architecture)
- [1. Service list](#1-service-list)
- [2. Service responsibility overview](#2-service-responsibility-overview)
  - [2.1. Frontend](#21-frontend)
  - [2.2. API Gateway](#22-api-gateway)
  - [2.3. Travel Agency](#23-travel-agency)
  - [2.4. Tour Operator (commands)](#24-tour-operator-commands)
  - [2.5. Tour Operator (queries)](#25-tour-operator-queries)
  - [2.6. Hotel](#26-hotel)
  - [2.7. Flight](#27-flight)
  - [2.8. Saga Orchestrator](#28-saga-orchestrator)
  - [2.9. Payment](#29-payment)
- [3. Database schemas](#3-database-schemas)
- [4. Saga descriptions](#4-saga-descriptions)
  - [4.1. Booking an offer](#41-booking-an-offer)
- [5. REST API overview](#5-rest-api-overview)
  - [Endpoints](#endpoints)
    - [POST `/api/offers/list`](#post-apiofferslist)
    - [POST `/api/offers/{id}/book`](#post-apioffersidbook)
    - [POST `/api/offer-purchases/{id}/pay`](#post-apioffer-purchasesidpay)
- [6. Messages overview](#6-messages-overview)
  - [6.1. Events](#61-events)
  - [6.2. Commands](#62-commands)
  - [6.3. Queries](#63-queries)

# 1. Service list
 - Frontend 
 - API Gateway
 - Travel Agency
 - Tour Operator command
 - Tour Operator query
 - Hotel
 - Flight
 - Saga Orchestrator
 - Payment Service

# 2. Service responsibility overview

## 2.1. Frontend
- database: none
- Is a component that directly participates in communication and displaying information to the client
- webpage component is created using Angular  
- runs on the user's browser and communicates with the API gateway

## 2.2. API Gateway
  - database: PostgreSQL
  - authorization
  - stores information about its clients and authenticates them
  - takes REST calls from frontend & passes it on to the message broker
  - subscribes to certain Agency messages to create HTTP responses for clients
  - websocket endpoints for offer status updates

## 2.3. Travel Agency
  - database: MongoDB
  - stores all created reservations and creates events that start saga
  - it is an event-sourced service that listens for commands and events that revolve around creating, cancelling and getting reservations

## 2.4. Tour Operator (commands)
  - database: MongoDB
  - event-sourced entity: Offer
  - generates offers (one-time call)
  - listens to World events & Travel Agency booking requests (through Saga) to update offer status
  - provides snapshots for query server

## 2.5. Tour Operator (queries)
  - database: MongoDB
  - stores relatively recent information about offers
  - is able to quickly provide a filtered list of offers as well as information about specific one

## 2.6. Hotel
  - database: MongoDB
  - listens to (& may generate) room booking events
  - maintains information about available hotel rooms
  - notifies any external services about changed availability status
    - e.g. "hotel LCA20064 will have no vacancy from 18.06 until 21.06" is
      an event that Tour Operator may subscribe to, which may invalidate an offer

## 2.7. Flight
  - database: PostgreSQL
  - listens to (& may generate) flight seat booking events
  - maintains information about available plane seats
  - notifies any external services about changed availability status

## 2.8. Saga Orchestrator
  - orchestrates Sagas. more in [4. Saga descriptions](#4-saga-descriptions)

## 2.9. Payment
  - Payment simulation. In response to a Pay command, sends back either:
    - Payment confirmation
    - Payment failure


# 3. Database schemas
<!-- https://dbdiagram.io/d/6485e884722eb77494c3f54a -->
## 3.1. Offerts DB (Mongo)

![img.png](architecture-img/img.png)

## 3.2. Hotels DB (Mongo)

![img.png](architecture-img/img_6.png)

![img_1.png](architecture-img/img_1.png)

## 3.3. Reservations DB (Mongo)

![img_2.png](architecture-img/img_2.png)

## 3.4. Flights DB (PostgreSQL)

![img_3.png](architecture-img/img_3.png)

## 3.5. Users DB (PostgreSQL)

![img_4.png](architecture-img/img_4.png)

# 4. Saga descriptions

<!-- https://lucid.app/lucidchart/c85fc5b5-cf70-4a1d-85e8-97416b94aa47/edit?viewport_loc=-676%2C1335%2C3012%2C1428%2C0_0&invitationId=inv_872fe337-aeca-4ed5-910f-f20a061be61c -->

## 4.1. Booking an offer

![img_5.png](architecture-img/img_5.png)

# 5. REST API overview

HTTP communication only happens between the frontend and the User service.
All other services communicate using the message broker.

## Endpoints

### POST `/api/offers/list`
Request body:
```json
{
  "filters": {
    "maxPrice": 3300,
    "countries": ["Grecja", "Bułgaria"],
    "page": 1,
    "offersPerPage": 20
  }
}
```

Response body:
```javascript
{
  "offers": [
    {
      "hotelCode": "DS3",
      "hotelStandard": 1437.0,
      /* ... */
    }
    /* etc... */
  ]
}
```

### POST `/api/offers/{id}/book`
request body: empty  
response body: 
```json
{
  "offerPurchaseId": "87cfh8q7nhr78a3bvcn90r3y"
}
```
status: 200 (booked), 404 (no such offer) or 400 (offer unavailable)

### POST `/api/offer-purchases/{id}/pay`
request body: empty  
response body: empty  
status: 200 (payment confirmed), 404 (no such offer purchase)

# 6. Messages overview

## 6.1. Events

| Name                               | Sender          | Receiver            | Description                                               |
|------------------------------------|-----------------|---------------------|-----------------------------------------------------------|
| HotelReservationFailedEvent        | Hotel Service   | Saga Service        | Information about failed hotel reservation                |
| HotelReservationSuccessfulEvent    | Hotel Service   | Saga Service        | Information about succesful hotel reservation             |
| OfferCreatedEvent                  | Tour Operator   | Tour Operator       | Has information about newly created offer                 |
| OfferDecreaseAmountEvent           | Tour Operator   | Tour Operator, Saga | Demand to lower ammount of Offers                         |
| OfferDecreaseAmountFailedEvent     | Tour Operator   | Saga                | Information about failed lowering of ammount of Offers    |
| OfferDecreaseAmountSuccessfulEvent | Tour Operator   | Saga                | Information about succesful lowering of ammount of Offers |
| OfferIncreaseAmountEvent           | Tour Operator   | Tour Operator, Saga | Demand to increase ammount of Offers                      |
| PaymentInvalidEvent                | Payment Service | Saga                | Information about failed payment validation               |
| PaymentValidEvent                  | Payment Service | Saga                | Information about succesful payment validation            |
| PlaneReservationFailedEvent        | Flight Service  | Saga                | Information about failed flight reservation               |
| PlaneReservationSuccessfulEvent    | Flight Service  | Saga                | Information about succesful return flight reservation     |
| ReturnPlaneReservationFailedEvent  | Flight Service  | Saga                | Information about failed flight reservation               |
| ReturnPlaneReservationFailedEvent  | Flight Service  | Saga                | Information about failed return flight reservation        |
| ReservationCanceledEvent           | Travel Agency   | Travel Agency, Saga | Demand to cancel reservation                              |
| ReservationCreatedEvent            | Travel Agency   | Travel Agency, Saga | Demand to create reservation                              |

## 6.2. Commands

| Name                       | Sender               | Receiver         | Description                                     |
|----------------------------|----------------------|------------------|-------------------------------------------------|
| BookFlightCommand          | Flight Service, Saga | Flight Service   | Demand to book flight                           |
| BookReturnFlightCommand    | Saga                 | Flight Service   | Demand to book return flight                    |
| CancelFlightBookingCommand | Saga                 | Flight Service   | Demand to cancel flight booking for reservation |
| BookHotelCommand           | Saga                 | Hotel Service    | Demand to book hotel                            |
| CancelHotelBookingCommand  | Saga                 | Hotel Service    | Demand to cancel hotel booking                  |
| CancelReservationCommand   | Saga                 | Travel Agency    | Demand to cancel reservation                    |
| CreateReservationCommand   | Gateway API          | Travel Agency    | Demand to create reservation                    |
| CreateOfferCommand         | Gateway API          | Tour Operator    | Demand to create new offer                      |
| DecreaseOfferAmountCommand | Gateway API, Saga    | Tour Operator    | Demand to lower ammount of Offers               |
| IncreaseOfferAmountCommand | Saga                 | Tour Operator    | Demand to increase ammount of Offers            |
| ValidatePaymentCommand     | Saga                 | Payment Service  | Demand to validate payment for offer            |

## 6.3. Queries

| Name                          | Sender                        | Receiver       | Description                                                     |
|-------------------------------|-------------------------------|----------------|-----------------------------------------------------------------|
| CheckFlightAvailabilityQuery  | Flight Service, Tour Operator | Flight Service | Information about number of seats aviable for flight            |
| FindAllViableFlightPairsQuery | Tour Operator                 | Flight Service | Information about aviable flight pairs for dates and airport    |
| FindBestFlightPairQuery       | Flight Service                | Flight Service | Information about best flight pairs for dates and airport       |
| GetFlightBookingPriceQuery    | Tour Operator                 | Flight Service | Information about price of booking this flight                  |
| GetFlightInfoQuery            | Gateway API                   | Flight Service | Information about flight                                        |
| CheckHotelAvailabilityQuery   | Hotel Service, Tour Operator  | Hotel Service  | Information about number of hotel rooms aviable inbetween dates |
| GetHotelBookingPriceQuery     | Tour Operator                 | Hotel Service  | Information about price of booking the hotel                    |
| GetHotelInfoQuery             | Gateway API                   | Hotel Service  | Information about hotel                                         |
| GetOfferQuery                 | Gateway API, Saga             | Tour Operator  | Information about offer                                         |
| GetOffersQuery                | Gateway Api                   | Tour Operator  | Information about list of filtered offers                       |
| GetRandomHotelQuery           | Tour Operator                 | Hotel Service  | Information about random hotel                                  |
| GetReservationQuery           | Gateway API, Saga             | Travel Agency  | Information about reservation                                   |
