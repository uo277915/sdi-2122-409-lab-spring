package com.uniovi.sdi;

import com.uniovi.sdi.pageobjects.*;
import com.uniovi.sdi.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2122409SpringApplicationTests {
    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
    static String Geckodriver = "C:\\Users\\Andrés\\OneDrive - Universidad de Oviedo\\Documentos\\SDI\\Session 5\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
    //static String Geckodriver = "/Users/USUARIO/selenium/geckodriver-v0.30.0-macos";
    //Común a Windows y a MACOSX

    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp() {
        driver.navigate().to(URL);
    }

    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }

    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {
    }

    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    @Test
    @Order(1)
    void PR01A() {
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
    }

    @Test
    @Order(2)
    void PR01B() {
        List<WebElement> welcomeMessageElement = PO_HomeView.getWelcomeMessageText(driver,
                PO_Properties.getSPANISH());
        Assertions.assertEquals(welcomeMessageElement.get(0).getText(),
                PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH()));
    }

    //PR02. Opción de navegación. Pinchar en el enlace Registro en la página home
    @Test
    @Order(3)
    public void PR02() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
    }

    //PR03. Opción de navegación. Pinchar en el enlace Identifícate en la página home
    @Test
    @Order(4)
    public void PR03() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
    }

    //PR04. Opción de navegación. Cambio de idioma de Español a Inglés y vuelta a Español
    @Test
    @Order(5)
    public void PR04() {
        PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish",
                PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
    }


    //PR05. Prueba del formulario de registro. registro con datos correctos
    @Test
    @Order(6)
    public void PR05() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");
        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06a. Prueba del formulario de registro. DNI repetido en la BD
    // Propiedad: Error.signup.dni.duplicate
    @Test
    @Order(7)
    public void PR06A() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.duplicate", PO_Properties.getSPANISH());
        //Comprobamos el error de DNI repetido.
        String checkText = PO_HomeView.getP().getString("Error.signup.dni.duplicate", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06B. Prueba del formulario de registro. Nombre corto.
    // Propiedad: Error.signup.name.length
    @Test
    @Order(8)
    public void PR06B() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());

        String checkText = PO_HomeView.getP().getString("Error.signup.name.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06C. Prueba del formulario de registro. Nombre largo.
    // Propiedad: Error.signup.name.length
    @Test
    @Order(100)
    public void PR06C() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "juansebastiangabiriatercerodelacallereal", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());

        String checkText = PO_HomeView.getP().getString("Error.signup.name.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06D. Prueba del formulario de registro. Apellido corto.
    // Propiedad: Error.signup.lastName.length
    @Test
    @Order(101)
    public void PR06D() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Juan", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());

        String checkText = PO_HomeView.getP().getString("Error.signup.lastName.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06E. Prueba del formulario de registro. Apellido largo.
    // Propiedad: Error.signup.lastName.length
    @Test
    @Order(102)
    public void PR06E() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Pereeeeeeeeeeeeeeeeeeeeeeeeeeeeeez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());

        String checkText = PO_HomeView.getP().getString("Error.signup.lastName.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06F. Prueba del formulario de registro. Contraseña corta.
    // Propiedad: Error.signup.password.length
    @Test
    @Order(103)
    public void PR06F() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Perez", "uwu", "uwu");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());

        String checkText = PO_HomeView.getP().getString("Error.signup.password.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06G. Prueba del formulario de registro. Contraseña larga.
    // Propiedad: Error.signup.password.length
    @Test
    @Order(104)
    public void PR06G() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Perez", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());

        String checkText = PO_HomeView.getP().getString("Error.signup.password.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06H. Prueba del formulario de registro. Contraseñas no coincidentes.
    // Propiedad: Error.signup.passwordConfirm.coincidence
    @Test
    @Order(105)
    public void PR06H() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Perez", "77777", "nosetio");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());

        String checkText = PO_HomeView.getP().getString("Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR07: Identificación válida con usuario de ROL usuario (99999990A/123456).
    @Test
    @Order(9)
    public void PR07() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        //COmprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR08: Identificación válida con usuario de ROL profesor (99999993D/123456).
    @Test
    @Order(10)
    public void PR08() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999993D", "123456");

        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR09: Identificación válida con usuario de ROL Administrador (99999988F/123456).
    @Test
    @Order(11)
    public void PR09() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999988F", "123456");

        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR10: Identificación inválida con usuario de ROL alumno (99999990A/123456)
    @Test
    @Order(12)
    public void PR10() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "nomeacuerdo");

        String checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR11: Identificación válida y desconexión con usuario de ROL usuario (99999990A/123456).
    @Test
    @Order(13)
    public void PR11() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        //Clicamos en log out.
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

        String checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR12. Loguearse, comprobar que se visualizan 4 filas de notas y desconectarse usando el rol de
    @Test
    @Order(14)
    public void PR12() {
        PO_PrivateView.doLogIn(driver, "99999990A", "123456", "Notas del usuario");

        //Contamos el número de filas de notas
        List<WebElement> markList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr", PO_View.getTimeout());
        Assertions.assertEquals(4, markList.size());

        PO_PrivateView.doLogOut(driver);
    }

    //PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion = Nota A2.
    @Test
    @Order(15)
    public void PR13() {
        PO_PrivateView.doLogIn(driver, "99999990A", "123456", "Notas del usuario");

        //SeleniumUtils.esperarSegundos(driver, 1);
        //Contamos las notas
        By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]");
        driver.findElement(enlace).click();

        PO_PrivateView.checkForElement(driver, "Detalles de la nota");

        PO_PrivateView.doLogOut(driver);
    }

    //P14. Loguearse como profesor y Agregar Nota A2.
    //P14. Esta prueba podría encapsularse mejor ...
    @Test
    @Order(16)
    public void PR14() {

        PO_PrivateView.doLogIn(driver, "99999993D", "123456", "99999993D");

        PO_PrivateView.clickMenuOption(driver, "marks-menu", "mark/add");

        String checkText = "Nota Nueva 1";
        PO_PrivateView.fillFormAddMark(driver, 3, checkText, "8");

        PO_PrivateView.goToLastPage(driver);
        PO_PrivateView.checkForElement(driver, checkText);

        PO_PrivateView.doLogOut(driver);
    }

    //PRN. Loguearse como profesor, vamos a la ultima página y Eliminamos la Nota Nueva 1.
    //PRN. Ver la lista de Notas.
    @Test
    @Order(17)
    public void PR15() {

        PO_PrivateView.doLogIn(driver, "99999993D", "123456", "99999993D");

        PO_PrivateView.clickMenuOption(driver, "marks-menu", "mark/list");

        PO_PrivateView.goToLastPage(driver);

        PO_PrivateView.deleteMark(driver, "Nota Nueva 1");

        PO_PrivateView.goToLastPage(driver);

        //Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "Nota Nueva 1", PO_View.getTimeout());

        PO_PrivateView.doLogOut(driver);
    }


}
