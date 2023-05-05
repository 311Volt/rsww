# RSWW Project Architecture

- [RSWW Project Architecture](#rsww-project-architecture)
- [1. Service list](#1-service-list)
- [2. Service responsibility overview](#2-service-responsibility-overview)
  - [2.1. User service](#21-user-service)
  - [2.2. Agency](#22-agency)
  - [2.3. Tour Operator (commands)](#23-tour-operator-commands)
  - [2.4. Tour Operator (queries)](#24-tour-operator-queries)
  - [2.5. World](#25-world)
  - [2.6. Saga Orchestrator](#26-saga-orchestrator)
- [3. Saga descriptions](#3-saga-descriptions)
  - [3.1. Booking an offer](#31-booking-an-offer)
- [4. REST API overview](#4-rest-api-overview)
  - [Endpoints](#endpoints)
    - [POST `/api/offers/list`](#post-apiofferslist)
    - [POST `/api/offers/{id}/book`](#post-apioffersidbook)
    - [POST `/api/offer-purchases/{id}/pay`](#post-apioffer-purchasesidpay)
- [5. Messages overview](#5-messages-overview)
  - [5.1. Events](#51-events)

# 1. Service list
 - API Gateway
 - Agency
 - Tour Operator command
 - Tour Operator query
 - World
 - Saga Orchestrator
 - RabbitMQ instance

# 2. Service responsibility overview

TODO not sure if user service & agency should be merged into one

## 2.1. User service
  - database: none
  - authorization
  - takes REST calls from frontend & passes it on to the message broker
  - subscribes to certain Agency messages to create HTTP responses for clients
  - websocket endpoints for offer status updates?

## 2.2. Agency
  - database: SQL
  - stores information about its clients and authenticates them
  - event-sourced entity: OfferBooking (offerid, birthdates, status)

## 2.3. Tour Operator (commands)
  - database: SQL
  - event-sourced entity: Offer
  - generates offers (one-time call)
  - listens to World events & Agency booking requests to update offer status
  - provides snapshots for query server

## 2.4. Tour Operator (queries)
  - database: MongoDB
  - stores relatively recent information about offers
  - is able to quickly provide a filtered list of offers

## 2.5. World
  - database: MongoDB?
  - listens to (& may generate) seat/room booking events
  - maintains information about available hotel rooms & plane seats
  - notifies any external services about changed availability status
    - e.g. "hotel LCA20064 will have no vacancy from 18.06 until 21.06" is
      an event that Tour Operator may subscribe to, which may invalidate an offer

## 2.6. Saga Orchestrator
  - orchestrates Sagas. more in [3. Saga descriptions](#3-saga-descriptions-1)

# 3. Saga descriptions

## 3.1. Booking an offer
  1. [Agency] Create `OfferBooking` and init status to "waiting" (rollback: don't delete, set status to "error")
  2. [World] Try to book airplane seats
  3. [World] Try to book hotel rooms
  4. [Tour Operator] Acknowledge offer sale
  5. [Agency] Update status to "booked" and wait 1 minute for payment confirmation
  6. [Agency] Update status to "done"

no more sagas are necessary imo

# 4. REST API overview

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
```
{
  "offerPurchaseId": "87cfh8q7nhr78a3bvcn90r3y"
}
```
status: 200 (booked), 404 (no such offer) or 400 (offer unavailable)

### POST `/api/offer-purchases/{id}/pay`
request body: empty  
response body: empty  
status: 200 (payment confirmed), 404 (no such offer purchase)

# 5. Messages overview

## 5.1. Events
