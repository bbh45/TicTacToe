package tictactoe;

import java.util.*;

public class Game {
    //board
    //list of players
    private final UUID uuid;
    private final Board board;
    private final Deque<Player> players;
    private final Stack<Move> movesHistory;
    private static final Scanner SCANNER = new Scanner(System.in);

    public Game(int boardSize, List<User> users){
        this.uuid = UUID.randomUUID();
        this.board = new Board(boardSize);
        this.players = new ArrayDeque<>();
        this.movesHistory = new Stack<>();
        for(int i=0; i<users.size(); i++){
            players.add(new Player(users.get(i),  new PieceType(i+1) ));
        }
    }

    public User start(){
        int totalMoves = board.getSize() * board.getSize();
        while (totalMoves != 0){

            //Picking a player in the row
            Player turn = players.pop();
            System.out.printf("%s, it's your chance. place %d on the board\n", turn.getName(), turn.getPieceType().getValue());
            this.board.print();

            //Taking user input
            while (true){
                System.out.print("row : ");
                int x = SCANNER.nextInt();
                System.out.print("column : ");
                int y = SCANNER.nextInt();
                try{
                    if(board.playMoveAndCheckIfGame(new Move(x, y, turn))){
                        turn.getUser().incrementWins();
                        return turn.getUser();
                    }
                    movesHistory.push(new Move(x, y, turn));
                    break;
                }catch (IllegalArgumentException e){
                    System.out.println("Invalid move. "+ e.getMessage() + "\n Try again!");
                }
            }
            System.out.println("Do you want to undo your move? type 'yes' or 'no'.");
            if(SCANNER.next().equals("yes")){
                players.addFirst(turn); //Placing player back again at the front of queue
                //Find the last move and remove the piece from that cell and decrease frequency in map
                this.board.undoMove(movesHistory.get(movesHistory.size()-1));
                this.movesHistory.pop();
                continue;
            }
            players.add(turn);
            totalMoves = totalMoves - 1;
        }
        return null;
    }
}
