package selenium.exceptions;

public class UnsupportedBrowserException extends Throwable {
    public UnsupportedBrowserException(String deviceName) {
        super(deviceName);
    }
}
