import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class AppTest extends CoreTest{

    public void testAdd() throws InterruptedException {
        Row row = new Row("test20", "testNode1", "1", "04/12/2018", "2018-05-30", "2018-06-20");
        add();
        fillRow(row);
        apply();
        Thread.sleep(500);
        WebElement element = findElementByRow(row);
        Row elementObj = rowElementToObject(element);
        assertTrue(isRowsEquals(elementObj, row));
        delete(element);
    }

    public void testEdit() throws InterruptedException {
        Row row = new Row("test1", "testNode1", "1", "04/12/2018", "2018-05-30", "2018-06-20");
        add();
        fillRow(row);
        apply();
        Thread.sleep(500);
        WebElement element = findElementByRow(row);

        edit(element, "test2");
        Thread.sleep(500);
        apply();
        Thread.sleep(500);
        assertNull(findElementByRow(row));
        row.setName("test2");
        WebElement elementEdited = findElementByRow(row);
        Row rowEdited = rowElementToObject(elementEdited);

        assertTrue(isRowsEquals(rowEdited, row));
        delete(elementEdited);
    }

    public void testAddInForm() throws InterruptedException {
        Row row =new Row("test3", "testNode1", "1", "02/12/2018", null, null);
        addInForm(row);
        Thread.sleep(500);
        WebElement element = findElementByRow(row);
        Row elementObj = rowElementToObject(element);
        assertTrue(isRowsEquals(elementObj, row));
        delete(element);
    }

    public void testEditInForm() throws InterruptedException, ParseException {
        Row row =new Row("test4", "testNode1", "1", "02/12/2018", null, null);
        addInForm(row);
        Thread.sleep(500);
        WebElement element = findElementByRow(row);

        Row editedRow = new Row("test5", "testNode2", "2", "01/12/2018", null, null);
        editInForm(element, editedRow);
        Thread.sleep(500);
        assertNull(findElementByRow(row));
        WebElement elementEdited = findElementByRow(editedRow);
        Row rowEdited = rowElementToObject(elementEdited);
        Thread.sleep(500);

        assertTrue(isRowsEquals(rowEdited, editedRow));
        delete(elementEdited);
    }

    public void testSearch() throws InterruptedException {
        Row row1 =new Row("test1", "testNode1", "1", "02/12/2018", null, null);
        Row row2 =new Row("test3", "testNode1", "1", "02/12/2018", null, null);

        addInForm(row1);
        Thread.sleep(500);
        addInForm(row2);
        Thread.sleep(500);

        FilterParams param1 = new FilterParams("Notes", "Contains", "1");
        FilterParams param2 = new FilterParams("Name", "Matches", "test1");
        List<FilterParams> params = new ArrayList<FilterParams>();
        params.add(param1);
        params.add(param2);
        search(params);
        Thread.sleep(500);

        List<WebElement> rows = getRowElements();
        assertEquals(1, rows.size());

        Row foundRow = rowElementToObject(rows.get(0));
        assertTrue(isRowsEquals(foundRow, row1));

        driver.navigate().refresh();
        delete(findElementByRow(row1));
        Thread.sleep(500);
        delete(findElementByRow(row2));
        Thread.sleep(500);
    }

    public void testCheck() throws InterruptedException, ParseException {
        Row row =new Row("test1", "testNode1", "1", null, null, null);
        add();
        fillRow(row);
        apply();
        Thread.sleep(500);
        WebElement element = findElementByRow(row);
        assertFalse(isChecked(element));

        changeChecker(element);
        apply();
        Thread.sleep(500);
        element = findElementByRow(row);
        Thread.sleep(200);
        assertTrue(isChecked(element));

        delete(element);
    }

    public void testDelete() throws InterruptedException {
        Row row =new Row("test100", "testNode1", "1", "02/12/2018", null, null);
        addInForm(row);
        Thread.sleep(500);
        WebElement element = findElementByRow(row);

        delete(element);
    }
}
