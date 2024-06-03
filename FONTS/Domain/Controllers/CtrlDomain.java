package Domain.Controllers;

import Domain.*;
import Domain.Operation.*;

import java.io.File;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Esta clase es el controlador de dominio principal que gestiona las operaciones de dominio de usuario y Kenken.
 * Interactúa con los controladores de dominio de usuario y Kenken para realizar operaciones relacionadas con los usuarios y los juegos Kenken.
 *
 * @author Feiyang Wang
 */
public class CtrlDomain {
    private final CtrlDomainUser ctrlDomainUser;
    private final CtrlDomainKenken ctrlDomainKenken;

    public CtrlDomain() {
        ctrlDomainUser = new CtrlDomainUser();
        ctrlDomainKenken = new CtrlDomainKenken();
    }

    /**
     * Inicia sesión de un usuario.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return true si el inicio de sesión fue exitoso, false en caso contrario.
     */
    public boolean loginUser(String username, String password) {
        if(!ctrlDomainUser.isUserExist(username)) return false;
        if(!ctrlDomainUser.isPasswordCorrect(username,password)) return false;
        ctrlDomainUser.loginUser(username);
        return true;
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param username El nombre de usuario a registrar.
     * @param password La contraseña del usuario a registrar.
     * @return true si el registro fue exitoso, false en caso contrario.
     */
    public boolean registerUser(String username, String password) {
        return ctrlDomainUser.addUser(username,password);
    }

    /**
     * Obtiene el ranking de los jugadores.
     *
     * @return Una cola de prioridad con las puntuaciones máximas de los jugadores.
     */
    public PriorityQueue<PlayerScore> getRanking() {
        return ctrlDomainUser.getRanking();
    }

    /**
     * Resuelve el juego actual.
     *
     * @return true si el juego se resolvió con éxito, false en caso contrario.
     */
    public boolean solveCurrent() {
        return ctrlDomainKenken.solveCurrentGame();
    }

    /**
     * Abre un juego Kenken desde un fichero.
     *
     * @param fileName El nombre del fichero del juego Kenken a abrir.
     * @return true si el juego se abrió con éxito, false en caso contrario.
     */
    public boolean openKenkenByFile(String fileName) {
        return ctrlDomainKenken.solveKenkenByFile(fileName);
    }

    /**
     * Abre un juego Kenken desde un fichero.
     *
     * @param file El fichero del juego Kenken a abrir.
     * @return true si el juego se abrió con éxito, false en caso contrario.
     */
    public boolean openKenkenByFile(File file) {
        return ctrlDomainKenken.solveKenkenByFile(file);
    }

    /**
     * Continúa un juego desde un fichero.
     *
     * @param file El fichero del juego a continuar.
     * @return true si el juego se continuó con éxito, false en caso contrario.
     */
    public boolean continueGame(File file) {
        return ctrlDomainKenken.continueKenken(file);
    }

    /**
     * Genera un juego Kenken a partir de la vista.
     *
     * @param size El tamaño del juego Kenken a crear.
     * @param operations El conjunto de operaciones a utilizar en el juego.
     * @param diff El nivel de dificultad del juego.
     * @return true si el juego se creó con éxito, false en caso contrario.
     */
    public boolean generateKenkenFromView(int size, HashSet<String> operations, String diff) {
        HashSet<Operation> opSets = new HashSet<>();
        for(String op : operations) {
            switch (op) {
                case "ADD":
                    opSets.add(new ADD());
                    break;
                case "SUB":
                    opSets.add(new SUB());
                    break;
                case "MULT":
                    opSets.add(new MULT());
                    break;
                case "DIV":
                    opSets.add(new DIV());
                    break;
                case "POW":
                    opSets.add(new POW());
                    break;
                case "MOD":
                    opSets.add(new MOD());
                    break;
            }
        }
        return ctrlDomainKenken.generateKenkenByDifficulty(size,opSets,TypeDifficulty.valueOf(diff));
    }


    public int[] hintCurrent(int[] values) {
        ctrlDomainKenken.incrHintsCurrGame();
        return ctrlDomainKenken.hintCurrentGame(values);
    }

    /**
     * Obtiene una pista para el juego actual.
     *
     * @param values Los valores del juego para obtener la pista.
     * @return Un array de enteros que representa la pista.
     */
    public boolean checkCurrent(int[] values) {
        return ctrlDomainKenken.checkCurrentGame(values);
    }

    /**
     * Actualiza el máximo de puntos del usuario actual.
     *
     * @param newMaxPoint Los nuevos puntos máximos.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean newMaxPoint(int newMaxPoint) {
        return ctrlDomainUser.updateMaxPointCurrUser(newMaxPoint);
    }

    /**
     * Obtiene el tamaño del juego Kenken.
     *
     * @return El tamaño del juego Kenken.
     */
    public int getKenkenSize() {
        return ctrlDomainKenken.getCurrentGameSize();
    }

    /**
     * Verifica si un usuario existe.
     *
     * @param username El nombre de usuario a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean isUserExist(String username) {
        return ctrlDomainUser.isUserExist(username);
    }

    /**
     * Obtiene el nombre del usuario que ha iniciado sesión.
     *
     * @return El nombre del usuario que ha iniciado sesión.
     */
    public String getLoggedUserName() {
        return ctrlDomainUser.getLoggedUser();
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void logoutCurrentUser() {
        ctrlDomainUser.logoutCurrentUser();
    }

    /**
     * Obtiene el número de regiones en el juego Kenken.
     *
     * @return El número de regiones en el juego Kenken.
     */
    public int getNCages() {
        return ctrlDomainKenken.getCurrentGame().getNumberCages();
    }

    /**
     * Obtiene las coordenadas X de las celdas de una región.
     *
     * @param index El índice de la región.
     * @return Un array de enteros con las coordenadas X de las celdas de la región.
     */
    public int[] getCageCellsX(int index) {
        return ctrlDomainKenken.getCurrentCageCellsX(index);
    }

    /**
     * Obtiene las coordenadas Y de las celdas de una región.
     *
     * @param index El índice de la región.
     * @return Un array de enteros con las coordenadas Y de las celdas de la región.
     */
    public int[] getCageCellsY(int index) {
        return ctrlDomainKenken.getCurrentCageCellsY(index);
    }

    /**
     * Obtiene la operación de una región.
     *
     * @param index El índice de la región.
     * @return Un carácter que representa la operación de la región.
     */
    public char getCageOp(int index) {
        KenkenCage cage = ctrlDomainKenken.getCages().get(index);

        return cage.getOperationAsChar();
    }

    /**
     * Obtiene el resultado de una región.
     *
     * @param index El índice de la región.
     * @return Un entero que representa el resultado de la región.
     */
    public int getCageRes(int index) {
        KenkenCage cage = ctrlDomainKenken.getCages().get(index);

        return cage.getResult();
    }

    /**
     * Obtiene el tablero del juego.
     *
     * @return Un array de enteros que representa el tablero del juego.
     */
    public int[] getCurrentBoard() {
        return ctrlDomainKenken.getCurrentGameBoard();
    }

    /**
     * Obtiene las celdas del juego.
     *
     * @return Un array de enteros que representa las celdas del juego.
     */
    public int[] getCells() {
        return ctrlDomainKenken.getCurrentCells();
    }

    /**
     * Establece una región en el juego.
     *
     * @param cellsX Un array de enteros con las coordenadas X de las celdas de la región.
     * @param cellsY Un array de enteros con las coordenadas Y de las celdas de la región.
     * @param op Un entero que representa la operación de la región.
     * @param res Un entero que representa el resultado de la región.
     */
    public void setCage(int[] cellsX, int[] cellsY, int op, int res) {
        Pos[] cells = new Pos[cellsX.length];

        for (int i = 0; i < cellsX.length; ++i) {
            cells[i] = new Pos(cellsX[i], cellsY[i]);
        }
        Operation o = CtrlDomainKenken.getOperation(op);
        ctrlDomainKenken.getCurrentGame().addOpCage(o, res, cells);

    }

    /**
     * Inicia el juego actual.
     *
     * @param size El tamaño del juego a iniciar.
     */
    public void initCurrentGame(int size) {
        if (ctrlDomainKenken.hasCurrentGame()) {
            System.out.println("Reset Current Game");
        }

        HashSet<Operation> opSets = new HashSet<>();
        opSets.add(new ADD());
        opSets.add(new SUB());
        opSets.add(new MULT());
        opSets.add(new DIV());
        opSets.add(new MOD());
        opSets.add(new POW());

        Kenken k = ctrlDomainKenken.getCurrentGame();

        ctrlDomainKenken.setCurrentGame(new Kenken(size, opSets, TypeDifficulty.CUSTOM));
    }

    /**
     * Guarda el juego actual.
     *
     * @param gameName El nombre del juego a guardar.
     * @param values Los valores del juego a guardar.
     * @return true si el juego se guardó con éxito, false en caso contrario.
     */
    public boolean saveCurrent(String gameName, int[] values) {
        return ctrlDomainKenken.saveCurrentGame(gameName, values);
    }

    /**
     * Obtiene los puntos del juego.
     *
     * @return Un entero que representa los puntos del juego.
     */
    public int getGamePoints() {
        return ctrlDomainKenken.getPoints();
    }

    /**
     * Obtiene las celdas que no cambian.
     * @return una array de las celdas bloqueadas no igual que 0.
     */
    public int[] getLockedCells() {
         return ctrlDomainKenken.getLockedCells();
    }

    /**
     * Asigna las celdas que no cambian.
     */
    public void setLockedCells(int[] valCells) {
        ctrlDomainKenken.setLockedCells(valCells);
    }
}
