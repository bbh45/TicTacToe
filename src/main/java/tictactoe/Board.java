package tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {

    private final int size;
    private final PieceType[][] board;
    private final List<HashMap<Integer, Integer>> rowFreqMap, columnFreqMap;
    private final HashMap<Integer, Integer> mainDiagonalFreqMap, offDiagonalFreqMap;

    public Board(int size){
        this.size = size;
        board = new PieceType[size][size];
        rowFreqMap = new ArrayList<>();
        columnFreqMap = new ArrayList<>();
        for(int i=0; i<size; i++){
            rowFreqMap.add(new HashMap<>());
            columnFreqMap.add(new HashMap<>());
        }
        mainDiagonalFreqMap = new HashMap<>();
        offDiagonalFreqMap = new HashMap<>();
    }

    public int getSize(){
        return size;
    }

    public boolean playMoveAndCheckIfGame(Move move) throws IllegalArgumentException{
        int x = move.getX();
        int y = move.getY();
        Player player = move.getPlayer();
        if(x < 0 || x >= size){
            throw new IllegalArgumentException("Invalid row provided");
        }else if(y < 0 || y >= size){
            throw new IllegalArgumentException("Invalid column provided");
        }else if(this.board[x][y] != null){
            throw new IllegalArgumentException("Cell is already occupied");
        }
        int pieceValue = player.getPieceType().getValue();
        this.board[x][y] = player.getPieceType();
        rowFreqMap.get(x).put(pieceValue, rowFreqMap.get(x).getOrDefault(pieceValue, 0)+1);
        columnFreqMap.get(y).put(pieceValue, columnFreqMap.get(y).getOrDefault(pieceValue, 0)+1);
        //If position is part of main diagonal
        if(x == y){
            mainDiagonalFreqMap.put(pieceValue, mainDiagonalFreqMap.getOrDefault(pieceValue, 0)+1);
        }
        //If position is part of off diagonal
        if(x == size - y - 1){
            offDiagonalFreqMap.put(pieceValue, offDiagonalFreqMap.getOrDefault(pieceValue, 0)+1);
        }

        if(rowFreqMap.get(x).getOrDefault(pieceValue, 0) == size){
            return true;
        }else if(columnFreqMap.get(y).getOrDefault(pieceValue, 0) == size){
            return true;
        }else if(x == y && mainDiagonalFreqMap.getOrDefault(pieceValue, 0) == size){
            return true;
        }else if(x == size - y - 1 && offDiagonalFreqMap.getOrDefault(pieceValue, 0) == size){
            return true;
        }
        return false;
    }

    public void undoMove(Move move){
        int pieceValue = move.getPlayer().getPieceType().getValue();
        int x = move.getX();
        int y = move.getY();
        this.board[x][y] = null;
        rowFreqMap.get(x).put(pieceValue, rowFreqMap.get(x).getOrDefault(pieceValue, 0)-1);
        columnFreqMap.get(y).put(pieceValue, columnFreqMap.get(y).getOrDefault(pieceValue, 0)-1);
        if(x == y){
            mainDiagonalFreqMap.put(pieceValue, mainDiagonalFreqMap.getOrDefault(pieceValue, 0)-1);
        }if(x == size-y-1){
            offDiagonalFreqMap.put(pieceValue, offDiagonalFreqMap.getOrDefault(pieceValue, 0)-1);
        }
    }
    public void print(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(board[i][j] == null){
                    System.out.print("0 ");
                }else{
                    System.out.print(board[i][j].getValue()+" ");
                }
            }
            System.out.println();
        }
    }

}
