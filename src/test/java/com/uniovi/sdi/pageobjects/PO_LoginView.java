package com.uniovi.sdi.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView {

	static public void fillLoginForm(WebDriver driver, String dnip, String passwordp) {
		// Recogemos Usuario
		WebElement dni = driver.findElement(By.name("username"));
		dni.click();
		dni.clear();
		dni.sendKeys(dnip);

		// Recogemos Password
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);

		// Pulsamos botón
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

}
