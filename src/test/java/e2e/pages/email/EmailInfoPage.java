package e2e.pages.email;

import e2e.pages.contact.ContactInfoPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EmailInfoPage extends ContactInfoPage {
    //import constructor!!!
    public EmailInfoPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(xpath = "//*[@id='search']")
    WebElement searchInput;
    @FindBy(xpath = "//*[@id='btn-add-phone']")
    WebElement addEmailButton;
    @FindBy(xpath = "//*[@class='row-table w-95']")
    WebElement emailInputField;
    @FindBy(xpath = "//*[@class='dropdown-toggle btn btn-outline-light btn-block']")
    WebElement optionDropDown;
    @FindBy(xpath = "//*[@class='dropdown-item btn-email-edit']")
    WebElement editEmailButton;
    @FindBy(xpath = "//*[@class='dropdown-item btn-email-remove']")
    WebElement removeEmailButton;

    @Step("Wait for loading email info page")
    public void waitForLoading() {
        getWait().forVisibility(searchInput);
        getWait().forClickable(addEmailButton);
    }
    public void clickOnAddEmailButton() {
        addEmailButton.click();
    }
    public EditEmailDialog openEditEmailDialog() {
        optionDropDown.click();
        getWait().forVisibility(editEmailButton);
        editEmailButton.click();
        return new EditEmailDialog(driver);
    }
    public String getEmail() {
        return emailInputField.getText();
    }
    @Step("Filter by email: {emailCheck}")
    public void filterByEmail(String emailCheck){
        searchInput.sendKeys(emailCheck);
    }
    @Step("Delete email")
    public void deleteEmail() {
        optionDropDown.click();
        getWait().forVisibility(removeEmailButton);
        removeEmailButton.click();
    }
    @Step
    public void takeEmailInfoPageScreenshot(){
        takeAndCompareScreenshot("emailInfoPage", null);
    }
}