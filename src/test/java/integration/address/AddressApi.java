package integration.address;

import com.github.javafaker.Faker;
import integration.ApiBase;
import integration.schemas.AddressDto;
import io.restassured.response.Response;

public class AddressApi extends ApiBase {

    Response response;
    AddressDto addressDto;
    Faker faker = new Faker();
    String city = faker.internet().uuid();
    String country = faker.internet().uuid();
    String street = faker.internet().uuid();
    String zip = faker.internet().uuid();
    String editCity = faker.internet().uuid();
    String editCountry = faker.internet().uuid();
    String editStreet = faker.internet().uuid();
    String editZip = faker.internet().uuid();
    public AddressApi(String token) {
        super(token);
    }

    public AddressDto rndDataForCreateAddress(int contactId) {
        addressDto = new AddressDto();
        addressDto.setCity(city);
        addressDto.setCountry(country);
        addressDto.setStreet(street);
        addressDto.setZip(zip);
        addressDto.setContactId(contactId);
        return addressDto;
    }

    public AddressDto rndDataForEditAddress(int id, int contactId) {
        addressDto = new AddressDto();
        addressDto.setId(id);
        addressDto.setCity(editCity);
        addressDto.setCountry(editCountry);
        addressDto.setStreet(editStreet);
        addressDto.setZip(editZip);
        addressDto.setContactId(contactId);
        return addressDto;
    }

    public void createAddress(int code, int contactId) {
        String endpoint = "/api/address";
        Object body = rndDataForCreateAddress(contactId);
        response = postRequest(endpoint, code, body);
    }

    public void editAddress(int code, int id, int contactId) {
        String endpoint = "/api/address";
        Object body = rndDataForEditAddress(id, contactId);
        putRequest(endpoint, code, body);
    }

    public void deleteAddress(int code, int id) {
        String endpoint = "/api/address/{id}";
        response = deleteRequest(endpoint, code, id);
    }

    public Response getAllAddress(int code, int contactId) {
        String endpoint = "/api/address/{contactId}/all";
        response = getRequestWithParam(endpoint, code, "contactId", contactId); //"contactID"
        return response;
    }

    public Response getAddress(int code, int id) {
        String endpoint = "/api/address/{id}";
        response = getRequestWithParam(endpoint, code, "id", id);
        return response;
    }
}
