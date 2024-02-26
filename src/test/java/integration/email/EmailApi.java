package integration.email;

import integration.ApiBase;
import integration.schemas.EmailDto;
import io.restassured.response.Response;

public class EmailApi extends ApiBase {

    EmailDto emailDto;
    Response response;
    String email = "georgiyManolov@gmail.com";
    String editEmail = "georgiy.Manolov@gmail.com";
    public EmailApi(String token) {
        super(token);
    }

    public EmailDto rndDataForCreateEmail(int contactId) {
        emailDto = new EmailDto();
        emailDto.setEmail(email);
        emailDto.setContactId(contactId);
        return emailDto;
    }

    public EmailDto rndDataForEditEmail(int id, int contactId) {
        emailDto = new EmailDto();
        emailDto.setId(id);
        emailDto.setEmail(editEmail);
        emailDto.setContactId(contactId);
        return emailDto;
    }

    public void createEmail(int code, int contactId) {
        String endpoint = "/api/email";
        Object body = rndDataForCreateEmail(contactId);
        response = postRequest(endpoint, code, body);
    }

    public void editEmail(int code, int id, int contactId) {
        String endpoint = "/api/email";
        Object body = rndDataForEditEmail(id, contactId);
        putRequest(endpoint, code, body);
    }

    public void deleteEmail(int code, int id) {
        String endpoint = "/api/email/{id}";
        response = deleteRequest(endpoint, code, id);
    }

    public Response getAllEmail(int code, int contactId) {
        String endpoint = "/api/email/{contactId}/all";
        response = getRequestWithParam(endpoint, code, "contactId", contactId); //"contactId"
        return response;
    }

    public Response getEmail(int code, int id) {
        String endpoint = "/api/email/{id}";
        response = getRequestWithParam(endpoint, code, "id", id);
        return response;
    }
}
