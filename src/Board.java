import java.util.ArrayList;

public class Board {
    char[][] b;

    Board(){
        this.b = new char[3][3];
        resetBoard();
    }

    boolean isWinner(char player){
        if ((b[0][0] == b[1][1] && b[0][0] == b[2][2] && b[0][0] == player) || (b[0][2] == b[1][1] && b[0][2] == b[2][0] && b[0][2] == player)) { // diaognal checks
            return true;
        }
        for (int i = 0; i < 3; ++i) { // row or column win
            if ((b[i][0] == b[i][1] && b[i][0] == b[i][2] && b[i][0] == player)
                    || (b[0][i] == b[1][i] && b[0][i] == b[2][i] && b[0][i] == player)) {
                return true;
            }
        }

        return false;
    }
    boolean isValid(int i, int j){
        if (this.b[i][j] == '-'){
            return true;
        }
        return false;
    }

    void processMove(char player, int i, int j){
        if(isValid(i, j)){
            this.b[i][j] = player;
        }
    }

    ArrayList<int[]> getPossibleMoves(){
        ArrayList<int[]> moves = new ArrayList<>();
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                if(b[i][j] == '-'){
                    int[] tmp = {i, j};
                   // System.out.println(tmp[0] + " " +  tmp[1]);
                    moves.add(tmp);
                }
            }
        }

        return moves;
    }

    void resetBoard(){
        for (int i = 0; i < this.b.length ; i++) {
            for (int j = 0; j <  this.b[0].length; j++) {
                b[i][j] = '-';
            }
        }
    }


    boolean endGame(boolean printer){
        if(isWinner('X')){
            if(printer)
                System.out.println("Player X wins!");
            return true;
        }else if(isWinner('O')){
            if(printer)
                System.out.println("Player O wins!");
            return true;
        }
        for (int i = 0; i < b.length ; i++) {
            for (int j = 0; j < b[0].length; j++) {
                if(b[i][j] == '-'){
                    return false;
                }
            }
        }
        if(printer)
            System.out.println("DRAW!!!!");
        return true;
    }

    public void printBoard(){
        System.out.println("   0 1 2");
        for (int i = 0; i < b.length; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < b[0].length ; j++) {
                System.out.print(b[i][j] + "|");
            }
            System.out.println();
            System.out.println(" --------");
        }
    }



}
