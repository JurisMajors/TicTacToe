import com.sun.tools.attach.AgentInitializationException;

import java.util.*;



public class Game {
    Scanner sc = new Scanner(System.in);
    Player AGENT;
    Player HUMAN;
    boolean human_turn;
    Board b = new Board();
    int[] agentMove;
    ArrayList<Integer> scores;
    ArrayList<int[]> moves;
    HashMap<Integer, int[]> book;

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
            return 10 ;
        } else if (game.isWinner(HUMAN.name)) {
            return -10 ;
        } else {
            return 0;
        }
    }

    int getMinIndex(ArrayList<Integer> a){
        int min = a.get(0);
        for (int i = 0; i < a.size() ; i++) {
            if(a.get(i) < min){
                min = a.get(i);
            }
        }

        return min;

    }

    int getMaxIndex(ArrayList<Integer> a){
        int max = a.get(0);
        for (int i = 0; i < a.size() ; i++) {
            if(a.get(i) > max){
                max = a.get(i);
            }
        }
        return max;

    }


    int minimax(Board game, int depth, boolean maximizer) {
        if (game.endGame(false)) {
            return score(game, depth);
        }
        ArrayList<int[]> possible_moves = game.getPossibleMoves();
        scores = new ArrayList<>();
        moves = new ArrayList<>();
        int currentScore;
        int bestScore = maximizer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for(int[] move: possible_moves){
            if(maximizer){
                currentScore = minimax(game.newState(HUMAN, move[0], move[1]), depth + 1, false);
            }else{
                currentScore = minimax(game.newState(AGENT, move[0], move[1]), depth + 1, true);
            }
            scores.add(currentScore);
            moves.add(move);
        }
        if(maximizer){
            for (int i = 0; i <scores.size(); i++) {
                if(scores.get(i) > bestScore){
                    bestScore = scores.get(i);
                    agentMove = moves.get(i);
                }
            }

        }else{
            for (int i = 0; i <scores.size(); i++) {
                if(scores.get(i) < bestScore){
                    bestScore = scores.get(i);
                    agentMove = moves.get(i);
                }
            }
        }
        return bestScore;

    }


    void play () {
        while (true) {  // infinite iterations
            setPlayers();

            while (!b.endGame(true)) { // while iteration is running
                int x, y;
                if (human_turn) {
                    System.out.println("Where do you want to put " + HUMAN.name + ":");
                    x = sc.nextInt();
                    y = sc.nextInt();
                    HUMAN.makeMove(b, x, y);
                } else {
                    System.out.println("Computers turn:");
                    int best = minimax(b, 0, true);
                    System.out.println(best);
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

