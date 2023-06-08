from initPostgres import engine
from sqlalchemy.orm import Session
import json
from models import Flights


with open('./flights.json', 'r') as f:
    flights = f.read() #still not a json format file

flightsDict = json.loads(flights)

counter = 0
'''
with Session(engine) as session:
    loop
    flight = Flights(...)
    session.add(flight)
'''
destinationReturn = ["WAW", "RZE", "POZ", "BXP", "BZG", "CZW", "GDN", "QLC", "KTW", "OSZ", "KRK", "LUZ", "LCJ", "SZY", "RDO", "SZZ", "WMI", "IEG", "WRO"]


with Session(engine) as session:
    for flight in flightsDict:
        if counter % 5:
            session.commit()

        new_flight = Flights(
        flightNumber = flight['flightNumber'],
        numSeats = flight['numSeats'],
        isReturn = True if flight['arrival']['airportCode'] in destinationReturn else False,
        departureAirportCode = flight['departure']['airportCode'],
        departureDate = flight['departure']['date'],
        departureTime = flight['departure']['time'],
        departureTimestamp = 2,
        arrivalAirportCode = flight['arrival']['airportCode'],
        arrivalDate = flight['arrival']['date'],
        arrivalTime = flight['arrival']['time'],
        arrivalTimestamp = 2
        )

        session.add(new_flight)
        counter = counter + 1



    

