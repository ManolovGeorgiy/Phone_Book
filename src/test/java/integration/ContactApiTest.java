package integration;

import integration.contact.ContactApi;
import integration.schemas.ContactDto;
import integration.user.UserApi;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class ContactApiTest {

    UserApi userApi;
    ContactApi contactApi;
    private void checkContactData(int contactId, ContactDto contactData){

        JsonPath actualObjects = contactApi.getContact(200, contactId).jsonPath();
        LinkedHashMap<String,String> contactObjects = new LinkedHashMap<>();
        contactObjects.put(actualObjects.getString("firstName"),contactData.getFirstName());
        contactObjects.put(actualObjects.getString("lastName"),contactData.getLastName());
        contactObjects.put(actualObjects.getString("description"),contactData.getDescription());
        for (Map.Entry<String,String> contactObject:contactObjects.entrySet()){
            String actualResult = contactObject.getKey();
            String expectedResult =contactObject.getValue();
            Assert.assertEquals(actualResult,expectedResult, actualResult + " is not equals " + expectedResult);
        }
    }
    @Test
    public void userCanWorkWithContactViaApiTest(){

        String email = "newtest@gmail.com";
        String password = "newtest@gmail.com";

        // login as user and get Access token from Response Header
        userApi = new UserApi();

        String token = userApi.login(email,password,200);

        contactApi = new ContactApi(token); // put Access token to class which need token for requests
        JsonPath object = contactApi.createContact(201).jsonPath();
        int contactId = object.getInt( "id");//"[0].id"
        checkContactData(contactId,contactApi.rndDataForCreateContact());

        // update Contact
        contactApi.editContact(200, contactId);
        checkContactData(contactId,contactApi.rndDataForEditContact(contactId));

        // delete Contact

        contactApi.deleteContact(200, contactId);
        JsonPath actualDeletedObject = contactApi.getContact(500, contactId).jsonPath();
        String errorMessage = actualDeletedObject.getString("message");
        Assert.assertEquals(errorMessage,"Error! This contact doesn't exist in our DB");
    }
}
