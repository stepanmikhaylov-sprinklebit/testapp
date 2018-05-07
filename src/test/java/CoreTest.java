import junit.framework.TestCase;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class CoreTest extends TestCase{
    private static final String REMOTESERVER = "http://localhost:4444/wd/hub";
    private static final String BASEURL = "http://autotesttask.herokuapp.com/";
    protected RemoteWebDriver driver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL(REMOTESERVER), options);
        driver.manage().window().maximize();
        driver.get(BASEURL);
        isReady();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        driver.close();
    }

    protected void add() throws InterruptedException {
        driver.findElementByXPath("//*[@data-qtip=\"Add\"]").click();
        Thread.sleep(200);
    }

    protected void fillRow(Row row) throws InterruptedException {
        driver.getKeyboard().sendKeys(row.getName());
        fillNextField(row.getNodes());
        fillNextField(row.getPriority());
        fillNextField(row.getDue(), false);
        fillNextField(row.getCreatedAt(),true);
        fillNextField(row.getUpdatedAt(),true);
    }

    private void fillNextField(String nodes) throws InterruptedException {
        driver.getKeyboard().sendKeys(Keys.TAB);
        Thread.sleep(200);
        driver.getKeyboard().sendKeys(nodes);
    }

    private void fillNextField(String date, Boolean includeTime) throws InterruptedException {
        driver.getKeyboard().sendKeys(Keys.TAB);
        Thread.sleep(200);
        if (date == null) {
            WebElement element = driver.switchTo().activeElement();
            WebElement parent = element.findElement(By.xpath(".."));
            parent.findElement(new By.ByCssSelector(".x-form-date-trigger")).click();
            Thread.sleep(200);
            driver.switchTo().activeElement().click();
            Thread.sleep(200);
        } else {
            driver.getKeyboard().sendKeys(date);
            Thread.sleep(200);
        }
        if (includeTime) {
            fillTime();
        }
    }

    private void fillTime() throws InterruptedException {
        driver.getKeyboard().sendKeys(Keys.TAB);
        Thread.sleep(200);
        WebElement element = driver.switchTo().activeElement();
        element.clear();
        WebElement parent = element.findElement(By.xpath(".."));
        parent.findElement(new By.ByCssSelector(".x-form-time-trigger")).click();
        Thread.sleep(200);
        List<WebElement> timers = driver.findElementsByCssSelector(".x-timepicker");
        WebElement timer = timers.get(timers.size() - 1);
        timer.findElement(new By.ByCssSelector("li:first-of-type")).click();
        Thread.sleep(200);
    }

    public void isReady() throws InterruptedException {
        int i;

        for (i = 0; i<10; i++) {
            JavascriptExecutor js = driver;
            boolean result = js.executeScript("return document.readyState").equals("complete");
            if (result) {
                break;
            }
            Thread.sleep(100);
        }
    }

    public void apply() {
        driver.findElementById("ext-gen1097").click();
    }

    public void scrollToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public List<WebElement> getRowElements() {
        return driver.findElementsByCssSelector(".x-grid-row");
    }

    public List<Row> getRowObjects(List<WebElement> elements) throws InterruptedException {
        List<Row> rows = new ArrayList<Row>();
        for (WebElement element: elements) {
            Row row = rowElementToObject(element);
            rows.add(row);
        }

        return rows;
    }

    public Row rowElementToObject(WebElement element) throws InterruptedException {
        Row row = new Row();
        row.setName(findName(element));
        row.setNodes(findNodes(element));
        row.setPriority(findPriority(element));
        row.setDue(findDue(element));
        row.setCreatedAt(findCreatedAt(element));
        row.setUpdatedAt(findUpdatedAt(element));

        return row;
    }

    private String findUpdatedAt(WebElement element) throws InterruptedException {
        if (!element.isDisplayed()) {
            scrollToElement(element);
            Thread.sleep(100);
        }
        WebElement updatedAt = element.findElement(new By.ByCssSelector(".x-grid-cell-gridcolumn-1025 div"));
        return updatedAt.getText();
    }

    private String findCreatedAt(WebElement element) throws InterruptedException {
        if (!element.isDisplayed()) {
            scrollToElement(element);
            Thread.sleep(100);
        }
        WebElement createdAt = element.findElement(new By.ByCssSelector(".x-grid-cell-gridcolumn-1024 div"));
        return createdAt.getText();
    }

    private String findDue(WebElement element) throws InterruptedException {
        if (!element.isDisplayed()) {
            scrollToElement(element);
            Thread.sleep(100);
        }
        WebElement due = element.findElement(new By.ByCssSelector(".x-grid-cell-datecolumn-1023 div"));
        return due.getText();
    }

    private String findPriority(WebElement element) throws InterruptedException {
        if (!element.isDisplayed()) {
            scrollToElement(element);
            Thread.sleep(100);
        }
        WebElement priority = element.findElement(new By.ByCssSelector(".x-grid-cell-gridcolumn-1022 div"));
        return priority.getText();
    }

    private String findNodes(WebElement element) throws InterruptedException {
        if (!element.isDisplayed()) {
            scrollToElement(element);
            Thread.sleep(100);
        }
        WebElement nodes = element.findElement(new By.ByCssSelector(".x-grid-cell-gridcolumn-1021 div"));
        return nodes.getText();
    }

    private String findName(WebElement element) throws InterruptedException {
        if (!element.isDisplayed()) {
            scrollToElement(element);
            Thread.sleep(100);
        }
        WebElement name = element.findElement(new By.ByCssSelector(".x-grid-cell-gridcolumn-1020 div"));
        return name.getText();
    }

    protected void addInForm(Row row) throws InterruptedException {
        driver.findElementByXPath("//*[@data-qtip=\"Add in form\"]").click();
        Thread.sleep(1000);
        isReady();
        fillFormAll(row, "#bugs__add_form .x-panel-body");
    }

    private void fillFormAll(Row row, String selector) throws InterruptedException {
        WebElement element = driver.findElementByCssSelector(selector);
        fillFormDone(element, true);
        fillFormName(element, row.getName());
        fillFormNodes(element, row.getNodes());
        fillFormProirity(element, row.getPriority());
        fillFormDue(element, row.getDue(), false);
        fillFormCreatedAt(element, row.getCreatedAt(), true);
        fillFormUpdatedAt(element, row.getUpdatedAt(), true);
        element.findElement(new By.ByXPath("//*[@data-qtip=\"OK\"]")).click();
    }

    private void fillFormUpdatedAt(WebElement element, String updatedAt,  Boolean includeTime) throws InterruptedException {
        WebElement dateElement = element.findElement(new By.ByCssSelector(".x-form-item:nth-of-type(8)"));
        dateElement.click();
        WebElement actionElement = driver.switchTo().activeElement();
        actionElement.clear();
        dateElement.findElement(new By.ByCssSelector(".x-form-date-trigger")).click();
        Thread.sleep(200);
        driver.switchTo().activeElement().click();
        Thread.sleep(200);
        if (includeTime) {
            fillTime();
        }
    }

    private void fillFormCreatedAt(WebElement element, String createdAt,  Boolean includeTime) throws InterruptedException {
        WebElement dateElement = element.findElement(new By.ByCssSelector(".x-form-item:nth-of-type(7)"));
        dateElement.click();
        WebElement actionElement = driver.switchTo().activeElement();
        actionElement.clear();
        dateElement.findElement(new By.ByCssSelector(".x-form-date-trigger")).click();
        Thread.sleep(200);
        driver.switchTo().activeElement().click();
        Thread.sleep(200);
        if (includeTime) {
            fillTime();
        }
    }

    private void fillFormDue(WebElement element, String due, Boolean includeTime) throws InterruptedException {
        element.findElement(new By.ByCssSelector(".x-form-item:nth-of-type(6)")).click();
        WebElement dateElement = driver.switchTo().activeElement();
        dateElement.clear();
        if (due == null) {
            WebElement parent = element.findElement(By.xpath(".."));
            parent.findElement(new By.ByCssSelector(".x-form-date-trigger")).click();
            Thread.sleep(200);
            driver.switchTo().activeElement().click();
            Thread.sleep(200);
        } else {
            driver.getKeyboard().sendKeys(due);
        }
        if (includeTime) {
            fillTime();
        }
    }

    private void fillFormProirity(WebElement element, String priority) {
        element.findElement(new By.ByCssSelector(".x-form-item:nth-of-type(5)")).click();
        WebElement actionElement = driver.switchTo().activeElement();
        actionElement.clear();
        actionElement.sendKeys(priority);
    }

    private void fillFormNodes(WebElement element, String nodes) {
        element.findElement(new By.ByCssSelector(".x-form-item:nth-of-type(4)")).click();
        WebElement actionElement = driver.switchTo().activeElement();
        actionElement.clear();
        actionElement.sendKeys(nodes);
    }

    private void fillFormName(WebElement element, String name) {
        element.findElement(new By.ByCssSelector(".x-form-item:nth-of-type(3)")).click();
        WebElement actionElement = driver.switchTo().activeElement();
        actionElement.clear();
        actionElement.sendKeys(name);
    }

    private void fillFormDone(WebElement element, boolean isChecked) {
        if (isChecked) {
            element.findElement(new By.ByCssSelector(".x-form-item:nth-of-type(2)")).click();
        }
    }

    public void editInForm(WebElement element, Row row) throws InterruptedException {
        element.click();
        driver.findElementByXPath("//*[@data-qtip=\"Edit in form\"]").click();
        Thread.sleep(500);
        isReady();
        fillFormAll(row, "#bugs__edit_form .x-panel-body");
    }

    public WebElement findElementByRow(Row row) throws InterruptedException {
        WebElement elementFound = null;

        List<WebElement> elements = getRowElements();
        for (WebElement element : elements) {
            if (rowElementToObject(element).getName().equals(row.getName())) {
                elementFound = element;
            }
        }

        return elementFound;
    }

    public void delete(WebElement element) throws InterruptedException {
        if (!element.isDisplayed()) {
            scrollToElement(element);
        }
        element.click();
        driver.findElementByXPath("//*[@data-qtip=\"Delete\"]").click();
        Thread.sleep(200);
        driver.findElementByCssSelector(".x-window .x-toolbar-footer .x-box-inner div:nth-of-type(2)").click();
        Thread.sleep(500);
    }

    public void edit(WebElement element, String name) throws InterruptedException {
        element.click();
        driver.findElementByXPath("//*[@data-qtip=\"Edit\"]").click();
        Thread.sleep(200);
        driver.getKeyboard().sendKeys(name);
    }

    public void search(List<FilterParams> params) throws InterruptedException {
        driver.findElementByXPath("//*[@data-qtip=\"Search\"]").click();
        Thread.sleep(500);
        int number = 1;
        for (FilterParams param: params) {
            addFilter(param, number);
            number++;
        }

        for (int i = 0; i<8; i++) {
            driver.getKeyboard().sendKeys(Keys.TAB);
            Thread.sleep(100);
        }

        driver.getKeyboard().sendKeys(Keys.ENTER);
        Thread.sleep(1000);
    }

    private void addFilter(FilterParams param, int number) throws InterruptedException {
        WebElement element = driver.findElementByCssSelector("#bugs__search_form__search_panel .x-panel-body .x-panel .x-panel-body .x-container:nth-of-type(" + Integer.toString(number) + ")");
        element.findElement(new By.ByCssSelector(".x-form-trigger-wrap:nth-of-type(1)")).click();
        WebElement selectoin = driver.findElementByCssSelector(".x-boundlist:last-of-type");
        WebElement field = findFieldToSelect(selectoin, param.getField());
        field.click();
        Thread.sleep(500);
        element = driver.findElementByCssSelector("#bugs__search_form__search_panel .x-panel-body .x-panel .x-panel-body .x-container:nth-of-type(" + Integer.toString(number) + ")");
        element.findElement(new By.ByCssSelector(".x-field:nth-of-type(2) .x-form-trigger-wrap")).click();
        selectoin = driver.findElementByCssSelector(".x-boundlist:last-of-type");
        WebElement rule = findFieldToSelect(selectoin, param.getRule());
        rule.click();
        element.findElement(new By.ByCssSelector(".x-field:nth-of-type(3)")).click();
        driver.getKeyboard().sendKeys(param.getValue());
        Thread.sleep(500);
    }

    private WebElement findFieldToSelect(WebElement selection, String fieldName) {
        WebElement selectedElement = null;
        List<WebElement> fiedls = selection.findElements(new By.ByCssSelector(".x-boundlist-item"));
        for (WebElement field : fiedls) {
            if (field.getText().equals(fieldName)) {
                selectedElement = field;
            }
        }

        return selectedElement;
    }

    public boolean isChecked(WebElement element) throws InterruptedException {
        if (!element.isDisplayed()) {
            scrollToElement(element);
            Thread.sleep(200);
        }
        WebElement checkbox = element.findElement(new By.ByCssSelector(".x-grid-cell-checkcolumn-1019 div div"));
        String classAtr = checkbox.getAttribute("class");
        return classAtr.contains("x-grid-checkheader-checked");
    }

    public void changeChecker(WebElement element) {
        element.findElement(new By.ByCssSelector(".x-grid-cell-checkcolumn-1019 div div")).click();
    }

    public boolean isRowsEquals(Row actual, Row expected) {
        if (actual.getName().equals(expected.getName())
                && (actual.getNodes().equals(expected.getNodes()))
                && (actual.getPriority().equals(expected.getPriority()))
                && (actual.getDue().equals(expected.getDue()))) {
            return true;
        } else {
            return false;
        }
    }
}
