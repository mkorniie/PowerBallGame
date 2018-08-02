import java.util.HashSet;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by Maria on 31.07.18.
 */
public class TicketTests {

    @org.junit.Test
    public void constructorTreeSetIsNull()
    {
        try {
            Ticket test = new Ticket(null, 1, PowerPlayOptions.NONE);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Invalid white numbers set:" + null));
        }
    }

    @org.junit.Test
    public void constructorSumLessThanZero()
    {
        TreeSet<Integer> whiteNumbers = new TreeSet<Integer>();
        whiteNumbers.add(1);
        whiteNumbers.add(2);
        whiteNumbers.add(3);
        whiteNumbers.add(4);
        whiteNumbers.add(5);
        try {
            Ticket test = new Ticket(whiteNumbers, -200, PowerPlayOptions.NONE);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Invalid powerball: -200"));
        }

        int wrongPower = Game.POWERBALLRANGE + 2;
        try {
            Ticket test = new Ticket(whiteNumbers,wrongPower, PowerPlayOptions.NONE);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Invalid powerball: " + wrongPower));
        }
    }

    @org.junit.Test
    public void uniqueIDs()
    {
        int testSize = 100;
        HashSet<Long> id = new HashSet<Long>();

        for (int i = 0; i < testSize; i++)
        {
            Ticket test = Ticket.newRandomTicket();
            id.add(test.getId());
        }
        if (id.size() != testSize)
            fail("IDs are not unique!");
    }

    @org.junit.Test
    public void newRandomTicketCorrectRangeAndSize()
    {
        int testSize = 100;
        int numberOfWhiteBalls;

        for (int i = 0; i < testSize; i++)
        {
            numberOfWhiteBalls = 0;
            Ticket test = Ticket.newRandomTicket();
            for (Integer num: test.getWhiteNumbers()) {
                numberOfWhiteBalls++;
                if (num <= 0 || num > Game.WHITEBALLRANGE)
                    fail("White number generated is out of range!");
            }
            if (test.getPowerBall() <= 0 || test.getPowerBall() > Game.POWERBALLRANGE)
                fail("PowerBall generated is out of range!");
            if (numberOfWhiteBalls != Game.WHITEBALLNUMBER)
                fail("Wrong white balls number generated: " + numberOfWhiteBalls);
        }
    }
}
