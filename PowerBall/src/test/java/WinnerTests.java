
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Maria on 31.07.18.
 */
public class WinnerTests {

    @org.junit.Test
    public void constructorTicketIsNullTest()
    {
        try {
            Winner test = new Winner(null, 1);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Ticket can not be null"));
        }
    }

    @org.junit.Test
    public void constructorSumLessThanZero()
    {
        Ticket mockTicket = mock(Ticket.class);
        try {
            Winner test = new Winner(mockTicket, -22);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Sum must be more than zero: -22"));
        }
    }

    @org.junit.Test
    public void setTicketIsNullTest()
    {
        try {
            Ticket mockTicket = mock(Ticket.class);
            Winner test = new Winner(mockTicket, 1);
            test.setTicket(null);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Ticket can not be null"));
        }
    }

    @org.junit.Test
    public void setSumLessThanZero()
    {
        Ticket mockTicket = mock(Ticket.class);
        try {
            Winner test = new Winner(mockTicket, 10);
            test.setSum(-15);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Sum must be more than zero: -15"));
        }
    }

}
