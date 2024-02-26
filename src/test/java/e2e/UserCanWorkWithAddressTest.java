package e2e;

import com.github.javafaker.Faker;
import e2e.enums.ContactInfoTabs;
import e2e.pages.*;
import e2e.pages.address.AddAddressDialog;
import e2e.pages.address.AddressesInfoPage;
import e2e.pages.address.EditAddressDialog;
import e2e.pages.contact.AddContactDialog;
import e2e.pages.contact.ContactInfoPage;
import e2e.pages.contact.ContactsPage;
import integration.contact.ContactApi;
import integration.user.UserApi;
import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserCanWorkWithAddressTest extends TestBase {
    LoginPage loginPage;
    UserApi userApi;
    ContactApi contactApi;
    ContactsPage contactsPage;
    AddContactDialog addContactDialog;
    ContactInfoPage contactInfoPage;
    AddressesInfoPage addressesInfoPage;
    AddAddressDialog addAddressDialog;
    EditAddressDialog editAddressDialog;

    Faker faker = new Faker();

    private void checkContactData(ContactInfoPage page, String firsName, String lastName, String description) {
        String actualFirstName = page.getFirstName();
        String actualLastName = page.getLastName();
        String actualDescription = page.getDescription();
        Assert.assertEquals(actualFirstName, firsName, actualFirstName + "is not equal " + firsName);
        Assert.assertEquals(actualLastName, lastName, actualLastName + "is not equal " + lastName);
        Assert.assertEquals(actualDescription, description, actualDescription + "is not equal " + description);
    }
    private void checkAddressData(AddressesInfoPage page, String country, String city, String postCode, String street) {
        String actualCountryName = page.getCountry();
        String actualCityName = page.getCity();
        String actualPostCode = page.getPostCode();
        String actualStreetName = page.getStreet();
        Assert.assertEquals(actualCountryName, country, actualCountryName + "is not equal " + country);
        Assert.assertEquals(actualCityName, city, actualCityName + "is not equal " + city);
        Assert.assertEquals(actualPostCode, postCode, actualPostCode + "is not equal " + postCode);
        Assert.assertEquals(actualStreetName, street, actualStreetName + "is not equal " + street);
    }
    @Test
    public void userCanWorkWithContactTest(){
        String email = "newTest@gmail.com";
        String password = "newtest@gmail.com";
        String language = "English";
        String country = "Germany";
        String city = "Berlin";
        String postCode = "19455";
        String street = "Dresdner st 8";

        String editCountry = "Angola";
        String editCity = "Buraban";
        String editPostCode = "1990";
        String editStreet = "Gdetotam 10";

        String firsName = faker.internet().uuid();
        String lastName = faker.internet().uuid();
        String description = faker.lorem().sentence();

        //logged as user
        loginPage = new LoginPage(app.driver);
        loginPage.waitForLoading();
        loginPage.login(email, password);

        //check that user was logged
        contactsPage = new ContactsPage(app.driver);
        contactsPage.waitForLoading();
        contactsPage.selectLanguage(language);
        String actualLanguage = contactsPage.getLanguage();
        Assert.assertEquals(actualLanguage, language);

        //add contact
        addContactDialog = contactsPage.openAddContactDialog();
        addContactDialog.waitForOpen();
        addContactDialog.setAddContactForm(firsName, lastName, description);
        addContactDialog.saveContact();

        //check  create contact
        contactInfoPage = new ContactInfoPage(app.driver);
        contactInfoPage.waitForLoading();
        checkContactData(contactInfoPage, firsName, lastName, description);

        //addAddress
        addressesInfoPage = new AddressesInfoPage(app.driver);
        addressesInfoPage.openTab(ContactInfoTabs.ADDRESSES);
        addressesInfoPage.clickOnAddressButton();
        addressesInfoPage.waitForLoading();

        addAddressDialog = new AddAddressDialog(app.driver);
        addAddressDialog.selectCountry(country);
        addAddressDialog.setCity(city);
        addAddressDialog.setPostCode(postCode);
        addAddressDialog.setStreet(street);
        addAddressDialog.addressAddSaveButtonClick();

        //check created Addresses

        addressesInfoPage = new AddressesInfoPage(app.driver);
        addressesInfoPage.waitForLoading();
        checkAddressData(addressesInfoPage, country, city, postCode, street);


        //edit Addresses
        editAddressDialog = addressesInfoPage.openEditAddressDialog();
        editAddressDialog.waitForOpen();
        editAddressDialog.selectCountry(editCountry);
        editAddressDialog.setCityInput(editCity);
        editAddressDialog.setPostCodeInput(editPostCode);
        editAddressDialog.setStreetInput(editStreet);
        editAddressDialog.saveChanges();
        addressesInfoPage.waitForLoading();

        //check  edited Addresses
        addressesInfoPage = new AddressesInfoPage(app.driver);
        addressesInfoPage.waitForLoading();
        checkAddressData(addressesInfoPage, editCountry, editCity, editPostCode, editStreet);


        //check search form
        addressesInfoPage.filterByPostCode(editPostCode);
        addressesInfoPage.waitForLoading();

        //remove Address
        addressesInfoPage.deleteAddress();
        addressesInfoPage.waitForLoading();

        //check that contact was deleted
        Assert.assertTrue(contactsPage.isNoResultMessageDisplayed(), "No result message is not visible");
        contactsPage.takeScreenshotNoResultMessage();
    }

    @Epic(value = "Contact")
    @Feature(value = "User can Add edit delete address")
    @Description(value = "User can Add edit delete address for new contact")
    @Severity(SeverityLevel.CRITICAL)
    @AllureId("3")
    @Test(description = "Work with address for new contact")
    public void workWithAddressForNewContact(){
        String email = "newtest@gmail.com";
        String password = "newtest@gmail.com";
        String country = "Germany";
        String city = "Berlin";
        String postCode = "19455";
        String street = "Dresdner st 8";

        String editCountry = "Angola";
        String editCity = "Buraban";
        String editPostCode = "1990";
        String editStreet = "Gdetotam 10";

        userApi = new UserApi();
        String token = userApi.login(email, password, 200);

        contactApi = new ContactApi(token);
        JsonPath json = contactApi.createContact(201).jsonPath();
        int contactId = json.getInt("id");

        loginPage = new LoginPage(app.driver);
        loginPage.login(email,password);

        contactsPage = new ContactsPage(app.driver);
        contactsPage.waitForLoading();
        app.driver.get("http://phonebook.telran-edu.de:8080/contacts/"+contactId);

        contactInfoPage = new ContactInfoPage(app.driver);
        contactInfoPage.waitForLoading();
        contactInfoPage.openTab(ContactInfoTabs.ADDRESSES);

        addressesInfoPage = new AddressesInfoPage(app.driver);
        addressesInfoPage.waitForLoading();
        addressesInfoPage.takeAddressInfoPageScreenshot();
        addressesInfoPage.clickOnAddressButton();


        addAddressDialog = new AddAddressDialog(app.driver);
        addAddressDialog.selectCountry(country);
        addAddressDialog.setCity(city);
        addAddressDialog.setPostCode(postCode);
        addAddressDialog.setStreet(street);
        addAddressDialog.addressAddSaveButtonClick();


        addressesInfoPage = new AddressesInfoPage(app.driver);
        addressesInfoPage.waitForLoading();
        checkAddressData(addressesInfoPage, country, city, postCode, street);


        editAddressDialog = addressesInfoPage.openEditAddressDialog();
        editAddressDialog.waitForOpen();
        editAddressDialog.selectCountry(editCountry);
        editAddressDialog.setCityInput(editCity);
        editAddressDialog.setPostCodeInput(editPostCode);
        editAddressDialog.setStreetInput(editStreet);
        editAddressDialog.saveChanges();
        addressesInfoPage.waitForLoading();

        addressesInfoPage = new AddressesInfoPage(app.driver);
        addressesInfoPage.waitForLoading();
        checkAddressData(addressesInfoPage, editCountry, editCity, editPostCode, editStreet);



        addressesInfoPage.filterByPostCode(editPostCode);
        addressesInfoPage.waitForLoading();


        addressesInfoPage.deleteAddress();

        contactApi.deleteContact(200,contactId);
    }
}

