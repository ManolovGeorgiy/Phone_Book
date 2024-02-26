package integration;


import integration.contact.ContactApi;
import integration.email.EmailApi;
import integration.schemas.EmailDto;
import integration.user.UserApi;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class EmailApiTest {

    UserApi userApi;
    ContactApi contactApi;
    EmailApi emailApi;

    private void checkEmailData(int emailId, EmailDto emailData){

        JsonPath actualObjects = emailApi.getEmail(200, emailId).jsonPath();
        LinkedHashMap<String,String> emailObjects = new LinkedHashMap<>();
        emailObjects.put(actualObjects.getString("email"),emailData.getEmail());

        for (Map.Entry<String,String> emailObject:emailObjects.entrySet()){
            String actualResult = emailObject.getKey();
            String expectedResult =emailObject.getValue();
            Assert.assertEquals(actualResult,expectedResult, actualResult + " is not equals " + expectedResult);
        }
    }
    @Test
    public void userCanWorkWithEmailViaApiTest(){

        String email = "newtest@gmail.com";
        String password = "newtest@gmail.com";

        // login as user and get Access token from Response Header
        userApi = new UserApi();
        String token = userApi.login(email, password, 200);
        contactApi = new ContactApi(token);
        JsonPath object = contactApi.createContact(201).jsonPath();
        int contactId = object.getInt("id");

        // create email
        emailApi = new EmailApi(token);
        emailApi.createEmail(201,contactId);

        JsonPath emailArrayObjects = emailApi.getAllEmail(200,contactId).jsonPath();
        int emailId = emailArrayObjects.getInt( "[0].id");//"[0].id"
        checkEmailData(emailId,emailApi.rndDataForCreateEmail(emailId));

        // update email
        emailApi.editEmail(200,emailId,contactId);
        checkEmailData(emailId,emailApi.rndDataForEditEmail(emailId,contactId));

        // delete email
        emailApi.deleteEmail(200,emailId);
        JsonPath actualDeletedObject = emailApi.getEmail(500, emailId).jsonPath();
        String errorMessage = actualDeletedObject.getString("message");
        Assert.assertEquals(errorMessage,"Error! This email doesn't exist in our DB");
    }
}
