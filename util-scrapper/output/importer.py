import requests
import json
import http.client

ADDR_SVC_FLIGHT = "http://localhost:1440"
ADDR_SVC_HOTEL = "http://localhost:1439"

def fetch_json(filename):
    with open(filename, "r") as jsonfile:
        return json.load(jsonfile)

def request_and_log(addr, json, num, total):
    response = requests.post(addr, json=json)
    status = response.status_code
    statusstr = http.client.responses[status]
    print("[{}/{}] POST {} | {} {}".format(num, total, addr, status, statusstr))
    if status >= 400:
        raise Exception("unexpected error status, aborting")
    
def import_airports(data):
    for count, airport in enumerate(data):
        request_and_log(ADDR_SVC_FLIGHT + "/admin/import-airport", json=airport, num=count, total=len(data))

def import_flights(data):
    for count, flight in enumerate(data):
        request_and_log(ADDR_SVC_FLIGHT + "/admin/import-flight", json=flight, num=count, total=len(data))

def import_hotels(data):
    for count, hotel in enumerate(data.values()):
        request_and_log(ADDR_SVC_HOTEL + "/admin/import-hotel", json=hotel, num=count, total=len(data))


import_hotels(fetch_json("hotels.json"))
import_airports(fetch_json("airports.json"))
import_flights(fetch_json("flights.json"))
