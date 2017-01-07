package tests;

import model.Users.Worker;
import model.report.*;
import org.junit.Before;
import org.junit.Test;

import static model.report.OverallCondition.SAFE;
import static model.report.OverallCondition.TREATABLE;
import static model.report.OverallCondition.UNSAFE;
import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

/**
 * Created by davidarida on 11/14/16.
 */
public class GenerateHistoricalReportByVirusPPMTests {
    private Map<Integer, QualityReport> quality;


    @Before
    public void setUp() {
        try { // clear maps before each test
            Field field;
            field = ReportsList.class.getDeclaredField("qualityReportMap");
            field.setAccessible(true);
            ((HashMap<Integer, QualityReport>) field.get("")).clear();
            quality = (HashMap<Integer, QualityReport>) field.get("");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test(timeout = 1000)
    public void testMapEmpty() {
        double[] arr = new double[12];
        assertEquals(12, SortReports.generateHistoricalReportByVirusPPM(new Location(0,0), 50, 2016).length);
        assertEquals(Arrays.toString(arr), Arrays.toString(SortReports.generateHistoricalReportByVirusPPM(new Location(0,0), 50, 2016)));
    }


    @Test(timeout = 1000)
    public void testGeneralOnce() {
        ReportsList.makeReport(new Worker("David", "darida3@gatech.edu", 189), new Location(0, 0), SAFE, 8, 5);

        double[] arr = new double[12];
        arr[10] = 8;
        assertEquals(12, SortReports.generateHistoricalReportByVirusPPM(new Location(0,0), 50, 2016).length);
        assertEquals(Arrays.toString(arr), Arrays.toString(SortReports.generateHistoricalReportByVirusPPM(new Location(0,0), 50, 2016)));
    }

    @Test(timeout = 1000)
    public void testGeneralMultiple() {
        ReportsList.makeReport(new Worker("David", "darida3@gatech.edu", 189), new Location(0, 0), SAFE, 8, 5);
        ReportsList.makeReport(new Worker("Owen", "grizzlythresher@gmail.com", 190), new Location(0.1, 0), TREATABLE, 9, 9);
        ReportsList.makeReport(new Worker("Dacorvyn", "iceguy12@gmail.com", 191), new Location(0, 0.1), TREATABLE, 17, 20);
        ReportsList.makeReport(new Worker("Ozzy", "oarmas3@gatech.edu", 192), new Location(27, 53), UNSAFE, 1023, 9001);

        double[] arr = new double[12];
        arr[10] = (8.0 + 9.0 + 17.0)/3;
        assertEquals(12, SortReports.generateHistoricalReportByVirusPPM(new Location(0,0), 50, 2016).length);
        assertEquals(Arrays.toString(arr), Arrays.toString(SortReports.generateHistoricalReportByVirusPPM(new Location(0,0), 50, 2016)));
    }

    @Test(timeout = 1000)
    public void testNullData() {
        double[] arr = new double[12];
        assertEquals(12, SortReports.generateHistoricalReportByVirusPPM(null, 0, 0).length);
        assertEquals(Arrays.toString(arr), Arrays.toString(SortReports.generateHistoricalReportByVirusPPM(null, 0, 0)));
    }

    @Test(timeout = 1000)
    public void testLocationZeroRadius() {
        ReportsList.makeReport(new Worker("Owen", "grizzlythresher@gmail.com", 190), new Location(0.1, 0), TREATABLE, 9, 9);
        ReportsList.makeReport(new Worker("Dacorvyn", "iceguy12@gmail.com", 191), new Location(0, 0.1), TREATABLE, 17, 20);

        double[] arr = new double[12];
        assertEquals(12, SortReports.generateHistoricalReportByVirusPPM(new Location(0,0), 0, 2016).length);
        assertEquals(Arrays.toString(arr), Arrays.toString(SortReports.generateHistoricalReportByVirusPPM(new Location(0,0), 0, 2016)));
    }

    @Test(timeout = 1000)
    public void testLocationWrongYear() {
        ReportsList.makeReport(new Worker("Owen", "grizzlythresher@gmail.com", 190), new Location(0.1, 0), TREATABLE, 9, 9);
        ReportsList.makeReport(new Worker("Dacorvyn", "iceguy12@gmail.com", 191), new Location(0, 0.1), TREATABLE, 17, 20);

        double[] arr = new double[12];
        assertEquals(12, SortReports.generateHistoricalReportByVirusPPM(new Location(0,0), 50, 2015).length);
        assertEquals(Arrays.toString(arr), Arrays.toString(SortReports.generateHistoricalReportByVirusPPM(new Location(0,0), 50, 2015)));
    }
    
}
