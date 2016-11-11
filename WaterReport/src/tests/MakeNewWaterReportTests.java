package tests;

import model.Users.User;
import model.report.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests ReportsList.makeReport
 * Created by grizz on 11/9/2016.
 */
public class MakeNewWaterReportTests {

    private Map<Integer, WaterReport> reportsReflect;
    private User user = new User("Owen Brahms", "O@B.com");

    @Before
    public void setUp() {
        reportsReflect = new HashMap<>();

        try {
            Field field;
            field = ReportsList.class.getDeclaredField("waterReportMap");
            field.setAccessible(true);
            ((HashMap<Integer, WaterReport>) field.get("")).clear();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setMaps() {
        try {
            Field field;
            field = ReportsList.class.getDeclaredField("waterReportMap");
            field.setAccessible(true);
            reportsReflect = (HashMap<Integer, WaterReport>) field.get("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkMaps(List<WaterReport> expected) {
        for (WaterReport r : expected) {
            int key = r.getReportID();
            assertEquals("ReportsList does not contain WaterReport " + key,
                    true, reportsReflect.containsKey(key));
            assertSame("ReportsList WaterReport mapping incorrect for user " + r.getReportID(),
                    r, reportsReflect.get(key));
        }
    }
    // This test fails since makeReport() does not do input checking.
    // All input checking is done in the controller, so this scenario will
    // never occur in the actual running of the application
    @Test(timeout = 200)
    public void testNullData() {
        assertNull(null, ReportsList.makeReport(null, null, null, null));

        setMaps();
        assertEquals(0, reportsReflect.size());
    }

    @Test(timeout = 200)
    public void testGeneralOnce() {
        WaterReport report = ReportsList.makeReport(user, new Location(23, 32),
                WaterType.BOTTLED, WaterCondition.POTABLE);
        assertNotNull(report);

        setMaps();
        assertEquals(1, reportsReflect.size());

        List<WaterReport> expected = new LinkedList<>();
        expected.add(report);
        checkMaps(expected);
    }

    @Test(timeout = 200)
    public void testGeneralMultiple() {
        List<WaterReport> expected = new LinkedList<>();
        expected.add(ReportsList.makeReport(user, new Location(23, 32),
                WaterType.BOTTLED, WaterCondition.POTABLE));
        expected.add(ReportsList.makeReport(user, new Location(33, 32),
                WaterType.BOTTLED, WaterCondition.POTABLE));
        expected.add(ReportsList.makeReport(user, new Location(23, 52),
                WaterType.BOTTLED, WaterCondition.POTABLE));
        expected.add(ReportsList.makeReport(user, new Location(27, 32),
                WaterType.BOTTLED, WaterCondition.POTABLE));
        for(int i = 0; i < expected.size(); i++) {
            assertNotNull("Expected non-null element at index " + i, expected.get(i));
        }

        setMaps();
        assertEquals(4, reportsReflect.size());
        checkMaps(expected);
    }

    @Test(timeout = 200)
    public void testGeneralHeavy() {
        List<WaterReport> expected = new LinkedList<>();
        for(int i = 0; i < 40; i++) {
            expected.add(ReportsList.makeReport(user, new Location(i + 5, i + 10),
                    WaterType.BOTTLED, WaterCondition.POTABLE));
        }
        for(int i = 0; i < expected.size(); i++) {
            assertNotNull("Expected non-null element at index " + i, expected.get(i));
        }
        setMaps();
        assertEquals(40, reportsReflect.size());
        checkMaps(expected);

    }
}
