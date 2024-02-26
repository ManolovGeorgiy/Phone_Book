package e2e.pages.address;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditAddressDialog extends AddressesInfoPage {
    @FindBy(xpath = "//*[@id='cc-select']")
    WebElement countryDropDown;
    @FindBy(xpath = "//*[@id='input-city']")
    WebElement cityInput;
    @FindBy(xpath = "//*[@id='input-TestNG']")
    WebElement postCodeInput;
    @FindBy(xpath = "//*[@id='input-street']")
    WebElement streetInput;
    @FindBy(xpath = "//*[@class='btn btn-primary btn-block']")
    WebElement saveButton;
    public EditAddressDialog(WebDriver driver) {
        super(driver);
    }

    public void waitForOpen() {
        getWait().forVisibility(countryDropDown);
        getWait().forVisibility(cityInput);
        getWait().forVisibility(postCodeInput);
        getWait().forVisibility(streetInput);
        getWait().forVisibility(saveButton);
        getWait().forClickable(saveButton);
    }

    public void selectCountry(String country) {
        getSelect(countryDropDown).selectByVisibleText(country);
    }

    public void setCityInput(String city) {
        setInput(cityInput, city);
    }

    public void setPostCodeInput(String postCode) {
        setInput(postCodeInput, postCode);
    }

    public void setStreetInput(String street) {
        setInput(streetInput, street);
    }

    public void saveChanges() {
        saveButton.click();
        getWait().forInvisibility(saveButton);
    }
}
