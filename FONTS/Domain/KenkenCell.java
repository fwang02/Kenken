package Domain;

/**
 * Esta clase representa una casilla de Kenken. Contiene la posición x e y de la casilla
 * en la matriz del kenken y un valor.
 * 
 * @author Javier Parcerisas
 */
public class KenkenCell {
    private final int posX;
    private final int posY;
    private int valor;
    private boolean locked;

    /**
	 * Constructora de la clase KenkenCage
     * 
     * @param posX posición en x de la casilla
     * @param posY posición en y de la casilla
	 */
    public KenkenCell(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        valor = 0;
        locked = false;
    }

    /**
	 * Constructora de la clase KenkenCage
     * 
     * @param posX posición en x de la casilla
     * @param posY posición en y de la casilla
     * @param valor valor que contiene la casilla
     * @param locked indica si el la casilla pertenece a una región o no
	 */
    public KenkenCell(int posX, int posY, int valor, boolean locked) {
        this.posX = posX;
        this.posY = posY;
        this.valor = valor;
        this.locked = locked;
    }

    /**
	 * Setter que nos permite asignar un valor a la casilla
     * 
     * @param val valor a asignar a la casilla
	 */
    public void setValue(int val) {
        valor = val;
    }

    /**
	 * Getter que nos permite obtener el valor de la casilla
     * 
     * @return devuelve el valor de la casilla
	 */
    public int getValue() {
        return valor;
    }

    /**
	 * Getter que nos permite obtener la posición en x de la casilla
     * 
     * @return devuelve la posición en x de la casilla
	 */
    public int getPosX() {
        return posX;
    }

    /**
	 * Getter que nos permite obtener la posición en y de la casilla
     * 
     * @return devuelve la posición en y de la casilla
	 */
    public int getPosY() {
        return posY;
    }

    /**
	 * Getter que nos permite obtener la posición de la casilla
     * 
     * @return devuelve la posición de la casilla como instancia de la clase Pos
	 */
    public Pos getPos() {
        return new Pos(posX, posY);
    }

    /**
	 * Función que modifica el estado de la casilla y la indica como bloqueada
	 */
    public void setLocked() {
        locked = true;
    }

    /**
	 * Función que modifica el estado de la casilla y la indica como desbloqueada
	 */
    public void setUnlocked() {
        locked = false;
    }

    /**
	 * Función de consulta que nos indica el estado de la casilla
     * 
     * @return devuelve cierto si la casilla ya pertenece a una región o falso si no pertenece
	 */
    public boolean isLocked() {
        return locked;
    }
}
