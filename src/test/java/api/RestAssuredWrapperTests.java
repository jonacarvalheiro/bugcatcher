package api;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

public class RestAssuredWrapperTests {

    @Test
    public void getRestClient_CorrectAuthentication_ShouldBeCreatedWithSuccess() throws IOException, ParseException {
      RestAssuredWrapper restAssuredWrapper=new RestAssuredWrapper();
      restAssuredWrapper.getRestClient();
    }
}
