package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.time.temporal.ChronoUnit.SECONDS;

public class BlackjackClient {
    private static List<Card> cardCoupier = new ArrayList<>();
    private static List<Card> cards = new ArrayList<>();
    private static boolean over21= false;
    private static boolean endgame = false;
    private static String host = "localhost";
    private static int port = 10000;
    private static Blackjack remoteBlackjack;
    private static User user;
    private static BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
    private static String nickname ="";

    private static String checkCorrectResponse() throws IOException, MissingException, ConflictException {
        String checkInput = reader.readLine();
        remoteBlackjack.isAlive(nickname);
        boolean wrongResponse = true;
        while (wrongResponse){
            if(!checkInput.equals("y") && !checkInput.equals("n")){
                System.out.println("ERROR response! Do you want to play again? (Press y for yes or n for no)");
                checkInput = reader.readLine();
                remoteBlackjack.isAlive(nickname);
            }else{
                wrongResponse = false;
            }
        }
        return checkInput;

    }

    private static String printOutput() throws MissingException, ConflictException {
        String output;
        String dealerCards ="";
        for (Card c : cardCoupier){
            dealerCards = dealerCards+c.getValue().name()+" "+c.getSuit().name() + " - ";
        }
        dealerCards = dealerCards.substring(0, dealerCards.length() - 2);

        String userCards ="";
        for (Card c : cards){
            userCards = userCards+c.getValue().name()+" "+c.getSuit().name() + " - ";
        }
        userCards = userCards.substring(0, userCards.length() - 2);

        output =
                "The dealer's cards: \n \t" +
                        dealerCards+"\n" +
                "Total cards value : " +remoteBlackjack.getValueCroupierCard(nickname)+
                "\n\n" +
                "Your cards:\n \t" +
                        userCards +"\n" +
                "Total cards value : "+ remoteBlackjack.getValueUserCard(nickname)+"\n";
        return output;
    }
    public static void main(String[] args) throws ConflictException, IOException, MissingException, InterruptedException {
        class CheckUserStateIsAlive extends TimerTask {
            @Override
            public void run() {
                try {
                    if(!remoteBlackjack.isUserInLobby(nickname)){
                        if(!endgame)
                            System.out.println("\nYou waited too long! You have been eliminated from the game!");
                        System.exit(0);
                    }
                } catch (MissingException e) {
                    throw new RuntimeException(e);
                } catch (ConflictException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        remoteBlackjack = new RemoteBlackjack(host,port);

        clearConsole();
        System.out.println( "░░░┌──┐┌┐░░░░░░┌┐░░░░░░░░░┌┐░░░░\n" +
                            "░░░│┌┐│││░░░░░░││░░┌┐░░░░░││░░░░\n" +
                            "░░░│└┘└┤│┌──┬──┤│┌┐└┼──┬──┤│┌┐░░\n" +
                            "░░░│┌─┐│││┌┐│┌─┤└┘┘┌┤┌┐│┌─┤└┘┘░░\n" +
                            "░░░│└─┘│└┤┌┐│└─┤┌┐┐││┌┐│└─┤┌┐┐░░\n" +
                            "░░░└───┴─┴┘└┴──┴┘└┘│├┘└┴──┴┘└┘░░\n" +
                            "░░░░░░░░░░░░░░░░░░┌┘│░░░░░░░░░░░\n" +
                            "░░░░░░░░░░░░░░░░░░└─┘░░░░░░░░░░░\n");
        System.out.print("\n");

        System.out.print("--Blackjack pays 3:2 and consists of an Ace and a 10-value card--\n");
        System.out.println("\n--WARNING: If you are inactive for more than 30 seconds you will be eliminated from the game!--");

        System.out.println("\n\nChoose a nickname:");

        boolean logged = false;
        while(!logged){
            try {
                nickname = reader.readLine();
                //remoteBlackjack.isAlive(nickname);
                user = new User(remoteBlackjack.register(nickname));
                logged = true;
            }catch (ConflictException e){
                System.out.println("Nickname already exist! Choose another nickname: ");
            }
        }

        System.out.println("\nNickname registered!");
        System.out.println("Your initial balance is $"+remoteBlackjack.getBalance(nickname));
        System.out.println("Start Game!");

        Timer timer = new Timer();
        timer.schedule(new CheckUserStateIsAlive(), 5000, 5000);
        while (!endgame && remoteBlackjack.getBalance(nickname) > 0){
            remoteBlackjack.isAlive(nickname);

            resetVariables();

            System.out.println("\nHow much money do you want to bet? You have got $"+remoteBlackjack.getBalance(nickname));

            boolean correctBet = false;
            while(!correctBet){
                try {
                    remoteBlackjack.addBet(new Token(user, reader.readLine()));
                    correctBet = true;
                    remoteBlackjack.isAlive(nickname);
                }catch (ConflictException e){
                    System.out.println("ERROR input value! Choose a numeric value! Bet must less or equal your balance!\nHow much money do you want to bet? You have got $"+remoteBlackjack.getBalance(nickname));
                }
            }


            System.out.println("Your cards are: ");
            cards.add(remoteBlackjack.hitUserCard(user));
            System.out.println("- "+cards.get(0).getValue()+" "+cards.get(0).getSuit());

            cards.add(remoteBlackjack.hitUserCard(user));
            System.out.println("- "+cards.get(1).getValue()+" "+cards.get(1).getSuit());

            System.out.println("Sum of cars value is: "+remoteBlackjack.getValueUserCard(nickname));
            //remoteBlackjack.userReady(user);

            Thread.sleep(2000);
            clearConsole();
            System.out.println("\nWait for the other players to be ready...\n");
            Thread.sleep(500);

            cardCoupier.add(remoteBlackjack.checkCroupierCard(user.getNickname()));

            while (cardCoupier.get(0).getSuit() == Suit.EMPTY && cardCoupier.get(0).getValue() == CardValue.EMPTY){
                remoteBlackjack.userReady(user);
                Thread.sleep(1000);
                cardCoupier.remove(0);
                cardCoupier.add(remoteBlackjack.checkCroupierCard(user.getNickname()));

                remoteBlackjack.isAlive(nickname);
            }

            clearConsole();

            System.out.println("First croupier card is: ");
            System.out.println("- "+cardCoupier.get(0).getValue()+" "+cardCoupier.get(0).getSuit());

            Thread.sleep(3000);
            clearConsole();

            if(!remoteBlackjack.hasBlackjackUser(nickname)) {

                remoteBlackjack.isAlive(nickname);
                System.out.println("Do you want another card? (Press h for hit a new card or s for stand)\n\n" + printOutput());
                String answer = reader.readLine();
                remoteBlackjack.isAlive(nickname);

                while (!answer.equals("s")) {
                    remoteBlackjack.isAlive(nickname);
                    while (!answer.equals("h") && !answer.equals("s")) {
                        System.out.println("ERROR INPUT. Use only the characters h and s to answer.");
                        System.out.println("Do you want another card?");
                        answer = reader.readLine();

                        remoteBlackjack.isAlive(nickname);
                    }
                    if (answer.equals("h")) {

                        printThreeDot();
                        cards.add(remoteBlackjack.hitUserCard(user));
                        System.out.println("Your new card is: ");
                        System.out.println("- " + cards.get(cards.size() - 1).getValue() + " " + cards.get(cards.size() - 1).getSuit());

                        Thread.sleep(2000);
                        clearConsole();
                        //System.out.println(printOutput());
                        if (remoteBlackjack.isOver21(user)) {
                            over21 = true;
                            answer = "s";

                        } else {
                            clearConsole();
                            System.out.println("Do you want another card? (Press h for hit a new card or s for stand)\n\n" + printOutput());
                            answer = reader.readLine();
                            remoteBlackjack.isAlive(nickname);
                        }
                    }

                }
                remoteBlackjack.isAlive(nickname);
            }else{
                System.out.println("You have got the Blackjack!");
            }

            if(over21){
                System.out.println("Your cards values are more 21!");
                over21 = false;

                System.out.println("\nWait for the other players to be ready...\n");
                remoteBlackjack.setTrueUserReadyStatus(nickname);
                while (!remoteBlackjack.allUsersReady(user.getNickname())) {
                    Thread.sleep(1000);

                    remoteBlackjack.isAlive(nickname);
                }
                remoteBlackjack.setFalseUserReadyStatus(nickname);
            }else {
                System.out.println("\nWait for the other players to be ready...\n");
                remoteBlackjack.setTrueUserReadyStatus(nickname);
                while (!remoteBlackjack.allUsersReady(user.getNickname())) {
                    Thread.sleep(1000);

                    remoteBlackjack.isAlive(nickname);
                }
                remoteBlackjack.setFalseUserReadyStatus(nickname);

                cardCoupier = new ArrayList<>();
                cardCoupier.add(remoteBlackjack.getCroupierCard(user.getNickname()));
                while (cardCoupier.get(cardCoupier.size() - 1).getSuit() != Suit.EMPTY && cardCoupier.get(cardCoupier.size() - 1).getValue() != CardValue.EMPTY) {
                    cardCoupier.add(remoteBlackjack.getCroupierCard(user.getNickname()));

                }
                cardCoupier.remove(cardCoupier.size() - 1);

            }
            remoteBlackjack.isAlive(nickname);

            System.out.println(printOutput());
            HandResult handResult = remoteBlackjack.resultUserHand(user);
            

            System.out.println(handResult);

            if (remoteBlackjack.getBalance(nickname) == 0) {
                System.out.println("You have no more money left to place another bet!");
                System.out.println("GAME OVER");
                remoteBlackjack.removeUser(user.getNickname());
                endgame = true;
            }else{
                System.out.println("Do you want to play again? (Press y for yes or n for no)");
                String response = checkCorrectResponse();
                remoteBlackjack.isAlive(nickname);
                if (response.equals("n")){
                    endgame = true;
                    remoteBlackjack.resetPlay(nickname);
                    remoteBlackjack.removeUser(user.getNickname());
                }
                else {

                    System.out.println("\nWait for the other players to be ready...\n");
                    //remoteBlackjack.resetPlay(nickname);
                    remoteBlackjack.setTrueUserReadyStatus(nickname);
                    while (!remoteBlackjack.allUsersReady(user.getNickname())) {
                        Thread.sleep(100);
                        remoteBlackjack.isAlive(nickname);
                    }
                    Thread.sleep(1000);
                    remoteBlackjack.resetPlay(nickname);
                    clearConsole();

                }
            }
        }
    }

    private static void resetVariables(){
        cardCoupier = new ArrayList<>();
        cards = new ArrayList<>();
        over21 = false;

    }

    private static void printThreeDot() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(".");
        Thread.sleep(1000);
        System.out.println(".");
        Thread.sleep(1000);
        System.out.println(".");
    }
    private static void clearConsole(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}