import datetime as dt
import json
import os
import random


def loaddata(filename):
    with open(filename, "r") as tuidata:
        return json.load(tuidata)["offers"]


def save_table(obj, filename):
    with open(filename, "w") as outfile:
        json.dump(obj, outfile, indent=4)


def create_hotels(offers):
    out_hotels = dict()
    for offer in offers:
        if "hotelCode" in offer:
            age0 = random.randrange(3, 6)
            age1 = 18
            out_hotels[offer["hotelCode"]] = {
                "code": offer["hotelCode"],
                "name": offer["hotelName"],
                "standard": offer["hotelStandard"],
                "latitude": offer["latitude"],
                "longitude": offer["longitude"],
                "airportCode": offer["departureFlight"]["arrival"]["airportCode"],
                "country": offer["breadcrumbs"][0]["label"],
                "numSingleRooms": random.randrange(10, 30),
                "numDoubleRooms": random.randrange(20, 40),
                "numTripleRooms": random.randrange(10, 25),
                "numQuadRooms": random.randrange(7, 15),
                "ageRange0": {
                    "lowerBound": 0,
                    "upperBound": age0,
                    "pricePerNight": random.randrange(0, 90) * offer["hotelStandard"]
                },
                "ageRange1": {
                    "lowerBound": age0,
                    "upperBound": age1,
                    "pricePerNight": random.randrange(55, 130) * offer["hotelStandard"]
                },
                "ageRange2": {
                    "lowerBound": age1,
                    "upperBound": 200,
                    "pricePerNight": random.randrange(35, 90) * offer["hotelStandard"]
                }
            }
    return out_hotels


def create_airports(offers):
    out_airports = dict()
    for offer in offers:
        flight = offer["departureFlight"]
        departure = flight["departure"]
        arrival = flight["arrival"]
        out_airports[departure["airportCode"]] = {
            "code": departure["airportCode"],
            "name": departure["airportName"],
            "forDeparture": True
        }
        out_airports[arrival["airportCode"]] = {
            "code": arrival["airportCode"],
            "name": arrival["airportName"],
            "forDeparture": False
        }
    return list(out_airports.values())


def create_flights(airports, year):
    out_flights = []

    dep_airports = [airport for airport in airports if airport["forDeparture"]]
    arr_airports = [airport for airport in airports if not airport["forDeparture"]]

    start_date = dt.date(year, 1, 1).toordinal()
    end_date = dt.date(year + 1, 1, 1).toordinal()

    flight_num_counter = 1410

    for date_ord in range(start_date, end_date):
        midnight = dt.datetime.fromordinal(date_ord)
        num_flights = random.randrange(20, 40)

        for i in range(num_flights):
            start_time = midnight + dt.timedelta(minutes=random.randrange(0, 1440))
            end_time = start_time + dt.timedelta(minutes=random.randrange(60, 300))

            dirOutOfCountry = random.randrange(2)
            polAirport = random.choice(dep_airports)["code"]
            frnAirport = random.choice(arr_airports)["code"]

            out_flights.append({
                "flightNumber": flight_num_counter,
                "numSeats": random.randrange(30, 300),
                "departure": {
                    "airportCode": polAirport if dirOutOfCountry else frnAirport,
                    "date": start_time.date().isoformat(),
                    "time": start_time.time().strftime("%H:%M:%S")
                },
                "arrival": {
                    "airportCode": frnAirport if dirOutOfCountry else polAirport,
                    "date": end_time.date().isoformat(),
                    "time": end_time.time().strftime("%H:%M:%S")
                }
            })

            flight_num_counter += 1

    return out_flights


def entry():
    initialdata = loaddata("tuidata.json")
    hotels = create_hotels(initialdata)
    airports = create_airports(initialdata)
    flights = create_flights(airports, 2023)

    os.makedirs("output", exist_ok=True)

    save_table(hotels, "output/hotels.json")
    save_table(airports, "output/airports.json")
    save_table(flights, "output/flights.json")


if __name__ == "__main__":
    entry()
