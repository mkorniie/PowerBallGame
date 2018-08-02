
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Maria on 02.08.18.
 */
public class GameTests {

    @org.junit.Test
    public void validConstantsTest()
    {
        if (Game.WHITEBALLRANGE <= 0)
            fail("White ball range must be more than zero");
        if (Game.POWERBALLRANGE <= 0)
            fail("Powerball range must be more than zero");
        if (Game.WHITEBALLNUMBER <= 0)
            fail("Number of white balls must be more than zero");
    }

    @org.junit.Test
    public void gameBuilderValidate()
    {
        ArrayList<Ticket> mockTicket = mock(ArrayList.class);
        GameStatistics mockStat = mock(GameStatistics.class);
        Payouts mockPayouts = mock(Payouts.class);

        try {
            Game test = new Game.Builder()
                    .withPayouts(null)
                    .withStatistics(mockStat)
                    .withTickets(mockTicket)
                    .build();
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Payouts can not be null"));
        }

        try {
            Game test = new Game.Builder()
                    .withPayouts(mockPayouts)
                    .withStatistics(null)
                    .withTickets(mockTicket)
                    .build();
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Statistics can not be null"));
        }

        try {
            Game test = new Game.Builder()
                    .withPayouts(mockPayouts)
                    .withStatistics(mockStat)
                    .withTickets(null)
                    .build();
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Tickets can not be null"));
        }
    }

    @org.junit.Test
    public void drawTest(){
        ArrayList<Ticket> mockTicket = mock(ArrayList.class);
        GameStatistics mockStat = mock(GameStatistics.class);
        Payouts mockPayouts = mock(Payouts.class);

        Game test = new Game.Builder()
                .withTickets(mockTicket)
                .withStatistics(mockStat)
                .withPayouts(mockPayouts)
                .build();

        assertEquals(null, test.getWinningWhiteNumbers());
        assertEquals(0, test.getWinningPowerBall());

        test.draw();
        assertNotEquals(null, test.getWinningWhiteNumbers());
        assertNotEquals(0, test.getWinningPowerBall());
        assertEquals(true, TicketValidator.validateWhiteNumbers(test.getWinningWhiteNumbers()));
        assertEquals(true, TicketValidator.validatePowerBall(test.getWinningPowerBall()));
    }

    @org.junit.Test
    public void findWinTypeTest(){
        assertEquals((Integer)11, Game.findWinType(1, true));
        assertEquals((Integer)51, Game.findWinType(5, true));
        assertEquals((Integer)50, Game.findWinType(5, false));
    }

}
