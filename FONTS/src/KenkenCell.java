package FONTS.src;

public class KenkenCell {
    private int posX;
    private int posY;
    private int valor;
    private boolean locked;

    KenkenCell(int posX,int posY,int valor, boolean locked) {
        this.posX = posX;
        this.posY = posY;
        this.valor = valor;
        this.locked = locked;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getValue() {
        return valor;
    }

    public boolean isLocked() {
        return locked;
    }
}
