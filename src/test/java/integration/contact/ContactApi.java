package integration.contact;

import com.github.javafaker.Faker;
import integration.ApiBase;
import integration.schemas.ContactDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ContactApi extends ApiBase {

    Response response;
    ContactDto dto;
    Faker faker = new Faker();
    String firstName = faker.internet().uuid();
    String lastName = faker.internet().uuid();
    String description = faker.lorem().sentence();
    String editFirstName = faker.internet().uuid();
    String editLastName = faker.internet().uuid();
    String editDescription = faker.lorem().sentence();
    public ContactApi(String token) {
        super(token);
    }

    @Step("Generate request body for create contact")
    public ContactDto rndDataForCreateContact() {
        dto = new ContactDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setDescription(description);
        return dto;
    }

    @Step("Generate request body for edit contact with id:{id}")
    public ContactDto rndDataForEditContact(int id) {
        dto = new ContactDto();
        dto.setId(id);
        dto.setFirstName(editFirstName);
        dto.setLastName(editLastName);
        dto.setDescription(editDescription);
        return dto;
    }

    @Step("Create contact")
    public Response createContact(int code) {
        String endpoint = "/api/contact";
        Object body = rndDataForCreateContact();
        response = postRequest(endpoint, code, body);
        response.as(ContactDto.class);
        return response;
    }

    @Step("Edit contact with id:{id}")
    public void editContact(int code, int id) {
        String endpoint = "/api/contact";
        Object body = rndDataForEditContact(id);
        putRequest(endpoint, code, body);
    }

    @Step("Delete contact with id:{id}")
    public void deleteContact(int code, int id) {
        String endpoint = "/api/contact/{id}";
        response = deleteRequest(endpoint, code, id);
    }

    @Step("Get contact with id:{id}")
    public Response getContact(int code, int id) {
        String endpoint = "/api/contact/{id}";
        response = getRequestWithParam(endpoint, code, "id", id); //"contactID"
        return response;
    }
}
