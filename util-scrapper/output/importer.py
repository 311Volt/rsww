import requests
import json
import http.client
import os

ADDR_GATEWAY = os.getenv('RSWW_SERVICE_ADDR', "http://localhost:1438")

def fetch_json(filename):
    with open(filename, "r") as jsonfile:
        return json.load(jsonfile)

def batch(iterable, n=1):
    l = len(iterable)
    for ndx in range(0, l, n):
        yield iterable[ndx:min(ndx + n, l)]


def request_and_log(addr, json, num, total):
    response = requests.post(addr, json=json)
    status = response.status_code
    statusstr = http.client.responses[status]
    print("[{}/{}] POST {} | {} {}".format(num, total, addr, status, statusstr))
    if status >= 400:
        raise Exception("unexpected error status, aborting")
    
def import_airports(data):
    for count, airport in enumerate(data):
        request_and_log(ADDR_GATEWAY + "/api/admin/import/airport", json=airport, num=count, total=len(data))

def import_flights(data):
    for count, flight_batch in enumerate(batch(data, 200)):
        request_and_log(ADDR_GATEWAY + "/api/admin/import/flight-batch", json=flight_batch, num=count, total=int(len(data)/200))

def import_hotels(data):
    for count, hotel in enumerate(data.values()):
        request_and_log(ADDR_GATEWAY + "/api/admin/import/hotel", json=hotel, num=count, total=len(data))


import_hotels(fetch_json("hotels.json"))
import_airports(fetch_json("airports.json"))
import_flights(fetch_json("flights.json"))
