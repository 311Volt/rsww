# RSWW Project Architecture

# 0. Contents

## 1. [Service list](#1-service-list-1)
## 2. [Service responsibility overview](#2-service-responsibility-overview-1)
 - 2.1 [User service](#21-normal-mode)
 - 2.2 [Agency](#22-agency)
 - 2.3 [Tour Operator (commands)](#23-tour-operator-commands)
 - 2.4 [Tour Operator (queries)](#24-tour-operator-queries)
 - 2.5 [World](#25-world)
 - 2.6 [Saga Orchestrator](#26-saga-orchestrator)
## 3. [Saga descriptions](#3-saga-descriptions-1)
 - 3.1 [Booking an offer](#31-booking-an-offer)
## 4. [API overview](#4-api-overview-1)
## 5. [Messages overview](#5-message-overview-1)
 

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

# 4. API overview

HTTP communication only happens between the frontend and the User service.
All other services communicate with RabbitMQ.

Endpoints: TODO

## 5. Messages overview

TODO
