package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.LogoutPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

/* Data is valid - Login Success - Pass - Logout
 * Data valid - Login Failed - Fail
 * 
 *  Data Invalid - Login Failed - Pass
 *  Data Invalid - Login Success - Fail - Logout
 */


public class TC_03_LoginDDT extends BaseClass {
	
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="Regression")	//get dataprovider from different class
	public void verify_loginDDT(String email, String pwd, String exp) {
		
		logger.info("***** Starting TC_03_LoginDDT **********");
		
		try
		{
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			LoginPage lp = new LoginPage(driver);
			lp.setLoginEmail(email);
			lp.setLoginPassword(pwd);
			
			lp.clickLogin();
			//Thread.sleep(3000);
			
			MyAccountPage myAcc = new MyAccountPage(driver);
			boolean targetPage = myAcc.isMyAccountPageExists();	//tells login is success-  i used to check
			
			LogoutPage logp = new LogoutPage(driver);
			
			if(exp.equalsIgnoreCase("Valid")) {
				if(targetPage == true) {
					Assert.assertTrue(true);	//Test case Passed
					hp.clickMyAccount();
					myAcc.clickLogout();
					logp.clickContinue();
				}else {
					Assert.assertTrue(false);	//Test Failed
				}
			}else {
				if(targetPage == true) {
					hp.clickMyAccount();
					myAcc.clickLogout();
					logp.clickContinue();
					Assert.assertTrue(false);	//Test Failed
				}else {
					Assert.assertTrue(true);	//Test Passed
				}
			}
		}catch(Exception e) {
			Assert.fail();
		}
		logger.info("***** Finished TC_03_LoginDDT **********");
	}
}
