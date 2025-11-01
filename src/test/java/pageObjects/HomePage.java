package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//span[@class='caret']")
	WebElement linkMyAccount;
	
	@FindBy(xpath = "//a[normalize-space()='Register']")
	WebElement linkRegister;
	
	@FindBy(xpath = "//a[normalize-space()='Login']")
	WebElement linkLogin;
	
	//method to click MyAccount
	public void clickMyAccount() {
		linkMyAccount.click();
	}
	
	// to click register link
	public void clickRegister() {
		linkRegister.click();
	}
	
	//to click login link
	public void clickLogin() {
		linkLogin.click();	}

}
