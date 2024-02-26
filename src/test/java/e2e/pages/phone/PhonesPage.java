package e2e.pages.phone;

import e2e.pages.contact.ContactInfoPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PhonesPage extends ContactInfoPage {
    @FindBy(xpath = "//*[@id='items-table-phone']")
    WebElement table;
    @FindBy(xpath = "//*[@formcontrolname='searchInput']")
    WebElement searchInput;
    @FindBy(xpath = "//*[@id='btn-add-phone']")
    WebElement addPhoneButton;
    @FindBy(xpath = "//*[@class='row-table-cc']")
    WebElement countryCodeField;
    @FindBy(xpath = "//*[@class='row-table-pn']")
    WebElement phoneNumberField;
    @FindBy(xpath = "//*[@class='nav-item ml-auto dropdown']")
    WebElement dropdown;
    @FindBy(xpath = "//*[@class='dropdown-item btn-phone-edit']")
    WebElement editButton;
    @FindBy(xpath = "//*[@class='dropdown-item btn-phone-remove']")
    WebElement removePhoneButton;

    public PhonesPage(WebDriver driver) {
        super(driver);
    }

    @Step
    public void waitForLoading() {
        getWait().forVisibility(searchInput);
        getWait().forVisibility(addPhoneButton);
        getWait().forVisibility(table);
    }

    @Step
    public void openPhoneButton() {
        addPhoneButton.click();
        getWait().forVisibility(addPhoneButton);
    }

    @Step
    public String getCountry() {
        return countryCodeField.getText();
    }

    @Step
    public String getPhoneNumber() {
        return phoneNumberField.getText();
    }

    @Step
    public EditPhoneDialog openEditPhoneForm() {
        dropdown.click();
        getWait().forVisibility(editButton);
        editButton.click();
        return new EditPhoneDialog(driver);
    }

    @Step
    public void deletePhone() {
        dropdown.click();
        getWait().forVisibility(removePhoneButton);
        removePhoneButton.click();
    }

    @Step
    public void takePhonesPageScreenshot() {
        takeAndCompareScreenshot("phonesPage", null);
    }

}





