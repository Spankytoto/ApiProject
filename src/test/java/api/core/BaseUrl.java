package api.core;

public interface BaseUrl {
    String BASE_URL = "https://reqres.in/";
    String LIST_USERS = "api/users?page=2";
    String USER = "api/users/%s";
    String REGISTER = "api/register";
    String LIST_RESOURCE = "api/unknown";
    String SINGLE_RESOURCE = "api/unknown/%s";
    String CREATE = "api/users";
    String LOGIN = "api/login";
    String DELAYED_RESPONSE = "/api/users?delay=3";
}
