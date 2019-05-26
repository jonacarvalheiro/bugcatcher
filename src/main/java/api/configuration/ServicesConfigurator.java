package api.configuration;

import org.json.simple.JSONObject;

import java.util.Map;

public class ServicesConfigurator {

    private final JSONObject jsonObject;

    public ServicesConfigurator(JSONObject jsonObject) {

        this.jsonObject = jsonObject;
    }

    public String getUsername() {
        return jsonObject.get("username").toString();
    }

    public String getPwd() {
        return jsonObject.get("pwd").toString();
    }

    public String getApiToken() {
        return jsonObject.get("apiToken").toString();
    }

    public String getBaseUrl() {
        return jsonObject.get("baseUrl").toString();
    }

    public Map<String, Object> getService(String service) {
        Map<String, Object> serviceConfiguration = (Map<String, Object>) ((Map<String, Object>) jsonObject.get("services")).get(service);

        return serviceConfiguration;
    }
}
