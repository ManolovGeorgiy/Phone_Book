package e2e;

import com.github.javafaker.Faker;
import e2e.enums.ContactInfoTabs;
import e2e.pages.LoginPage;
import e2e.pages.contact.AddContactDialog;
import e2e.pages.contact.ContactInfoPage;
import e2e.pages.contact.ContactsPage;
import e2e.pages.contact.DeleteContactDialog;
import e2e.pages.phone.AddPhoneDialog;
import e2e.pages.phone.EditPhoneDialog;
import e2e.pages.phone.PhonesPage;
import integration.contact.ContactApi;
import integration.user.UserApi;
import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserCanWorkWithPhoneTest extends TestBase {
    LoginPage loginPage;
    UserApi userApi;
    ContactsPage contactsPage;
    ContactApi contactApi;
    AddContactDialog addContactDialog;
    ContactInfoPage contactInfoPage;
    PhonesPage phonesPage;
    AddPhoneDialog addPhoneDialog;
    EditPhoneDialog editPhoneForm;
    DeleteContactDialog deleteContactDialog;


    Faker faker = new Faker();

    private void checkContactData(ContactInfoPage page, String firsName, String lastName, String description) {
        String actualFirstName = page.getFirstName();
        String actualLastName = page.getLastName();
        String actualDescription = page.getDescription();
        Assert.assertEquals(actualFirstName, firsName, actualFirstName + "is not equal " + firsName);
        Assert.assertEquals(actualLastName, lastName, actualLastName + "is not equal " + lastName);
        Assert.assertEquals(actualDescription, description, actualDescription + "is not equal " + description);
    }

    private void checkPhoneData(PhonesPage page, String country, String phoneNumber) {
        phonesPage = new PhonesPage(app.driver);
        String actualCountry = page.getCountry();
        String actualPhoneNumber = page.getPhoneNumber();
        Assert.assertEquals(actualCountry, country, actualCountry + "is not equal" + country); // актуальное с ожидаемым
        Assert.assertEquals(actualPhoneNumber, phoneNumber, actualPhoneNumber + "is not equal" + phoneNumber); // актуальное с ожидаемым

    }

    @Test
    public void userCanAddPhoneNumber() {
        String email = "newtest@gmail.com";
        String password = "newtest@gmail.com";
        String language = "English";


        String firstName = faker.internet().uuid(); // faker генерирует рандомные данные через генератор uuid
        String lastName = faker.internet().uuid();
        String description = faker.lorem().sentence(); // рандомный текст

        // login as user
        loginPage = new LoginPage(app.driver);
        loginPage.waitForLoading();
        loginPage.login(email, password);

        // check that user was logged
        contactsPage = new ContactsPage(app.driver);
        contactsPage.waitForLoading();
        contactsPage.selectLanguage(language);
        Assert.assertEquals(contactsPage.getLanguage(), language);

        // Add contact
        addContactDialog = contactsPage.openAddContactDialog();
        addContactDialog.waitForOpen(); // только для диалога
        addContactDialog.setAddContactForm(firstName, lastName, description);
        addContactDialog.saveContact();

        // open Phone Tab
        contactInfoPage = new ContactInfoPage(app.driver);
        contactInfoPage.waitForLoading();
        checkContactData(contactInfoPage, firstName, lastName, description);
        contactInfoPage.openTab(ContactInfoTabs.PHONES);

        // add phone number
        phonesPage = new PhonesPage(app.driver);
        //phonesPage.waitForLoading();
        phonesPage.openPhoneButton();

        //fill addPhoneDialog
        addPhoneDialog = new AddPhoneDialog(app.driver);
        addPhoneDialog.waitForOpen();
        addPhoneDialog.selectCountryCode(addPhoneDialog.getCountry());
        addPhoneDialog.setPhoneNumberInput("12345678911");
        addPhoneDialog.savePhone();


        // check created phone
        phonesPage = new PhonesPage(app.driver);
        phonesPage.waitForLoading();
        checkPhoneData(phonesPage, phonesPage.getCountry(), phonesPage.getPhoneNumber());


        //
        editPhoneForm = phonesPage.openEditPhoneForm();
        editPhoneForm.waitForOpen();
        editPhoneForm.selectCountryCode(editPhoneForm.getCountry());
        editPhoneForm.setPhoneNumberInput("11987654321");
        editPhoneForm.saveChange();
        phonesPage.waitForLoading();

        //
        phonesPage.deletePhone();

        // open contact page
        contactInfoPage.openContactsPage();
        contactsPage.waitForLoading();

        // filter by contact name
        contactsPage.filterByContact(firstName);
        contactsPage.waitForLoading(); //дождаться момента по фильтрации

        // check rows count after filter by contact name
        int actualContactCountRow = contactsPage.getContactCount();
        Assert.assertEquals(actualContactCountRow, 1, "Contact count row after filter should be 1");

        // check that contact was deleted
        deleteContactDialog = contactsPage.openDeleteDialog();
        deleteContactDialog.waitForOpen();
        deleteContactDialog.setConfirmDeletion();
        deleteContactDialog.removeContact();

        Assert.assertTrue(contactsPage.isNoResultMessageDisplayed(), " No result message is not visible");
        contactsPage.takeScreenshotNoResultMessage();
    }

    @Epic(value = "Contact")
    @Feature(value = "User can Add edit delete phone")
    @Description(value = "User can Add edit delete phone for new contact")
    @Severity(SeverityLevel.CRITICAL)
    @AllureId("1")
    @Test(description = "Work with phone for new contact")
    public void workWithPhoneForNewContact() {
        String email = "newtest@gmail.com";
        String password = "newtest@gmail.com";

        userApi = new UserApi();
        String token = userApi.login(email, password, 200);

        contactApi = new ContactApi(token);
        JsonPath json = contactApi.createContact(201).jsonPath();
        int contactId = json.getInt("id");

        loginPage = new LoginPage(app.driver);
        loginPage.login(email, password);

        contactsPage = new ContactsPage(app.driver);
        contactsPage.waitForLoading();
        app.driver.get("http://phonebook.telran-edu.de:8080/contacts/" + contactId);

        contactInfoPage = new ContactInfoPage(app.driver);
        contactInfoPage.waitForLoading();
        contactInfoPage.openTab(ContactInfoTabs.PHONES);

        phonesPage = new PhonesPage(app.driver);
        phonesPage.waitForLoading();
        phonesPage.takePhonesPageScreenshot();
        phonesPage.openPhoneButton();

        addPhoneDialog = new AddPhoneDialog(app.driver);
        addPhoneDialog.waitForOpen();
        addPhoneDialog.selectCountryCode(addPhoneDialog.getCountry());
        addPhoneDialog.setPhoneNumberInput("12345678911");
        addPhoneDialog.savePhone();

        phonesPage = new PhonesPage(app.driver);
        phonesPage.waitForLoading();
        checkPhoneData(phonesPage, phonesPage.getCountry(), phonesPage.getPhoneNumber());

        editPhoneForm = phonesPage.openEditPhoneForm();
        editPhoneForm.waitForOpen();
        editPhoneForm.selectCountryCode(editPhoneForm.getCountry());
        editPhoneForm.setPhoneNumberInput("11987654321");
        editPhoneForm.saveChange();
        phonesPage.waitForLoading();

        phonesPage.deletePhone();

        contactApi.deleteContact(200, contactId);
    }
}