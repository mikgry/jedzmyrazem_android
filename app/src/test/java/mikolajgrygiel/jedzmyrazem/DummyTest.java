package mikolajgrygiel.jedzmyrazem;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class DummyTest {

    @Before
    public void setUp() throws Exception {
        // setup
    }

    @Test
    public void testDummy() throws Exception {
        assertTrue(true);
    }
}
