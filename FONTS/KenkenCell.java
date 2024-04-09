public class KenkenCell {
    private final int posX;
    private final int posY;
    private int value;
    private boolean locked;

    //esta creadora para las casillas del tablero vacío.
    KenkenCell(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        value = -1;
        locked = false;
    }
    
    KenkenCell(int posX,int posY,int valor, boolean locked) {
        this.posX = posX;
        this.posY = posY;
        this.value = valor;
        this.locked = locked;
    }

    public void setValue(int val) {  
        value = val;
    }

    public int getValue() {
        return value;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setLocked() {
        locked = true;
    }

    public void setUnlocked() {
        locked = false;
    }

    public boolean isLocked() {
        return locked;
    }
}
