public class Player {

    char name;

    Player(char OorX){
        this.name = OorX;
    }


    void makeMove(Board b, int i, int j){
        b.processMove(this.name, i, j);
    }

}
