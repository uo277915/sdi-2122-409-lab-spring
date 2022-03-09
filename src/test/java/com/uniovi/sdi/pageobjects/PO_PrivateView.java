package com.uniovi.sdi.pageobjects;

import com.uniovi.sdi.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView {

    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep) {
        //Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
        //Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
        //Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    /**
     * Logs in and check if the login was correct.
     *
     * @param driver    The web driver.
     * @param username  The username of the user.
     * @param password  The password of the user.
     * @param checkText The string we have to check to verify it.
     */
    static public void doLogIn(WebDriver driver, String username, String password, String checkText) {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, username, password);

        checkForElement(driver, checkText);

    }

    /**
     * Logs out the user.
     *
     * @param driver The web driver.
     */
    static public void doLogOut(WebDriver driver) {
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }

    /**
     * Accesses the paging system and goes to the last page.
     *
     * @param driver The web driver.
     */
    public static void goToLastPage(WebDriver driver) {
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        elements.get(elements.size() - 1).click();
    }

    /**
     * Check whether an element is on screen.
     *
     * @param driver    The web driver.
     * @param checkText The text to search on screen.
     */
    public static void checkForElement(WebDriver driver, String checkText) {
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());
    }

    /**
     * Clicks an option on a menu from the screen.
     *
     * @param driver      The web driver.
     * @param menu        The string defining the menu.
     * @param option_href The href that the option redirects you to.
     */
    public static void clickMenuOption(WebDriver driver, String menu, String option_href) {
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id, '" + menu + "')]/a");
        elements.get(0).click();

        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, '" + option_href + "')]");
        elements.get(0).click();
    }

    /**
     * Deletes a mark from the system.
     *
     * @param driver   The web driver.
     * @param markName The name of the mark.
     */
    public static void deleteMark(WebDriver driver, String markName) {
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
        elements.get(0).click();
    }
}

