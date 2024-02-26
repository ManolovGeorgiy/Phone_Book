package e2e.pages.address;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddAddressDialog extends AddressesInfoPage {
    @FindBy(xpath = "//*[@id='cc-select']")
    WebElement countryDropdown;
    @FindBy(xpath = "//*[@id='input-city']")
    WebElement cityInput;
    @FindBy(xpath = "//*[@id='input-zip']")
    WebElement postCodeInput;
    @FindBy(xpath = "//*[@id='input-street']")
    WebElement streetInput;
    @FindBy(xpath = "//*[@class='btn btn-primary btn-block']")
    WebElement saveButton;

    public AddAddressDialog(WebDriver driver) {
        super(driver);
    }

    public void waitForOpen() {
        getWait().forVisibility(countryDropdown);
        getWait().forVisibility(cityInput);
        getWait().forVisibility(postCodeInput);
        getWait().forVisibility(streetInput);
        getWait().forVisibility(saveButton);
    }

    public void selectCountry(String country) {
        getSelect(countryDropdown).selectByVisibleText(country);
    }

    public void setCity(String city) {
        cityInput.sendKeys(city);
    }

    public void setPostCode(String postCode) {
        postCodeInput.sendKeys(postCode);
    }

    public void setStreet(String street) {
        streetInput.sendKeys(street);
    }

    public void addressAddSaveButtonClick() {
        saveButton.click();
    }
}
