package com.datadriven.testcases;

//import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.datadriven.base.BaseClass;
//import com.datadriven.base.TestBase;
//import com.datadriven.rough.GenericMethods;
import com.datadriven.utilities.ExcelReader;

public class ApprovePurchaseOrder extends BaseClass {
	public  WebDriverWait  wait;
	@Test
	public void defApprovePurchaseOrder() throws InterruptedException {
		test=extent.createTest("defApprovePurchaseOrder");
		driver.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);

	
		// Explicitly wait until the purchasing tab is present
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement menuPurchasing = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//td[contains(text(),'Purchasing')]"))));

		String parentWindow = driver.getWindowHandle();
		log.debug("Parent Window > " + parentWindow);
		Thread.sleep(3000);

		// Click purchasing tab
		menuPurchasing.click();
		log.debug("Purchasing tab is clicked");

		// Click New/Revised tab
		WebElement menuNewRevisePO = driver.findElement(By.xpath("//td[contains(text(),'New/Revised')]"));
		menuNewRevisePO.click();
		log.debug(" New/Revised tab is clicked");
		Thread.sleep(3000);

		// Enter Filegroup
		WebElement txtFileGroup = driver.findElement(By.name("FileGroup"));
		txtFileGroup.click();
		Thread.sleep(2000);
		txtFileGroup.sendKeys(config.getProperty("filegroup"));
		Thread.sleep(5000);
		txtFileGroup.sendKeys(Keys.ENTER);
		log.debug(" Filegroup is entered");
		Thread.sleep(5000);

/*List<WebElement> noOfPOs= driver.findElements(By.xpath("(//tr[@role='listitem'])//td[3]"));
		System.out.println("no of POs: " +noOfPOs.size() );
		
		for (WebElement elements : noOfPOs) {
			String value=elements.getText();
			System.out.println(value);
		} */
		
		//Enter PO number in the column filter
		
		String projectPath = System.getProperty("user.dir");

		ExcelReader reader = new ExcelReader(projectPath + "\\src\\test\\resources\\excel\\TestData_PO_Approval.xlsx");
		int rowCount = reader.getRowCount("PurchaseOrdersToBeApproved");
		System.out.println("Row count is: " +rowCount);
		
		for (int rowNum = 2; rowNum <= rowCount; rowNum++) {
			String poNumber = reader.getCellData("PurchaseOrdersToBeApproved", " ponumber", rowNum);
			System.out.println(poNumber);
			
			try {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@name = 'HONO$148l'])[2]")));
				//Actions action = new Actions(driver);
			  // action.moveToElement( driver.findElement(By.xpath("(//*[@name = 'HONO$148l'])[2]"))).click().build().perform();;
				WebElement  poColumnFilter=driver.findElement(By.xpath("(//*[@name = 'HONO$148l'])[2]"));
			   	poColumnFilter.clear();
				poColumnFilter.sendKeys(poNumber);
				poColumnFilter.sendKeys(Keys.ENTER);
				Thread.sleep(3000);
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class = 'checkboxFalse'])[2]")));
			WebElement poCheckBox=driver.findElement(By.xpath("(//*[@class = 'checkboxFalse'])[2]"));
			poCheckBox.click();
			System.out.println("Check box clicked");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[(text() = 'Approve' or . = 'Approve')])[2]")));
			Thread.sleep(3000);
			
			WebElement btnApprove= driver.findElement(By.xpath("(//*[(text() = 'Approve' or . = 'Approve')])[2]"));
			btnApprove.click();
			System.out.println("Approve button clicked");
			Thread.sleep(5000);
					
			WebElement btnOk1= driver.findElement(By.xpath("(//td[@class='buttonRounded'])[2]"));
			btnOk1.click();
			System.out.println("OK button  clicked");
			Thread.sleep(2000);
		
			WebElement btnOk2= driver.findElement(By.xpath("(//td[@class='buttonRounded'])[3]"));
			btnOk2.click();
			System.out.println("OK button  clicked");
			Thread.sleep(5000);
					
		
	}
		
	
	}

}
