package co.darma.smartmattress.service;

import co.darma.smartmattress.BaseTestCase;
import org.junit.Test;

/**
 * Created by frank on 16/1/5.
 */
public class PhysicalDataServiceTest extends BaseTestCase {

    @Test
    public void testGetPhsicalData() throws Exception {

        HealthDataService service = getContext().getBean(HealthDataService.class);
        String token = "";
        String v = null;
        String device = "0d8a3f6d7ac6";
        System.out.println(service.getHealthData(token, v, device));
    }
}