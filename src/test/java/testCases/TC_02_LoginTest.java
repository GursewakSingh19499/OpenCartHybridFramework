package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC_02_LoginTest extends BaseClass {
  
	@Test(groups="Master")
  public void verify_login() {
		
		logger.info("***** Starting TC_02_LoginTest **********");
		try
		{
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			LoginPage lp = new LoginPage(driver);
			//get values from property file
			lp.setLoginEmail(p.getProperty("email"));
			lp.setLoginPassword(p.getProperty("password"));
			
			lp.clickLogin();
			
			MyAccountPage myAcc = new MyAccountPage(driver);
			boolean targetPage = myAcc.isMyAccountPageExists();
			
			Assert.assertEquals(targetPage, true, "Login failed");
			//Assert.assertTrue(targetPage);
		}catch (AssertionError | Exception e) {
			Assert.fail(e.getMessage());
		}
		logger.info("***** Finished TC_02_LoginTest **********");
  }
}
