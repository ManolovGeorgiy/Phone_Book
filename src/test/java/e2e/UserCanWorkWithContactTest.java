package e2e;

import com.github.javafaker.Faker;
import e2e.pages.*;
import e2e.pages.contact.*;
import e2e.utils.DataProviders;
import integration.contact.ContactApi;
import integration.user.UserApi;
import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserCanWorkWithContactTest extends TestBase {
    LoginPage loginPage;
    UserApi userApi;
    ContactApi contactApi;
    ContactsPage contactsPage;
    AddContactDialog addContactDialog;
    ContactInfoPage contactInfoPage;
    EditContactForm editContactForm;
    DeleteContactDialog deleteContactDialog;

    Faker faker = new Faker();

    private void checkContactData(ContactInfoPage page,String firstName,String lastName,String description){
        String actualFirstName = page.getFirstName();
        String actualLastName = page.getLastName();
        String actualDescription = page.getDescription();
        Assert.assertEquals(actualFirstName,firstName,actualFirstName + " is not equal " + firstName);
        Assert.assertEquals(actualLastName,lastName,actualLastName + " is not equal " + lastName);
        Assert.assertEquals(actualDescription,description,actualDescription + " is not equal " + description);
    }
    @Test(dataProvider = "newContact",dataProviderClass = DataProviders.class)
    public void userCanWorkWithContactTest(String firstName, String lastName, String description) {
        String email = "newtest@gmail.com";
        String password = "newtest@gmail.com";
        String language = "English";

        String editFirstName = faker.internet().uuid();
        String editLastName = faker.internet().uuid();
        String editDescription = faker.lorem().sentence();

        // login as user " войти как пользователь "
        loginPage=new LoginPage(app.driver);
        loginPage.waitForLoading();
        loginPage.login(email,password);

        // Check that user was logged " Проверьте, что пользователь залогинился "
        contactsPage = new ContactsPage(app.driver);
        contactsPage.waitForLoading();
        contactsPage.selectLanguage(language);
        Assert.assertEquals(contactsPage.getLanguage(),language);

        // add contact " добавить контакт "
        addContactDialog = contactsPage.openAddContactDialog();
        addContactDialog.waitForOpen();
        addContactDialog.setAddContactForm(firstName,lastName,description);
        addContactDialog.saveContact();

        // check created contact " Создать новый контакт "
        contactInfoPage = new ContactInfoPage(app.driver);
        contactInfoPage.waitForLoading();
        checkContactData(contactInfoPage,firstName,lastName,description);

        // edit contact " изменить контакт "
        editContactForm = contactInfoPage.openEditContactForm();
        editContactForm.waitForOpen();
        editContactForm.setFirstNameInput(editFirstName);
        editContactForm.setLastNameInput(editLastName);
        editContactForm.setDescriptionInput(editDescription);
        editContactForm.saveChanges();

        //check edited contact
        contactInfoPage.waitForLoading();
        checkContactData(contactInfoPage,editFirstName,editLastName,editDescription);

        //open contacts page
        contactInfoPage.openContactsPage();
        contactsPage.waitForLoading();

        //filter by contact name
        contactsPage.filterByContact(editFirstName);
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
    @Feature(value = "User can Add edit delete contact")
    @Description(value = "User can Add edit delete new contact")
    @Severity(SeverityLevel.CRITICAL)
    @AllureId("4")
    @Test(description = "Work with new contact")

    public void workWithNewContact(){

        String email = "newtest@gmail.com";
        String password = "newtest@gmail.com";
        String language = "English";

        String firstName = faker.internet().uuid();
        String lastName = faker.internet().uuid();
        String description = faker.lorem().sentence();

        String editFirstName = faker.internet().uuid();
        String editLastName = faker.internet().uuid();
        String editDescription = faker.lorem().sentence();

        userApi = new UserApi();
        String token = userApi.login(email, password, 200);

        contactApi = new ContactApi(token);
        JsonPath json = contactApi.createContact(201).jsonPath();
        int contactId = json.getInt("id");
        app.driver.get("http://phonebook.telran-edu.de:8080/contacts/"+contactId);

        loginPage = new LoginPage(app.driver);
        loginPage.waitForLoading();
        loginPage.login(email,password);

        contactsPage = new ContactsPage(app.driver);
        contactsPage.waitForLoading();
        contactsPage.selectLanguage(language);
        Assert.assertEquals(contactsPage.getLanguage(),language);

        addContactDialog = contactsPage.openAddContactDialog();
        addContactDialog.waitForOpen();
        addContactDialog.setAddContactForm(firstName,lastName,description);
        addContactDialog.saveContact();

        // check created contact " Создать новый контакт "
        contactInfoPage = new ContactInfoPage(app.driver);
        contactInfoPage.waitForLoading();
        checkContactData(contactInfoPage,firstName,lastName,description);

        // edit contact " изменить контакт "
        editContactForm = contactInfoPage.openEditContactForm();
        editContactForm.waitForOpen();
        editContactForm.setFirstNameInput(editFirstName);
        editContactForm.setLastNameInput(editLastName);
        editContactForm.setDescriptionInput(editDescription);
        editContactForm.saveChanges();

        //check edited contact
        contactInfoPage.waitForLoading();
        checkContactData(contactInfoPage,editFirstName,editLastName,editDescription);

        //open contacts page
        contactInfoPage.openContactsPage();
        contactsPage.waitForLoading();

        //filter by contact name
        contactsPage.filterByContact(editFirstName);
        contactsPage.waitForLoading();

        //delete contact
        deleteContactDialog = contactsPage.openDeleteDialog();
        deleteContactDialog.waitForOpen();
        deleteContactDialog.setConfirmDeletion();
        deleteContactDialog.removeContact();
    }
}


