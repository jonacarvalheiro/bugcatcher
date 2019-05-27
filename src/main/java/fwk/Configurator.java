package fwk;

import org.json.simple.JSONObject;

public class Configurator {

    private final JSONObject jsonObject;

    public Configurator(JSONObject jsonObject) {
        this.jsonObject = jsonObject;

    }

    public String getSeleniumConfigFilename() {
        return jsonObject.get("seleniumConfig").toString();
    }

    public String getRestServicesConfigFilename() {
        return jsonObject.get("restServicesConfig").toString();
    }


}
