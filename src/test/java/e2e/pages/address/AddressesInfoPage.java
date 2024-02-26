package e2e.pages.address;

import e2e.pages.contact.ContactInfoPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddressesInfoPage extends ContactInfoPage {
    @FindBy(xpath = "//*[@id='search']")
    WebElement searchAddressInput;
    @FindBy(xpath = "//*[@id='btn-add-phone']")
    WebElement addAddressButton;
    @FindBy(xpath = "//*[@class='close']")
    WebElement closeButton;
    @FindBy(xpath = "//*[@class='dropdown-toggle btn btn-outline-light btn-block']")
    WebElement optionDropDown;
    @FindBy(xpath = "//*[@class='col-country']")
    WebElement countryField;
    @FindBy(xpath = "//*[@class='col-city']")
    WebElement cityField;
    @FindBy(xpath = "//*[@class='col-TestNG']")
    WebElement postCodeField;
    @FindBy(xpath = "//*[@class='col-street']")
    WebElement streetField;
    @FindBy(xpath = "//*[@class='dropdown-item btn-address-edit']")
    WebElement editButton;
    @FindBy(xpath = "//*[@class='dropdown-item btn-address-remove']")
    WebElement removeButton;
    public AddressesInfoPage(WebDriver driver) {
        super(driver);
    }

    public void waitForLoading() {
        getWait().forVisibility(searchAddressInput);
        getWait().forVisibility(addAddressButton);
    }

    public String getCountry() {
        return countryField.getText();
    }

    public String getCity() {
        return cityField.getText();
    }

    public String getPostCode() {
        return postCodeField.getText();
    }

    public String getStreet() {
        return streetField.getText();
    }

    public void clickOnAddressButton() {
        addAddressButton.click();
    }

    public EditAddressDialog openEditAddressDialog() {
        optionDropDown.click();
        getWait().forVisibility(editButton);
        editButton.click();
        return new EditAddressDialog(driver);
    }

    public void filterByPostCode(String postCodeValue) {
        searchAddressInput.sendKeys(postCodeValue);
    }

    public void deleteAddress() {
        optionDropDown.click();
        getWait().forVisibility(removeButton);
        removeButton.click();
    }

    @Step
    public void takeAddressInfoPageScreenshot() {
        takeAndCompareScreenshot("addressInfoPage", null);
    }
}

