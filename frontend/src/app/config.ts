

export class RswwConfig {
  static readonly backendAddress: string = (() => {
	var venv: any = process.env['RSWW_BACKEND_ADDR'];
	if(venv == "routed") {
		return "";
	} else if(venv) {
		return venv;
	} else {
		return "http://localhost:1438"
	}
  })();
};

