import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by Maria on 30.07.18.
 */
public class Game {

    public boolean verbose = true;
    public static final int WHITEBALLRANGE = 69;
    public static final int POWERBALLRANGE = 26;
    public static final int WHITEBALLNUMBER = 5;

    private ArrayList<Ticket> allTickets;
    private ArrayList<Winner> lotteryWinners;
    private int winningPowerBall;
    private TreeSet<Integer> winningWhiteNumbers;
    private Payouts lotteryPayouts;
    private GameStatistics statistics;

    private Game(){ }

    public static class Builder {
        private Game newGame;

        public Builder() {
            newGame = new Game();
        }

        public Builder withPayouts(Payouts lotteryPayouts){
            if (lotteryPayouts == null){
                throw new IllegalArgumentException("Payouts can not be null");
            }
            newGame.lotteryPayouts = lotteryPayouts;
            return this;
        }

        public Builder withTickets(ArrayList<Ticket> allTickets){
            if (allTickets == null){
                throw new IllegalArgumentException("Tickets can not be null");
            }
            newGame.allTickets = allTickets;
            return this;
        }

        public Builder withStatistics(GameStatistics s){
            if (s == null){
                throw new IllegalArgumentException("Statistics can not be null");
            }
            newGame.statistics = s;
            return this;
        }

        public Game build(){
            newGame.statistics.generateProbabilities(newGame.lotteryPayouts.getPrizePayouts());
            return newGame;
        }
    }

    public void play() {
        draw();
        if (verbose)
            System.out.println("Winning sequence: " + winningWhiteNumbers + " " + winningPowerBall);
        lotteryWinners = assignWins();
        announce();
    }

    public void draw() {
        winningWhiteNumbers = new TreeSet<Integer>();

        while (winningWhiteNumbers.size() != Game.WHITEBALLNUMBER)
            winningWhiteNumbers.add((int) ((Math.random() * (Game.WHITEBALLRANGE - 1)) + 1));

        winningPowerBall = (int) ((Math.random() * (Game.POWERBALLRANGE - 1)) + 1);
    }

    public static Integer findWinType(int nOfMatchingNumbers,  boolean powerBallMatch) {

        Integer key = (nOfMatchingNumbers * 10) + (powerBallMatch ? 1 : 0 );
        return key;
    }

    public Long calculateSum(PowerPlayOptions powerPlay, Integer key){
        Long sum = lotteryPayouts.getPrizePayouts().get(key);
        if (sum != null && sum != lotteryPayouts.getJackPot())
        {
            switch (powerPlay) {
                case x2:  sum *= 2;
                    break;
                case x4:  sum *= 4;
                    break;
                case x5:  sum *= 5;
                    break;
                case x10: sum *= 10;
                    break;
            }
        }
        return sum;
    }

    private ArrayList<Winner> assignWins(){
        ArrayList<Winner> winners = new ArrayList<Winner>();
        boolean powerBallMatch = false;
        int nOfMatchingNumbers;
        Long sum;
        Integer key;

        for (Ticket ticket: allTickets) {
            Integer currArr[] = ticket.getWhiteNumbers().toArray(new Integer[Game.WHITEBALLNUMBER]);
            Integer winArr[] = winningWhiteNumbers.toArray(new Integer[Game.WHITEBALLNUMBER]);
            nOfMatchingNumbers = 0;
            powerBallMatch = false;
            for (int i = 0; i < currArr.length; i++) {
                for (int z = 0; z < winArr.length; z++)
                    if (winArr[z] == currArr[i])
                        nOfMatchingNumbers++;
            }
            if (winningPowerBall == ticket.getPowerBall())
                powerBallMatch = true;
            key = findWinType(nOfMatchingNumbers, powerBallMatch);
            sum = calculateSum(ticket.getPowerPlay(), key);
            if (sum != null) {
                statistics.addKeyWinner(key);
                winners.add(new Winner(ticket, sum));
            }
        }
        return winners;
    }

    public void announce(){
        int nOfWinners = lotteryWinners.size();

        if (nOfWinners == 0)
            System.out.println("We have no winners! ;(");
        else {
            System.out.println("We have " + nOfWinners + " winners:");
            for (int i = 0; i < nOfWinners; i++) {
                System.out.println("\tID: " + lotteryWinners.get(i).getTicket().getId() + " wins " + lotteryWinners.get(i).getSum() + "$ (PowerPlay: " + lotteryWinners.get(i).getTicket().getPowerPlay() + ")");
                if (verbose) {
                    System.out.println("\t\tDRAW: " + winningWhiteNumbers + " " + winningPowerBall);
                    System.out.println("\t\tTHIS: " + lotteryWinners.get(i).getTicket().getWhiteNumbers() + " " + lotteryWinners.get(i).getTicket().getPowerBall());
                }
            }
        }
    }

    public void showStatictics(){
        statistics.totalWinStatistics(lotteryWinners, lotteryPayouts.getPrizePayouts());
    }

    public void showDetailedWinStatistics(){
        statistics.DetailedWinStatistics(lotteryPayouts);
    }

    public boolean compareToStatistics(boolean mode, double error){ return(statistics.compareToStatistics(lotteryPayouts, mode, error)); }

    public ArrayList<Ticket> getAllTickets() { return allTickets; }

    public ArrayList<Winner> getLotteryWinners() { return lotteryWinners; }

    public Payouts getLotteryPayouts() { return lotteryPayouts; }

    public TreeSet<Integer> getWinningWhiteNumbers() { return winningWhiteNumbers;}

    public int getWinningPowerBall() { return winningPowerBall; }
}
