package tictactoe;

import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        User a = new User("Honey");
        User b = new User("Teju");

        Game game = new Game(3, Arrays.asList(a,b));
        User winner = game.start();
        if(winner == null){
            System.out.println("********** Game Over! It's a Draw **********");
        }else {
            System.out.printf("********** Yay! %s is the winner **********\n", winner.getName());
        }
    }
}
