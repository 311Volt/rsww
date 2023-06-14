
CREATE USER postgres WITH PASSWORD 'student';

CREATE DATABASE rsww_175651_svc_client;
GRANT ALL PRIVILEGES ON DATABASE rsww_175651_svc_client TO postgres;
ALTER DATABASE rsww_175651_svc_client OWNER TO postgres;

CREATE DATABASE rsww_175651_svc_flight;
GRANT ALL PRIVILEGES ON DATABASE rsww_175651_svc_flight TO postgres;
ALTER DATABASE rsww_175651_svc_flight OWNER TO postgres;

CREATE DATABASE rsww_175651_svc_gateway;
GRANT ALL PRIVILEGES ON DATABASE rsww_175651_svc_gateway TO postgres;
ALTER DATABASE rsww_175651_svc_gateway OWNER TO postgres;

CREATE DATABASE rsww_175651_svc_travel_agency;
GRANT ALL PRIVILEGES ON DATABASE rsww_175651_svc_travel_agency TO postgres;
ALTER DATABASE rsww_175651_svc_travel_agency OWNER TO postgres;