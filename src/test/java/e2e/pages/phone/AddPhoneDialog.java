package e2e.pages.phone;

import io.qameta.allure.Step;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddPhoneDialog extends PhonesPage {
    public AddPhoneDialog(WebDriver driver) {
        super(driver);
    }
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

    @Step("Wait for open Add Contact Dialog")
    public void waitForOpen() {
        getWait().forVisibility(countryCodeLabel);
        getWait().forVisibility(countryCodeDropDown);
        getWait().forVisibility(phoneNumberField);
        getWait().forVisibility(phoneNumberInput);
        getWait().forVisibility(saveButton);
    }
    @Step("Select country code: {country}")
    public void selectCountryCode(String country) {
        getSelect(countryCodeDropDown).selectByVisibleText(country);
    }
    @Step
    public String getCountry() {
        return getSelect(countryCodeDropDown).getFirstSelectedOption().getText();
    }
    @Step("Set phone number: {phoneNumber}")
    public void setPhoneNumberInput(String phoneNumber) {
        setInput(phoneNumberInput, phoneNumber);
    }
    @Step
    public void savePhone() {
        try {
            getWait().forClickable(saveButton);
            saveButton.click();
            getWait().forInvisibility(countryCodeLabel);
        } catch (StaleElementReferenceException e) { //отображается старый элемент
            e.printStackTrace();
        }
    }
}