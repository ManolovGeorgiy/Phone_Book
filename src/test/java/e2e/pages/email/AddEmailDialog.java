package e2e.pages.email;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddEmailDialog extends EmailInfoPage {
    @FindBy(xpath = "//*[@role='dialog']")
    WebElement dialog;
    @FindBy(xpath = "//*[@id='input-email']")
    WebElement emailFieldInput;
    @FindBy(xpath = "//*[@class='btn btn-primary']")
    WebElement saveButton;
    @FindBy(xpath = "//*[@aria-label='Close']")
    WebElement closeWindowsButton;
    public AddEmailDialog(WebDriver driver) {
        super(driver);
    }

    @Step("Wait for open Add Email Dialog")
    public void waitForOpen() {
        getWait().forVisibility(dialog);
        getWait().forVisibility(emailFieldInput);
        getWait().forClickable(saveButton);
        getWait().forClickable(closeWindowsButton);
    }

    @Step("Set email input: {email}")
    public void setEmailInput(String email) {
        emailFieldInput.sendKeys(email);
    }

    public void saveEmailButtonClick() {
        saveButton.click();
    }

}