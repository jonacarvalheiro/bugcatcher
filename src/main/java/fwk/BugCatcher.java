package fwk;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BugCatcher {


    private static ThreadLocal<BugCatcher> instance;
    private final Configurator configurator;

    private BugCatcher() throws IOException, ParseException {
        configurator = makeConfigurator();
    }

    public static BugCatcher getInstance() throws IOException, ParseException {
        if (instance == null) {
            instance = new ThreadLocal<>();
        }
        if (instance.get() == null) {
            instance.set(new BugCatcher());
        }
        return instance.get();
    }


    public Configurator getConfigurator() {
        return configurator;
    }

    private Configurator makeConfigurator() throws IOException, ParseException {
        FileReader reader = new FileReader("config.json");
        JSONParser jsonParser = new JSONParser();
        return new Configurator((JSONObject) jsonParser.parse(reader));
    }
}
