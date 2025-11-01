package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC_01_AccountRegistration extends BaseClass {
		
		
		@Test(groups={"Master","Regression"})
		void verify_account_registration() {
			
			logger.info("****** Starting TC_01_AccountRegistration ********************");
			try 
			{
				HomePage hp = new HomePage(driver);
				hp.clickMyAccount();
				logger.info("****** Clicked on My Account Link ********************");
				hp.clickRegister();
				logger.info("****** Clicked on Registration Link ********************");
				
				String title = driver.getTitle();
				Assert.assertEquals(title, "Register Account");
				
				AccountRegistrationPage accReg = new AccountRegistrationPage(driver);
				logger.info("****** Providing Customer Details ********************");
				accReg.setFirstName(randomString());
				accReg.setLastName(randomString());
				accReg.setEmail(randomString()+"@etc.com");
				accReg.setTelephone(randomNumber());
				accReg.setPassword("xyz123456");
				accReg.setConfirmPassword("xyz123456");
				
				accReg.checkPrivacyPolicy();
				accReg.clickContinue();
				
				logger.info("****** Validating Message ************");
				String getMsg = accReg.getConfirmationmsg();
				
	//			String getMsg1 = accReg.getFailedmsg();
	//			
	//			if(getMsg.equals("Your Account Has Been Created!")) {
	//				Assert.assertTrue(true);
	//			}else {
	//				Assert.assertTrue(false);
	//			}
				Assert.assertEquals(getMsg, "Your Account Has Been Created!");
				
			}catch (AssertionError | Exception e) {
	            logger.error("Test Failed due to exception....");
	            logger.debug("Debug logs....");
	            Assert.fail();
	        }
	            logger.info("****** Finished TC_01_AccountRegistration ********************");
		}

}
