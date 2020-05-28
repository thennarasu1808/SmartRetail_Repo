package com.datadriven.testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.Test;


import com.datadriven.base.BaseClass;
//import com.datadriven.base.TestBase;
import com.datadriven.utilities.ExcelReader;

public class CreatePurchaseOrder extends BaseClass {

	@Test
	public void defCreatePurchaseOrder() throws InterruptedException {
		test=extent.createTest("defCreatePurchaseOrder");
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

		// Click on New order tab
		WebElement btnNewOrder = driver.findElement(By.xpath("//div[contains(text(),'New Order')]"));
		btnNewOrder.click();
		log.debug(" New order tab is clicked");
		Thread.sleep(5000);

		/*
		 * //Getting the child window
		 * 
		 * for (String handle : driver.getWindowHandles()) {
		 * driver.switchTo().window(handle); System.out.println("Child window: "
		 * +handle); }
		 */

		// Creating PO - fetching the values from excel sheet

		String projectPath = System.getProperty("user.dir");

		ExcelReader reader = new ExcelReader(projectPath + "\\src\\test\\resources\\excel\\TestData_PO_Ceation.xlsx");
		int rowCount = reader.getRowCount("PurchaseOrderCreation");
		System.out.println("Row count is: " +rowCount);
		
		for (int rowNum = 2; rowNum <= rowCount; rowNum++) {
			String blockout = reader.getCellData("PurchaseOrderCreation", " blockout", rowNum);
			System.out.println(blockout);

			String vendor = reader.getCellData("PurchaseOrderCreation", " vendor", rowNum).replace(".0", "");
			System.out.println(vendor);

			String shipTo = reader.getCellData("PurchaseOrderCreation", " shipto", rowNum).replace(".0", "");
			System.out.println(shipTo);

			String department = reader.getCellData("PurchaseOrderCreation", " department", rowNum).replace(".0", "");
			System.out.println(department);

			String QntyOrdered = reader.getCellData("PurchaseOrderCreation", " orderquantity", rowNum).replace(".0",
					"");
			System.out.println(QntyOrdered);

			String itemNum = reader.getCellData("PurchaseOrderCreation", " itemnumber", rowNum);
			System.out.println(itemNum);

			try {
				WebElement poBlockOut = driver.findElement(By.name("HBLK"));
				poBlockOut.click();
				poBlockOut.sendKeys(blockout);
				Thread.sleep(2000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				WebElement ImgPoNumber = driver.findElement(By.xpath(
						" (.//*[normalize-space(text()) and normalize-space(.)='Unassigned'])[1]/following::img[1]"));
				ImgPoNumber.click();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				WebElement poVendor = driver.findElement(By.name("HVEN"));
				poVendor.click();
				poVendor.sendKeys(vendor);
				Thread.sleep(3000);
				poVendor.sendKeys(Keys.ENTER);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				WebElement poShiptoStore = driver.findElement(By.name("HSTR"));
				poShiptoStore.click();
				poShiptoStore.sendKeys(shipTo);
				Thread.sleep(3000);
				poShiptoStore.sendKeys(Keys.ENTER);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				WebElement poDepartment = driver.findElement(By.name("HDPT"));
				poDepartment.click();
				poDepartment.sendKeys(department);
				Thread.sleep(3000);
				poDepartment.sendKeys(Keys.ENTER);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			WebElement btnSave = driver.findElement(By.xpath("//*[(text() = 'Save' or . = 'Save')]"));
			btnSave.click();
			Thread.sleep(8000);

			WebElement tabItems = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[(text() = 'Items' or . = 'Items')]")));
			tabItems.click();
			Thread.sleep(3000);

			WebElement txtGroupBy = driver.findElement(
					By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Group by'])[3]/following::div[1]"));
			String groupByTextValue = txtGroupBy.getText();
			System.out.println("Gropu by text :" + groupByTextValue);
			// txtGroupBy.click();

			if (!groupByTextValue.equalsIgnoreCase("Style/Color/Size")) {
				driver.findElement(By.xpath(
						"(.//*[normalize-space(text()) and normalize-space(.)='Group by'])[3]/following::div[1]"))
						.click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[(text() = 'Style/Color/Size' or . = 'Style/Color/Size')]")).click();
				;
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[(text() = 'Add Item' or . = 'Add Item')]")).click();
			} else {

				driver.findElement(By.xpath("//*[(text() = 'Add Item' or . = 'Add Item')]")).click();
			}
			Thread.sleep(2000);

			WebElement txtItemNumber = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='ItemId']")));
			txtItemNumber.click();
			txtItemNumber.sendKeys(itemNum);
			txtItemNumber.sendKeys(Keys.TAB);
			Thread.sleep(3000);

			WebElement txtQtyOrdered = driver.findElement(By.xpath("//*[@name = 'IQTY']"));
			txtQtyOrdered.sendKeys(QntyOrdered);
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//tr[18]/td[2]/table/tbody/tr/td[2]/span/img")).click();
			driver.findElement(By.xpath("//*/text()[normalize-space(.)='122 - MPO desc']/parent::*")).click();
			Thread.holdsLock(2000);
			driver.findElement(By.xpath("//tr[18]/td[4]/table/tbody/tr/td[2]/span/img")).click();
			driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Item number'])[1]/following::div[14]")).click();

			driver.findElement(
					By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Exit'])[2]/following::td[1]"))
					.click();
			Thread.sleep(8000);
			System.out.println("clicked save and exit");

			Actions a = new Actions(driver);

			try {

				// driver.switchTo().alert().accept();
				System.out.println("TrY cATCH  ");
				a.moveToElement(driver.findElement(By.xpath(
						"(.//*[normalize-space(text()) and normalize-space(.)='Book retail should not be greater than suggested retail.'])[1]/following::td[1]")))
						.click().build().perform();
				System.out.println("Warning message 'OK' button Clicked");
			} catch (Exception e) {
				System.out.println("unexpected alert not present");
							}
			Thread.sleep(10000);

				
				try {
				driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Exit'])[1]/following::td[1]")).click();
		
				} catch (StaleElementReferenceException e) {
				
					WebElement saveAndExit=driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Exit'])[1]/following::td[1]"));
					saveAndExit.clear();
					System.out.println(e.getMessage());
					System.out.println("Stale Element handled");
				}
				
			}
			
			}
		}
	
