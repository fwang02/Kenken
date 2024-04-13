package Domain;

public class KenkenCell {
    private final int posX;
    private final int posY;
    private int valor;
    private boolean locked;

    //esta creadora para las casillas del tablero vacío.
    KenkenCell(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        valor = 0;
        locked = false;
    }
    
    KenkenCell(int posX,int posY,int valor, boolean locked) {
        this.posX = posX;
        this.posY = posY;
        this.valor = valor;
        this.locked = locked;
    }

    public void setValue(int val) {  
        valor = val;                
    }  

    public int getValue() {
        return valor;
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
