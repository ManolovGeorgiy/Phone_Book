package integration.phone;

import com.github.javafaker.Faker;
import integration.ApiBase;
import integration.schemas.PhoneDto;
import io.restassured.response.Response;

public class PhoneApi extends ApiBase {


    PhoneDto phoneDto;
    Response response;
    Faker faker = new Faker();
    String countryCode = faker.internet().uuid();
    String phoneNumber = faker.internet().uuid();
    String editCountryCode = faker.internet().uuid();
    String editPhoneNumber = faker.internet().uuid();
    public PhoneApi(String token) {
        super(token);
    }

    public PhoneDto rndForCreatedPhone(int contactId) {
        phoneDto = new PhoneDto();
        phoneDto.setCountryCode(countryCode);
        phoneDto.setPhoneNumber(phoneNumber);
        phoneDto.setContactId(contactId);
        return phoneDto;
    }

    public PhoneDto rndForEditPhone(int id, int contactId) {
        phoneDto = new PhoneDto();
        phoneDto.setId(id);
        phoneDto.setCountryCode(editCountryCode);
        phoneDto.setPhoneNumber(editPhoneNumber);
        phoneDto.setContactId(contactId);
        return phoneDto;
    }

    public void createPhone(int code, int contactId) {
        String endpoint = "/api/phone";
        Object body = rndForCreatedPhone(contactId);
        response = postRequest(endpoint, code, body);
    }

    public void editPhone(int code, int id, int contactId) {
        String endpoint = "/api/phone";
        Object body = rndForEditPhone(id, contactId);
        putRequest(endpoint, code, body);
    }

    public void deletePhone(int code, int id) {
        String endpoint = "/api/phone/{id}";
        response = deleteRequest(endpoint, code, id);
    }

    public Response getAllPhones(int code, int contactId) {
        String endpoint = "/api/phone/{contactId}/all";
        response = getRequestWithParam(endpoint, code, "contactId", contactId);
        return response;
    }

    public Response getPhone(int code, int id) {
        String endpoint = "/api/phone/{id}";
        response = getRequestWithParam(endpoint, code, "id", id);
        return response;
    }
}