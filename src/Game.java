
import java.util.*;



public class Game {
    Scanner sc = new Scanner(System.in);
    Player AGENT;
    Player HUMAN;
    boolean human_turn;
    Board b = new Board();
    int[] agentMove;

    void setPlayers() {
        System.out.println("Press 0 if you want to be X or 1 otherwise: ");
        if (sc.nextInt() == 0) {
            HUMAN = new Player('X');
            AGENT = new Player('O');
            human_turn = true;
            System.out.println("You start first!");
        } else {
            HUMAN = new Player('O');
            AGENT = new Player('X');
            human_turn = false;
            System.out.println("Computer will start first!");
        }
        b.resetBoard();
        b.printBoard();
    }

    int score(Board game, int depth) {
        if (game.isWinner(AGENT.name)) {
            return 10 - depth ;
        } else if (game.isWinner(HUMAN.name)) {
            return -10 + depth;
        } else {
            return 0;
        }
    }

    int getMaxIndex(ArrayList<Integer> a){
        int max_index = 0;
        for (int i = 0; i < a.size() ; i++) {
            if(a.get(i) > a.get(max_index)){
                max_index = i;
            }
        }
        return max_index;

    }
    int minimax(Board game, int depth, boolean maximizer) {
        if (game.endGame(false)) {
            return score(game, depth);
        }
        ArrayList<int[]> possible_moves = game.getPossibleMoves();
        ArrayList<Integer> scores = new ArrayList<>();
        int currentScore;
        int max =  Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int[] move: possible_moves){
            // for each move simulate
            if(maximizer){
                AGENT.makeMove(b, move[0], move[1]); // simulate agent move
                currentScore = minimax( b, depth + 1, false); // recurse for next board state
                max = Math.max(currentScore, max); // get maximum available score
                scores.add(currentScore);
                if (depth == 0) {
                    int best_index = getMaxIndex(scores); // index of best score
                    agentMove = possible_moves.get(best_index); // get move with max score
                }
            }else{
                HUMAN.makeMove(b, move[0], move[1]); // simulate human move
                currentScore = minimax(b, depth + 1, true);
                min = Math.min(currentScore, min);
            }
            b.b[move[0]][move[1]] = '-'; // reset board state
        }
        return maximizer?max:min; // return best score available

    }


    void play () {
        while (true) {  // infinite iterations
            setPlayers();

            while (!b.endGame(true)) { // while iteration is running
                int x, y;
                agentMove = new int[2];
                if (human_turn) {
                    System.out.println("Where do you want to put " + HUMAN.name + ":");
                    x = sc.nextInt();
                    y = sc.nextInt();
                    HUMAN.makeMove(b, x, y);
                } else {
                    System.out.println("Computers turn:");
                    int best = minimax(b, 0, true);
                    AGENT.makeMove(b, agentMove[0], agentMove[1]);
                }
                b.printBoard();
                human_turn = !human_turn; // switch turns
            }
        }
    }

    public static void main (String[]args){
        Game g = new Game();
        g.play();
    }
}

