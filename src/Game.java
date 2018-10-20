import com.sun.tools.attach.AgentInitializationException;

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
            return 10-depth ;
        } else if (game.isWinner(HUMAN.name)) {
            return -10 + depth;
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
        int currentScore;
        int max =  Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Board tmp_state;
        int counter = 0;
        for(int[] move: possible_moves){
            if(maximizer){
                HUMAN.makeMove(b, move[0], move[1]);
                tmp_state = b;
                currentScore = minimax(tmp_state, depth + 1, false);
                max = Math.max(currentScore, max);
                if(currentScore>= 0){if(depth ==0){agentMove = move;}}
                if(counter == possible_moves.size() - 1 && max<0){
                    if(depth == 0) agentMove = move;
                }
            }else{
                AGENT.makeMove(b, move[0], move[1]);
                tmp_state = b;
                currentScore = minimax(tmp_state, depth + 1, true);
                min = Math.min(currentScore, min);
                if(min == -1) {b.b[move[0]][move[1]] = '-'; break;}
            }
            b.b[move[0]][move[1]] = '-';
            counter++;
        }
        return maximizer?max:min;

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

