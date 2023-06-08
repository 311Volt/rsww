from sqlalchemy import Table, Column, String, Integer, Boolean, MetaData
from sqlalchemy.orm import declarative_base


Base = declarative_base()


class Flights(Base):
    __tablename__ = "Flights"

    id = Column(Integer, primary_key=True)
    flightNumber = Column(Integer)
    numSeats = Column(Integer)
    isReturn = Column(Boolean)
    departureAirportCode = Column(String)
    departureDate = Column(String)
    departureTime = Column(String)
    departureTimestamp = Column(Integer)
    arrivalAirportCode = Column(String)
    arrivalDate = Column(String)
    arrivalTime = Column(String)
    arrivalTimestamp = Column(Integer)

    
    def __init__(self, flightNumber, numSeats, isReturn, departureAirportCode, 
                 departureDate, departureTime, departureTimestamp, arrivalAirportCode,
                 arrivalDate, arrivalTime, arrivalTimestamp):
        self.flightNumber = flightNumber
        self.numSeats = numSeats
        self.isReturn = isReturn
        self.departureAirportCode = departureAirportCode
        self.departureDate = departureDate
        self.departureTime = departureTime
        self.departureTimestamp = departureTimestamp
        self.arrivalAirportCode = arrivalAirportCode
        self.arrivalDate = arrivalDate
        self.arrivalTime = arrivalTime
        self.arrivalTimestamp = arrivalTimestamp
    
        
    def __str__(self):
        return f"Flight number {self.flightNumber}, departure from {self.departureAirportCode}, arrival at airport {self.arrivalAirportCode}"
    

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