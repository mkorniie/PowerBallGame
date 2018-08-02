import java.util.*;

/**
 * Created by Maria on 01.08.18.
 */
public class GameStatistics {

    private LinkedHashMap<Integer, Chance> winStats;

    public static long countPart(int n, int k)
    {
        int result = 1;

        for (int i = 0; i < k; i++)
        {
            result *= n;
            n--;
        }
        return (result);
    }

    public static long countFactorial(int number){
        long result = 1;

        if (number < 0)
            return 1;
        if (number == 0 || number == 1)
            return 1;
        for (int i = 2; i <= number; i++)
            result *= i;
        return result;
    }
    public static long countCombination(int k, int n) {
        return (countPart(n, k) / countFactorial(k));
    }

    public long calculateFrom(int howMany, int fromRange){
        if (howMany == 0)
            return -1;
        long res;
        if (howMany == Game.WHITEBALLNUMBER)
            res = countPart(fromRange, howMany) / countFactorial(howMany);
        else
            res = (countPart(fromRange, Game.WHITEBALLNUMBER) / countFactorial(Game.WHITEBALLNUMBER))
                    / (countCombination(howMany, Game.WHITEBALLNUMBER)
                    * countCombination(Game.WHITEBALLNUMBER - howMany, Game.WHITEBALLRANGE - Game.WHITEBALLNUMBER));
        return res;
    }

    public double winProbability(LinkedHashMap<Integer, Long> prizePayouts, long nOfTickets){
        double sum = 0;
        Set<Integer> keysSet = winStats.keySet();
        Integer keys[] = keysSet.toArray(new Integer[keysSet.size()]);

        for (Integer k: keys) {
            sum += nOfTickets / winStats.get(k).getProbability();
        }
        return sum;
    }

    public void addKeyWinner(Integer key) {
        Chance toUpdate = winStats.get(key);
        if (toUpdate == null)
            throw new IllegalArgumentException("Illegal key :" + key);
        long n = toUpdate.getnOfWinners();
        toUpdate.setnOfWinners(n + 1);
    }

    public void generateProbabilities(HashMap<Integer, Long> prizePayouts){
          if (prizePayouts == null)
            throw new IllegalArgumentException("prizePayouts can not be null");
        if (winStats == null) {
            winStats = new LinkedHashMap<Integer, Chance>();
            boolean hasPowerBall;
            boolean hasWhiteBall;
            long whiteProbability = 0;
            long powerBallProbability = Game.POWERBALLRANGE;
            long totalKeyProbability;
            Integer keys[] = prizePayouts.keySet().toArray(new Integer[prizePayouts.size()]);
            double noWhiteMatchProbability;

            for (int i = 0; i < prizePayouts.size(); i++) {
                hasPowerBall = (keys[i] % 2 == 1);
                hasWhiteBall = (keys[i] > 9);
                if (hasWhiteBall)
                    whiteProbability = calculateFrom(keys[i] / 10, Game.WHITEBALLRANGE);
                if (hasWhiteBall && hasPowerBall)
                    totalKeyProbability = whiteProbability * powerBallProbability;
                else if (hasPowerBall) {
                    noWhiteMatchProbability = 1.346;
                    totalKeyProbability = (long) ((double) powerBallProbability * noWhiteMatchProbability);
                } else {
                    totalKeyProbability = (whiteProbability * Game.POWERBALLRANGE) / (Game.POWERBALLRANGE - 1);
                }

                Chance ch = new Chance(totalKeyProbability);
                winStats.put(keys[i], ch);
            }
        }
    }

    public void DetailedWinStatistics(Payouts p){

        System.out.println("------------------------");
        Set<Integer> keysSet = winStats.keySet();
        Integer keys[] = keysSet.toArray(new Integer[keysSet.size()]);

        String s = "";
        long expected;
        for (Integer k: keys) {
            if (p.getPrizePayouts().get(k) == p.getJackPot())
                s = "Jackpot";
            else
                s = (k / 10) + " out of " + Game.WHITEBALLRANGE + " and " + (k % 10 != 0 ? "with   " : "without") + " Powerball";
            expected = GameSimulator.nOfTickets / winStats.get(k).getProbability();
            System.out.println("Total number of " + s + " winners: " + winStats.get(k).getnOfWinners() + "/" + GameSimulator.nOfTickets +"\t| Expected: " + expected);
        }
        System.out.println("------------------------");
    }


    public void totalWinStatistics(ArrayList<Winner> lotteryWinners, LinkedHashMap<Integer, Long> prizePayouts){
        long sum = 0;
        long nOfWinners;
        double nOfWinnersExpected = winProbability(prizePayouts, GameSimulator.nOfTickets);
        double average;

        if (lotteryWinners == null || (lotteryWinners.size() == 0)) {
            nOfWinners = 0;
            average = 0;
        }
        else{
            for (Winner w: lotteryWinners) {
                sum += w.getSum();
            }
            nOfWinners = lotteryWinners.size();
            average = sum/lotteryWinners.size();
        }

        System.out.println("------------------------");
        System.out.println("Total number of winners: " + nOfWinners + "/" + GameSimulator.nOfTickets + " | Expected: " + nOfWinnersExpected);
        System.out.println("Total sum won: " + sum + "$");
        System.out.println("Average sum won: " + average);
        System.out.println("------------------------");
    }

    public boolean withinErrorLimit(long actual, long expected, double error)
    {
        long upperLimit = (long)((double)expected * (error + 1));
        long lowerLimit = (long)((double)expected * (1- error));

        if (expected == 0) {
            upperLimit  = expected + (long)(error + 1);
            lowerLimit  = expected - (long)(error + 1);
        }
        if (actual >= lowerLimit && actual <= upperLimit)
            return true;
        else
            return false;
    }

    public boolean compareToStatistics(Payouts p, boolean verbose, double error) {
        if (verbose)
            System.out.println("------------------------");
        Set<Integer> keysSet = winStats.keySet();
        Integer keys[] = keysSet.toArray(new Integer[keysSet.size()]);
        boolean withinLimit = true;
        boolean result = true;

        String s = "";
        String ver = "";
        long expected;
        for (Integer k: keys) {
            if (p.getPrizePayouts().get(k) == p.getJackPot())
                s = "Jackpot";
            else
                s = (k / 10) + " out of " + Game.WHITEBALLRANGE + " and " + (k % 10 != 0 ? "with   " : "without") + " Powerball";
            expected = GameSimulator.nOfTickets / winStats.get(k).getProbability();
            withinLimit = withinErrorLimit(winStats.get(k).getnOfWinners(), expected, error);
            ver = (withinLimit? "Within limit" : "Not within limit");
            result = (result == false ? false : withinLimit);
            if (verbose)
                System.out.println("Compare: " + s + " winners:\n"
                    + "\t\tActual: " + winStats.get(k).getnOfWinners()
                    + "\t\tExpected: " + expected
                    + "\t\t_______________________"
                    + "\t\t" + ver + " with error " + error);
        }
        if (verbose)
            System.out.println("------------------------");
        return result;
    }
}
