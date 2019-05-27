package api;

import api.configuration.ServicesConfigurator;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import fwk.BugCatcher;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;

public class RestAssuredWrapper {

    private final ServicesConfigurator configurator;
    private RequestSpecification restClient;

    public RestAssuredWrapper() throws IOException, ParseException {
        configurator = makeConfigurator(BugCatcher.getInstance().getConfigurator().getRestServicesConfigFilename());
    }

    public RequestSpecification getRestClient() {
        if (restClient == null)
            restClient = makeRestClient();

        return restClient;
    }

    public ServicesConfigurator getConfigurator() {
        return configurator;
    }

    private RequestSpecification makeRestClient() {


        return given().headers("api_token",
                configurator.getApiToken(),
                "Accept",
                ContentType.JSON)
                .when();
    }

    private ServicesConfigurator makeConfigurator(String servicesConfigFilename) throws IOException, ParseException {
        FileReader reader = new FileReader(servicesConfigFilename);
        JSONParser jsonParser = new JSONParser();
        return new ServicesConfigurator((JSONObject) jsonParser.parse(reader));

    }


}
