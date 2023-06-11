USER="rsww175651"
PASSWORD="jpg4ever"
SVR_ADDRESS="127.0.0.1"
PORT="5432"

DB_URL = 'postgresql+psycopg2://{user}:{pw}@{ipv4}:{port}/svc_flight'.format(user=USER,pw=PASSWORD, ipv4=SVR_ADDRESS, port=PORT)
