package e2e.pages.contact;

import e2e.pages.contact.ContactInfoPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditContactForm extends ContactInfoPage {
    public EditContactForm(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[@class='btn btn-secondary cancel-btn-ec']")
    WebElement cancelButton;
    @FindBy(xpath = "//button[@class='btn btn-primary submit-btn-ec']")
    WebElement saveButton;
    @FindBy(xpath = "//input[@name='input-ec-firstName']")
    WebElement firstNameInput;
    @FindBy(xpath = "//input[@name='input-ec-lastName']")
    WebElement lastNameInput;
    @FindBy(xpath = "//textarea[@name='input-ec-description']")
    WebElement descriptionInput;

    public void waitForOpen(){
        getWait().forVisibility(firstNameInput);
        getWait().forVisibility(lastNameInput);
        getWait().forVisibility(descriptionInput);
        getWait().forVisibility(saveButton);
        getWait().forClickable(saveButton);
    }
    public void setFirstNameInput(String firstName){
        setInput(firstNameInput,firstName);
    }

    public  void setLastNameInput(String lastName){
        setInput(lastNameInput,lastName);
    }

    public  void  setDescriptionInput(String description){
        setInput(descriptionInput,description);
    }

    public  void saveChanges() {

        saveButton.click();
        getWait().forInvisibility(saveButton);
    }
}