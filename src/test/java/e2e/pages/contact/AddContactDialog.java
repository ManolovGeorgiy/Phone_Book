package e2e.pages.contact;

import io.qameta.allure.Step;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddContactDialog extends ContactsPage {

    public AddContactDialog(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@role='dialog']")
    WebElement dialog;

    @FindBy(xpath = "//*[@id='form-name']")
    WebElement firstNameInput;

    @FindBy(xpath = "//*[@id='form-lastName']")
    WebElement lastNameInput;

    @FindBy(xpath = "//*[@id='form-about']")
    WebElement descriptionInput;

    @FindBy(xpath = "//*[@type='reset']")
    WebElement cancelButton;

    @FindBy(xpath = "//*[@role='dialog']//*[@type='submit']")
    WebElement saveButton;

    @FindBy(xpath = "//*[@aria-label='Close']")
    WebElement closeWindowsButton;

    @FindBy(xpath = "//*[@id='form-error-firstName']")
    WebElement errorMessage;

    public void waitForOpen(){
        getWait().forVisibility(dialog);
        getWait().forVisibility(firstNameInput);
        getWait().forVisibility(lastNameInput);
        getWait().forVisibility(descriptionInput);
        getWait().forVisibility(saveButton);
    }
    public void setFirstNameInput(String firstName){
        setInput(firstNameInput,firstName);
    }

    public void setLastNameInput(String lastName){
        setInput(lastNameInput,lastName);
    }

    public void setDescription(String description){
        setInput(descriptionInput,description);
    }

    public void setAddContactForm(String firstName, String lastName, String description){
        setFirstNameInput(firstName);
        setLastNameInput(lastName);
        setDescription(description);
    }
    public void saveContact(){
        try {
            getWait().forClickable(saveButton);
            saveButton.click();
            getWait().forInvisibility(dialog);
        }catch (StaleElementReferenceException e){
        e.printStackTrace();
        }
    }
    @Step
    public void takeUserInfoScreenshot(){
        takeAndCompareScreenshot("userInfo", null);
    }
}
