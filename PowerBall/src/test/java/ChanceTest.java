import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by Maria on 02.08.18.
 */
public class ChanceTest {

    @org.junit.Test
    public void constructorProbabiltyLessThanZero()
    {
        try {
            Chance test = new Chance(-20);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Probability must be a positive number: -20"));
        }
    }

    @org.junit.Test
    public void setNumberOfWinnersLessThanZero()
    {
        try {
            Chance test = new Chance(1);
            test.setnOfWinners(-200);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Number of winners must be a positive number: -200"));
        }
    }
}
