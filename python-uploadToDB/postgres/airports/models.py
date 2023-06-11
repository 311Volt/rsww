from sqlalchemy import Table, Column, String, Integer, Boolean, MetaData
from sqlalchemy.orm import declarative_base


Base = declarative_base()


class Airports(Base):
    __tablename__ = "Airports"

    id = Column(Integer, primary_key=True)
    code = Column(String)
    name = Column(String)
    forDeparture = Column(Boolean)

    
    def __init__(self, code, name, forDeparture):
        self.code = code
        self.name = name
        self.forDeparture = forDeparture
    
        
    def __str__(self):
        return f"Airport code {self.code}, name {self.name}, forDeparture {self.forDeparture}"
    

'''
meta = MetaData()

Flights = Table(
    "Flights", meta,
    Column("id", Integer, primary_key=True),
    Column("flightNumber", Integer),
    Column("numSeats", Integer),
    Column("isReturn", Boolean),
    Column("departureAirportCode", String),
    Column("departureDate", String),
    Column("departureTime", String),
    Column("departureTimestamp", Integer),
    Column("arrivalAirportCode", String),
    Column("arrivalDate", String),
    Column("arrivalTime", String),
    Column("arrivalTimestamp", Integer)
)
'''