import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Maria on 31.07.18.
 */
public class PayoutsTests {

    @org.junit.Test
    public void constructorHashMapIsNullTest()
    {
        try {
            Payouts test = new Payouts(null, 1);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("prizePayouts can not be null"));
        }
    }

    @org.junit.Test
    public void constructorJackpotLessThanZero()
    {
        LinkedHashMap<Integer, Long> mockPayout = mock(LinkedHashMap.class);
        try {
            Payouts test = new Payouts(mockPayout, -100);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("jackPot must be more than zero: -100"));
        }
    }
}
