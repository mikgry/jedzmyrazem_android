package mikolajgrygiel.jedzmyrazem.enums;

/**
 * Created by Mikolaj on 2015-11-11.
 */
public enum RestApiUrl {
    SIGN_IN("users/sign_in.json"),
    SIGN_UP("users.json"),
    SEARCH("journeys.json");

    private final String url;
    private final String BASE_URL = "http://www.jedzmyrazem.pl/";

    RestApiUrl(String url) {
        this.url = url;
    }

    public final String getUrl() { return  BASE_URL + url;}
}
