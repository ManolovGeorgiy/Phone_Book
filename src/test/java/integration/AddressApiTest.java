package integration;

import integration.address.AddressApi;
import integration.contact.ContactApi;
import integration.schemas.AddressDto;
import integration.user.UserApi;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class AddressApiTest {

    UserApi userApi;
    ContactApi contactApi;
    AddressApi addressApi;

    private void checkAddressData(int addressId, AddressDto addressData) {

        JsonPath actualObjects = addressApi.getAddress(200, addressId).jsonPath();
        LinkedHashMap<String, String> addressObjects = new LinkedHashMap<>();
        addressObjects.put(actualObjects.getString("city"), addressData.getCity());
        addressObjects.put(actualObjects.getString("country"), addressData.getCountry());
        addressObjects.put(actualObjects.getString("street"), addressData.getStreet());
        addressObjects.put(actualObjects.getString("TestNG"), addressData.getZip());

        for (Map.Entry<String, String> addressObject : addressObjects.entrySet()) {
            String actualResult = addressObject.getKey();
            String expectedResult = addressObject.getValue();
            Assert.assertEquals(actualResult, expectedResult, actualResult + " is not equals " + expectedResult);
        }
    }

    @Test
    public void userCanWorkWithAddressViaApiTest() {

        String email = "newtest@gmail.com";
        String password = "newtest@gmail.com";

        // login as user and get Access token from Response Header
        userApi = new UserApi();
        String token = userApi.login(email, password, 200);
        contactApi = new ContactApi(token);
        JsonPath object = contactApi.createContact(201).jsonPath();
        int contactId = object.getInt("id");

        // create Address
        addressApi = new AddressApi(token);// put Access token to class which need token for requests
        addressApi.createAddress(201, contactId);

        JsonPath addressArrayObjects = addressApi.getAllAddress(200, contactId).jsonPath();
        int addressId = addressArrayObjects.getInt("[0].id");//"[0].id"
        checkAddressData(addressId, addressApi.rndDataForCreateAddress(addressId));

        // update Address
        addressApi.editAddress(200, addressId, contactId);
        checkAddressData(addressId, addressApi.rndDataForEditAddress(addressId, contactId));

        // delete Address
        addressApi.deleteAddress(200, addressId);

        JsonPath actualDeletedObject = addressApi.getAddress(500, addressId).jsonPath();
        String errorMessage = actualDeletedObject.getString("message");
        Assert.assertEquals(errorMessage, "Error! This address doesn't exist in our DB");
    }
}



