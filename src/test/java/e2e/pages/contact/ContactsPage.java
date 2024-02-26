package e2e.pages.contact;

import e2e.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ContactsPage extends BasePage {
    public ContactsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='collapse navbar-collapse']")
    public WebElement header;
    @FindBy(xpath = "//div[@class='collapse navbar-collapse']//*[@href='/']")
    WebElement contactsButton;
    @FindBy(xpath = "//*[@href='/contacts']")
    WebElement addContactButton;
    @FindBy(xpath = "//select[@id='langSelect']")
    WebElement languageDropdown;
    @FindBy(xpath = "//*[@id='contacts-list']")
    WebElement contactsList;
    @FindBy(xpath = "//*[@class='list-group']")
    List<WebElement> contactRows;
    @FindBy(xpath = "//*[@formcontrolname='searchInput']")
    WebElement searchInput;
    @FindBy(xpath = "//*[@ng-reflect-router-link='/account']")
    WebElement accountButton;
    @FindBy(xpath = "//*[@src='/assets/icons/trash.svg']")
    WebElement deleteButton;
    @FindBy(xpath = "//*[@type='warning']")
    WebElement noResultsMessage;
    @FindBy(xpath = "//*[text()='Logout']")
    WebElement logoutButton;
    @Step
    public void waitForLoading(){
        getWait().forVisibility(header);
        getWait().forVisibility(contactsButton);
        getWait().forVisibility(addContactButton);
        getWait().forVisibility(contactsList);
        getWait().forAllVisibility(contactRows);
        getWait().forClickable(addContactButton);
        getWait().forClickable(contactsButton);
    }
    @Step
    public void openContactsPage(){
        contactsButton.click();
    }
    @Step
    public int getContactCount(){
        return driver.findElements(By.xpath("//*[@id='contacts-list']//*[@class='list-group']")).size();
    }
    @Step
    public AddContactDialog openAddContactDialog(){
        addContactButton.click();
        return new AddContactDialog(driver);
    }
    @Step("Select language: {language}")
    public void selectLanguage(String language){
        getSelect(languageDropdown).selectByVisibleText(language);
    }
    @Step
    public String getLanguage(){
        return getSelect(languageDropdown).getFirstSelectedOption().getText();
    }

    @Step
    public DeleteContactDialog openDeleteDialog(){
        getWait().forClickable(deleteButton);
        deleteButton.click();
        return new DeleteContactDialog(driver);
    }
    @Step("Filter by value: {contactValue}")
    public void filterByContact(String contactValue){
        searchInput.sendKeys(contactValue);
    }

    @Step("Check displayed is no result message")
    public boolean isNoResultMessageDisplayed(){
        getWait().forVisibility(noResultsMessage);
        return isElementDisplayed(noResultsMessage);
    }
    @Step
    public void takeScreenshotHeader(){
        takeAndCompareScreenshot("header", header);
    }

    @Step
    public void takeScreenshotNoResultMessage(){
        takeAndCompareScreenshot("contactsPageNoResultMessage", noResultsMessage);
    }
}
