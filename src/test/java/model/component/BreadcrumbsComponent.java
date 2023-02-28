package model.component;

import io.qameta.allure.Step;
import model.page.HomePage;
import model.component.base.BaseComponent;
import model.page.status.FolderStatusPage;
import model.page.status.FreestyleProjectStatusPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BreadcrumbsComponent extends BaseComponent {

    @FindBy(id = "breadcrumbs")
    private WebElement breadcrumbs;

    @FindBy(css = "#breadcrumbs li a")
    protected WebElement topMenuRoot;

    @FindBy(xpath = "//li[@class='item'][last()-1]")
    private WebElement parentFolderRoot;

    public BreadcrumbsComponent(WebDriver driver) {
        super(driver);
    }

    @Step("Get full username from the breadcrumbs")
    public String getTextBreadcrumbs() {
        return breadcrumbs.getText();
    }

    @Step("Click 'Dashboard' link")
    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }

    public FolderStatusPage clickParentFolder() {
        parentFolderRoot.click();

        return new FolderStatusPage(getDriver());
    }

    @Step("Click Freestyle project '{name}' to refresh the page")
    public FreestyleProjectStatusPage clickFreestyleProjectNameOnBreadcrumbs(String name) {
        getDriver().findElement(By.xpath("//ul[@id = 'breadcrumbs']//a[contains(@href,'/job/"+ name + "/')]")).click();

        return new FreestyleProjectStatusPage(getDriver());
    }
}