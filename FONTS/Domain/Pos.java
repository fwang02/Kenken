package Domain;

public class Pos {
    public int posX;
    public int posY;

    public Pos(int x, int y) {
        posX = x;
        posY = y;
    }

    public void print() {
        System.out.println("X:"+posX+" Y:"+posY);
    }
}
