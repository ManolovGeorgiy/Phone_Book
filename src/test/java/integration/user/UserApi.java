package integration.user;

import integration.ApiBase;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.LinkedHashMap;

public class UserApi extends ApiBase {
    Response response;

    public UserApi() {
    }

    @Step("Login via api: {email},{password}")
    public String login(String email, String password, int code) {
        String endpoint = "/api/user/login";
        LinkedHashMap<String, String> body = new LinkedHashMap<>();
        body.put("email", email);
        body.put("password", password);
        response = postRequest(endpoint, code, body);
        return response.header("Access-Token");
    }

    @Step("New User Registration : {email},{password}")
    public String newUserRegistration(String email, String password, int code) {
        String endpoint = "/api/user";
        LinkedHashMap<String, String> body = new LinkedHashMap<>();
        body.put("email", email);
        body.put("password", password);
        response = postRequest(endpoint, code, body);
        return response.asString();
    }

    @Step("New User activation: {token}")
    public void getNewUserActivation(int code, String token) {
        String endpoint = "/api/user/activation/{token}";
        response = getRequestWithParamString(endpoint, code, "token", token);
    }
}


