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

    public void setValue(int val) {  //@javi: feiyang he añadido esto para poder ir poniendo los valores a las cells :D cuando los modifiquemos.
        valor = val;                //tipo al momento de inicializarlos les paso el valor = -1. Cuando genere la matriz correctamente podre usar esta
    }                               //funcion para modificar el valor de la celda.

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
