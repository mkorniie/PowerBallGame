import java.util.ArrayList;

/**
 * Created by Maria on 31.07.18.
 */
public class GameSimulator {
    public static boolean verboseGS = false;
    public static boolean verboseGame = false;
    public static long nOfTickets = 1000;

    private static ArrayList<Ticket> generateRandomTickets(long nOfTickets){

        if (nOfTickets < 1)
            throw new IllegalArgumentException("All arguments must be greater than zero");

        ArrayList<Ticket> newTicketList = new ArrayList<Ticket>();
        if (verboseGS)
            System.out.println("Generating " + nOfTickets + " tickets:");
        for (int i = 0; i < nOfTickets; i++) {
            newTicketList.add(Ticket.newRandomTicket());
            if (verboseGS)
                System.out.println("New ticket generated! " + newTicketList.get(i));
        }
        return newTicketList;
    }

    public static Game generateGame(long newTickets) {
        nOfTickets = newTickets;
        if (newTickets <= 0)
            throw new IllegalArgumentException("Number of tickets must be more than zero: " + newTickets);

        Payouts gamePayouts = new Payouts();
        ArrayList<Ticket> tickets = generateRandomTickets(newTickets);
        GameStatistics s = new GameStatistics();

        Game newGame = new Game.Builder()
                .withPayouts(gamePayouts)
                .withTickets(tickets)
                .withStatistics(s)
                .build();
        newGame.verbose = verboseGame;
        return newGame;
    }

    public static void main(String[] args) {
        Game currentGame = generateGame(nOfTickets);

        currentGame.verbose = verboseGame;
        currentGame.play();
        currentGame.showStatictics();
        currentGame.showDetailedWinStatistics();
    }
}
