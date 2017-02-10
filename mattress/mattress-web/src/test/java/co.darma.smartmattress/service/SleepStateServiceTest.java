package co.darma.smartmattress.service;

import co.darma.smartmattress.BaseTestCase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by frank on 16/1/4.
 */
public class SleepStateServiceTest extends BaseTestCase {

    @Test
    public void testGetSleepState() throws Exception {

        SleepStateService service = getContext().getBean(SleepStateService.class);

        String token = "39e6d2b12bdf47eb9ad70a55b0c2004b";

        String v = null;

        String lastUpdateTime = "11111";

        System.out.println(service.getSleepState(token, v, "3fa19303a5df", "212121").getEntity());
    }
}