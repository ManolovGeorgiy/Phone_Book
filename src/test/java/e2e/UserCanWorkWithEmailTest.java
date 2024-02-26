package e2e;

import com.github.javafaker.Faker;
import e2e.enums.ContactInfoTabs;
import e2e.pages.LoginPage;
import e2e.pages.contact.AddContactDialog;
import e2e.pages.contact.ContactInfoPage;
import e2e.pages.contact.ContactsPage;
import e2e.pages.contact.DeleteContactDialog;
import e2e.pages.email.AddEmailDialog;
import e2e.pages.email.EditEmailDialog;
import e2e.pages.email.EmailInfoPage;
import integration.contact.ContactApi;
import integration.user.UserApi;
import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserCanWorkWithEmailTest extends TestBase {

    LoginPage loginPage;
    UserApi userApi;
    ContactApi contactApi;
    ContactsPage contactsPage;
    AddContactDialog addContactDialog;
    ContactInfoPage contactInfoPage;
    EmailInfoPage emailInfoPage;
    AddEmailDialog addEmailDialog;
    EditEmailDialog editEmailDialog;
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

    private void checkEmailData(EmailInfoPage page, String email) {
        String actualEmailName = page.getEmail();
        Assert.assertEquals(actualEmailName, email, actualEmailName + "is not equal " + email);
    }

    @Test
    public void userCanWorkWithEmailTest() {
        String email = "newTest@gmail.com";
        String password = "newtest@gmail.com";
        String language = "English";
        String expectedEmail = "newhrest@gmail.com";

        String editExpectedEmail = "new.manolov@gmail.com";

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
        contactInfoPage.openTab(ContactInfoTabs.EMAILS);

        // create email
        emailInfoPage = new EmailInfoPage(app.driver);
        emailInfoPage.waitForLoading();
        emailInfoPage.clickOnAddEmailButton();
        addEmailDialog = new AddEmailDialog(app.driver);
        addEmailDialog.waitForLoading();
        addEmailDialog.setEmailInput(expectedEmail);
        addEmailDialog.saveEmailButtonClick();

        // check email data
        emailInfoPage = new EmailInfoPage(app.driver);
        emailInfoPage.waitForLoading();
        checkEmailData(emailInfoPage, expectedEmail);
        emailInfoPage.waitForLoading();

        // edit email dialog
        editEmailDialog = emailInfoPage.openEditEmailDialog();
        editEmailDialog.waitForOpen();
        editEmailDialog.setEditEmail(editExpectedEmail);
        editEmailDialog.saveEmailChanges();
        emailInfoPage.waitForLoading();

        //check edited email
        checkEmailData(emailInfoPage, editExpectedEmail);
        emailInfoPage.waitForLoading();

        //check search form
        emailInfoPage.filterByEmail(editExpectedEmail);
        emailInfoPage.waitForLoading();

        //delete email
        emailInfoPage.deleteEmail();

        //open contacts page
        contactInfoPage.openContactsPage();
        contactsPage.waitForLoading();
        //filter by contact name
        contactsPage.filterByContact(firsName);
        contactsPage.waitForLoading();

        //check row
        int actualContactCountRow = contactsPage.getContactCount();
        Assert.assertEquals(actualContactCountRow, 1, "Contact count row after filter should be 1");

        //delete contact
        deleteContactDialog = contactsPage.openDeleteDialog();
        deleteContactDialog.waitForOpen();
        deleteContactDialog.setConfirmDeletion();
        deleteContactDialog.removeContact();

        //check that contact was deleted
        Assert.assertTrue(contactsPage.isNoResultMessageDisplayed(), "No result message is not visible");
        contactsPage.takeScreenshotNoResultMessage();
    }

    @Epic(value = "Contact")
    @Feature(value = "User can Add edit delete email")
    @Description(value = "User can Add edit delete email for new contact")
    @Severity(SeverityLevel.CRITICAL)
    @AllureId("2")
    @Test(description = "Work with email for new contact")
    public void workWithEmailForNewContact() {
        String email = "newtest@gmail.com";
        String password = "newtest@gmail.com";

        String expectedEmail = "newhrest@gmail.com";
        String editExpectedEmail = "new.manolov@gmail.com";

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
        contactInfoPage.openTab(ContactInfoTabs.EMAILS);

        // create email
        emailInfoPage = new EmailInfoPage(app.driver);
        emailInfoPage.waitForLoading();
        emailInfoPage.takeEmailInfoPageScreenshot();
        emailInfoPage.clickOnAddEmailButton();

        addEmailDialog = new AddEmailDialog(app.driver);
        addEmailDialog.waitForLoading();
        addEmailDialog.setEmailInput(expectedEmail);
        addEmailDialog.saveEmailButtonClick();

        // check email data
        emailInfoPage = new EmailInfoPage(app.driver);
        emailInfoPage.waitForLoading();
        checkEmailData(emailInfoPage, expectedEmail);


        // edit email dialog
        editEmailDialog = emailInfoPage.openEditEmailDialog();
        editEmailDialog.waitForOpen();
        editEmailDialog.setEditEmail(editExpectedEmail);
        editEmailDialog.saveEmailChanges();
        emailInfoPage.waitForLoading();

        //check edited email
        checkEmailData(emailInfoPage, editExpectedEmail);
        emailInfoPage.waitForLoading();

        //check search form
        emailInfoPage.filterByEmail(editExpectedEmail);
        emailInfoPage.waitForLoading();

        //delete email
        emailInfoPage.deleteEmail();
        contactApi.deleteContact(200, contactId);
    }
}