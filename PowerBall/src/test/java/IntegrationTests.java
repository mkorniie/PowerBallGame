import org.junit.Assert;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by Maria on 02.08.18.
 */
public class IntegrationTests {

    @org.junit.Test
    public void validWinners()
    {
        long nOfTickets = 1000;
        Game test = GameSimulator.generateGame(nOfTickets);

        int winningPB = test.getWinningPowerBall();
        TreeSet<Integer> winningWN = test.getWinningWhiteNumbers();
        ArrayList<Ticket> allT = test.getAllTickets();
        ArrayList<Winner> lotteryWinners = test.getLotteryWinners();
        Payouts pay = test.getLotteryPayouts();

        int nOfMatchingWN;
        boolean matchPB;
        long calculatedSum;
        if (lotteryWinners != null){
            for ( Winner w: lotteryWinners) {
                matchPB = (w.getTicket().getPowerBall() == winningPB);
                nOfMatchingWN = 0;
                for (Integer wn : w.getTicket().getWhiteNumbers()){
                    for (Integer winning : winningWN){
                        if (wn == winning)
                            nOfMatchingWN++;
                    }
                }
                int key = test.findWinType(nOfMatchingWN, matchPB);
                calculatedSum = test.calculateSum(w.getTicket().getPowerPlay(), key);
                Assert.assertEquals(calculatedSum, w.getSum());
            }
        }
    }

    @org.junit.Test
    public void matchStatistics()
    {
        boolean verbose = true;
        long nOfTickets = 10000;
        double error = 10;

        for (int i = 0; i < 100; i++){
            Game test = GameSimulator.generateGame(nOfTickets);
            test.play();
            Assert.assertTrue(test.compareToStatistics(verbose, error));
        }
    }
}
