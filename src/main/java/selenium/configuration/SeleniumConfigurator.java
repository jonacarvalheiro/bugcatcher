package selenium.configuration;

import org.json.simple.JSONObject;

import java.util.Map;

public class SeleniumConfigurator {


    private final JSONObject jsonObject;

    public SeleniumConfigurator(JSONObject jsonObject) {

        this.jsonObject = jsonObject;
    }


    public String getBrowserName() {
        return jsonObject.get("browserName").toString();
    }

    public String getUrl() {
        return jsonObject.get("url").toString();
    }


    public Map<String, Object> getPage(String page) {
        Map<String, Object> pageConfiguration = (Map<String, Object>) ((Map<String, Object>) jsonObject.get("pages")).get(page);
        return pageConfiguration;
    }


    public long getTimeout() {
        return (long) jsonObject.get("timeout");
    }

    public long getPolling() {
        return (long) jsonObject.get("polling");
    }

    public boolean isRemote() {
        return (boolean) jsonObject.get("remote");
    }
}
