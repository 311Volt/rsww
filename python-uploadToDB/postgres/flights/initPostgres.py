from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from postgres_config import *
from models import Base
from sqlalchemy_utils import database_exists, create_database


engine = create_engine(DB_URL)

if not database_exists(DB_URL):
    create_database(DB_URL)

Base.metadata.create_all(engine)

# exec(open("./uploadFlightsData.py").read())



