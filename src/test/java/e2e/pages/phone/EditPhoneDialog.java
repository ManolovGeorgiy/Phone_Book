package e2e.pages.phone;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditPhoneDialog extends PhonesPage {
    @FindBy(xpath = "//*[@for='cc-select']")
    WebElement countryCodeLabel;
    @FindBy(xpath = "//*[@id='cc-select']")
    WebElement countryCodeDropDown;
    @FindBy(xpath = "//*[@for='selected-cc']")
    WebElement phoneNumberField;
    @FindBy(xpath = "//*[@id='selected-cc']")
    WebElement phoneNumberInput;
    @FindBy(xpath = "//*[@class='btn btn-primary']")
    WebElement saveButton;

    public EditPhoneDialog(WebDriver driver) {
        super(driver);
    }

    @Step("Wait for open edit phone dialog")
    public void waitForOpen() {
        getWait().forVisibility(countryCodeLabel);
        getWait().forVisibility(countryCodeDropDown);
        getWait().forVisibility(phoneNumberField);
        getWait().forVisibility(phoneNumberInput);
        getWait().forVisibility(saveButton);
    }

    @Step("Select country: {country}")
    public void selectCountryCode(String country) {
        getSelect(countryCodeDropDown).selectByVisibleText(country);
    }

    @Step
    public String getCountry() {
        return getSelect(countryCodeDropDown).getFirstSelectedOption().getText();
    }

    @Step
    public void setPhoneNumberInput(String phoneNumber) {
        setInput(phoneNumberInput, phoneNumber);
    }

    @Step
    public void saveChange() {
        saveButton.click();
        getWait().forInvisibility(saveButton);
    }
}

