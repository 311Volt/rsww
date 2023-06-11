from initPostgres import engine
from sqlalchemy.orm import Session
import json
from models import Airports


with open('./airports.json', 'r') as f:
    airports = f.read() #still not a json format file

airportsDict = json.loads(airports)

counter = 0
'''
with Session(engine) as session:
    loop
    flight = Flights(...)
    session.add(flight)
'''
destinationReturn = ["WAW", "RZE", "POZ", "BXP", "BZG", "CZW", "GDN", "QLC", "KTW", "OSZ", "KRK", "LUZ", "LCJ", "SZY", "RDO", "SZZ", "WMI", "IEG", "WRO"]


with Session(engine) as session:
    for airport in airportsDict:
        if counter % 5:
            session.commit()

        new_airport = Airports(code = airport['code'], name = airport['name'], forDeparture = airport['forDeparture'])

        session.add(new_airport)
        counter = counter + 1



    

