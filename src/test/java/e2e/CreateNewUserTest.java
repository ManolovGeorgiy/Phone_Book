package e2e;

import com.github.javafaker.Faker;
import e2e.enums.ContactInfoTabs;
import e2e.pages.LoginPage;
import e2e.pages.address.AddAddressDialog;
import e2e.pages.address.AddressesInfoPage;
import e2e.pages.contact.AddContactDialog;
import e2e.pages.contact.ContactInfoPage;
import e2e.pages.contact.ContactsPage;
import e2e.pages.contact.DeleteContactDialog;
import e2e.pages.email.AddEmailDialog;
import e2e.pages.email.EmailInfoPage;
import e2e.pages.phone.AddPhoneDialog;
import e2e.pages.phone.PhonesPage;
import integration.user.UserApi;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateNewUserTest extends TestBase {
    LoginPage loginPage;
    UserApi userApi;
    ContactsPage contactsPage;
    AddContactDialog addContactDialog;
    ContactInfoPage contactInfoPage;
    PhonesPage phonesPage;
    AddPhoneDialog addPhoneDialog;
    EmailInfoPage emailInfoPage;
    AddEmailDialog addEmailDialog;
    AddressesInfoPage addressesInfoPage;
    AddAddressDialog addAddressDialog;
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

    @Epic(value = "UserNewRegistration")
    @Feature(value = "User can be created")
    @Description(value = " User can be created . " + " Contact info created ")
    @Severity(SeverityLevel.CRITICAL)
    @AllureId("1")
    @Test(description = "Work with new create user")
    public void workWithNewCreateUser() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String language = "English";
        String firstName = "Georgiy";
        String lastName = "Manolov";
        String description = "Germany,Berlin";

        userApi = new UserApi();
        String token = userApi.newUserRegistration(email, password, 201);

        userApi.getNewUserActivation(200, token);

        loginPage = new LoginPage(app.driver);
        loginPage.waitForLoading();
        loginPage.login(email, password);
        loginPage.waitForLoading();

        contactsPage = new ContactsPage(app.driver);
        contactsPage.selectLanguage(language);
        Assert.assertEquals(contactsPage.getLanguage(), language);

        addContactDialog = contactsPage.openAddContactDialog();
        addContactDialog.waitForOpen();
        addContactDialog.setAddContactForm(firstName, lastName, description);
        addContactDialog.saveContact();
        //addContactDialog.takeUserInfoScreenshot();

        contactInfoPage = new ContactInfoPage(app.driver);
        contactInfoPage.waitForLoading();
        checkContactData(contactInfoPage, firstName, lastName, description);

        contactInfoPage.openTab(ContactInfoTabs.PHONES);

        // create phone
        phonesPage = new PhonesPage(app.driver);
        phonesPage.waitForLoading();
        //phonesPage.takePhonesPageScreenshot();
        phonesPage.openPhoneButton();

        addPhoneDialog = new AddPhoneDialog(app.driver);
        addPhoneDialog.waitForOpen();
        addPhoneDialog.selectCountryCode(addPhoneDialog.getCountry());
        addPhoneDialog.setPhoneNumberInput("15724689321");
        addPhoneDialog.savePhone();

        phonesPage = new PhonesPage(app.driver);
        //
        phonesPage.takePhonesPageScreenshot();
        phonesPage.waitForLoading();

        contactInfoPage.openTab(ContactInfoTabs.EMAILS);

        // create email
        emailInfoPage = new EmailInfoPage(app.driver);
        emailInfoPage.waitForLoading();
        emailInfoPage.clickOnAddEmailButton();

        addEmailDialog = new AddEmailDialog(app.driver);
        addEmailDialog.waitForLoading();
        addEmailDialog.setEmailInput("g.manolov@gmail.com");
        addEmailDialog.saveEmailButtonClick();

        emailInfoPage = new EmailInfoPage(app.driver);
        emailInfoPage.waitForLoading();
        //emailInfoPage.takeEmailInfoPageScreenshot();
        emailInfoPage.waitForLoading();

        contactInfoPage.openTab(ContactInfoTabs.ADDRESSES);

        // create address
        addressesInfoPage = new AddressesInfoPage(app.driver);
        addressesInfoPage.waitForLoading();
        addressesInfoPage.clickOnAddressButton();

        addAddressDialog = new AddAddressDialog(app.driver);
        addAddressDialog.selectCountry("Germany");
        addAddressDialog.setCity("Berlin");
        addAddressDialog.setPostCode("13593");
        addAddressDialog.setStreet("Kudamm");
        addAddressDialog.addressAddSaveButtonClick();

        addressesInfoPage = new AddressesInfoPage(app.driver);
        addressesInfoPage.waitForLoading();
        //addressesInfoPage.takeAddressInfoPageScreenshot();

        contactInfoPage.openContactsPage();
        contactsPage.waitForLoading();

        contactsPage.filterByContact(firstName);
        contactsPage.waitForLoading();

        // delete contact
        deleteContactDialog = contactsPage.openDeleteDialog();
        deleteContactDialog.waitForOpen();
        deleteContactDialog.setConfirmDeletion();
        deleteContactDialog.removeContact();
        deleteContactDialog = new DeleteContactDialog(app.driver);
        //deleteContactDialog.deleteContactDialogScreenshot();
    }
}