import java.util.ArrayList;

public class Board {
    char[][] b;

    Board(){
        this.b = new char[3][3];
        resetBoard();
    }

    boolean isWinner(char player){
        if(this.b[0][0] == this.b[0][1] && this.b[0][0] == this.b[0][2] && this.b[0][0] == player ||
                this.b[0][0] == this.b[1][0] && this.b[0][0] == this.b[2][0] && this.b[0][0] == player ||
                this.b[0][0] == this.b[1][1] && this.b[0][0] == this.b[2][2] && this.b[0][0] == player ||
                this.b[0][1] == this.b[1][1] && this.b[0][1] == this.b[2][1] && this.b[0][1] == player ||
                this.b[0][2] == this.b[1][2] && this.b[0][2] == this.b[2][2] && this.b[0][2] == player ||
                this.b[1][0] == this.b[1][1] && this.b[1][0] == this.b[1][2] && this.b[1][0] == player ||
                this.b[2][0] == this.b[2][1] && this.b[2][0] == this.b[2][2] && this.b[2][0] == player ||
                this.b[2][0] == this.b[1][1] && this.b[2][0] == this.b[0][2] && this.b[2][0] == player
                ){
            return true;
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
    protected Board clone(){
        Board c = new Board();
        for (int i = 0; i < b.length ; i++) {
            for (int j = 0; j < b[0].length; j++) {
                c.b[i][j] = b[i][j];
            }
        }
        return c;
    }

    Board newState(Player player, int i, int j){
        Board cloned = this.clone();
        player.makeMove(cloned, i, j);
        return cloned;
    }

    ArrayList<int[]> getPossibleMoves(){
        ArrayList<int[]> moves = new ArrayList<>();
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                if(b[i][j] == '-'){
                    int[] tmp = {i, j};
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
