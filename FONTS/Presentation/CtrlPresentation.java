package Presentation;

import Domain.Controllers.CtrlDomain;
import Domain.PlayerScore;
import Presentation.Views.GameCreatorView;
import Presentation.Views.GameView;
import Presentation.Views.MainMenuView;
import Presentation.Views.PlayOptionView;

import java.io.File;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Esta clase es el controlador de presentación que gestiona las transiciones entre las diferentes vistas y
 * interactúa con la capa de dominio.
 *
 * @author feiyang.wang
 */
public class CtrlPresentation {

    /**
     * El controlador de dominio que interactúa con la capa de dominio.
     */
    private static CtrlDomain ctrlDomain;
    private final MainMenuView mainMenuView;
    private PlayOptionView playOptionView;
    private GameView gameView;
    private final GameCreatorView gameCreatorView;

    /**
     * Constructor de CtrlPresentation. Inicializa las vistas y el controlador de dominio.
     */
    public CtrlPresentation () {
        ctrlDomain = new CtrlDomain();
        mainMenuView = new MainMenuView(this);
        gameCreatorView = new GameCreatorView(this);
        gameView = new GameView(this);
    }

    /**
     * Inicia la presentación mostrando la vista del menú principal.
     */
    public void initPresentation() {
        mainMenuView.makeVisible();
    }

    /**
     * Gestiona la transición de la vista principal a la vista de opciones de juego.
     */
    public void mainViewToPlayOptionView() {
        mainMenuView.makeInvisible();
        if(playOptionView == null) playOptionView = new PlayOptionView(this);
        playOptionView.makeVisible();
        playOptionView.addLoggedUser();
    }

    /**
     * Gestiona la transición de las vistas.
     */
    public void playOptionViewToMainMenuView() {
        playOptionView.makeInvisible();
        mainMenuView.makeVisible();
        ctrlDomain.logoutCurrentUser();
    }

    /**
     * Gestiona la transición de las vistas.
     */
    public void playOptionViewToGameCreatorView() {
        playOptionView.makeInvisible();
        gameCreatorView.makeVisible();
    }

    /**
     * Gestiona la transición de las vistas.
     */
    public void playOptionViewToGameView() {
        playOptionView.makeInvisible();
        if(gameView == null) gameView = new GameView(this);
        gameView.startPlay();
        gameView.makeVisible();
    }

    /**
     * Gestiona la transición de las vistas.
     */
    public void gameViewToPlayOptionView() {
        gameView.makeInvisible();
        playOptionView.backToPlayOption();
        playOptionView.makeVisible();
    }

    /**
     * Gestiona la transición de las vistas.
     */
    public void gameCreatorViewToPlayOptionView() {
        gameCreatorView.makeInvisible();
        playOptionView.backToPlayOption();
        playOptionView.makeVisible();
    }

    /**
     * Gestiona la transición de las vistas.
     */
    public void gameCreatorViewToGameView() {
        gameCreatorView.makeInvisible();
        gameView.startPlay();
        gameView.makeVisible();
    }

    //Llamadas a la capa dominio
    /**
     * Obtiene el ranking de los jugadores.
     *
     * @return Una cola de prioridad con las puntuaciones máximas de los jugadores.
     */
    public PriorityQueue<PlayerScore> getRanking() {
        return ctrlDomain.getRanking();
    }

    /**
     * Crea un juego Kenken.
     *
     * @param size El tamaño del juego Kenken a crear.
     * @param operations El conjunto de operaciones a utilizar en el juego.
     * @param diff El nivel de dificultad del juego.
     * @return true si el juego se creó con éxito, false en caso contrario.
     */
    public boolean createKenken(int size, HashSet<String> operations, String diff) {
        return ctrlDomain.generateKenkenFromView(size,operations,diff);
    }

    /**
     * Abre un juego Kenken desde un fichero.
     *
     * @param file El fichero del juego Kenken a abrir.
     * @return true si el juego se abrió con éxito, false en caso contrario.
     */
    public boolean openKenkenByFile(File file) {
        return ctrlDomain.openKenkenByFile(file);
    }

    /**
     * Abre un juego Kenken desde un fichero.
     *
     * @param fileName El nombre del fichero del juego Kenken a abrir.
     * @return true si el juego se abrió con éxito, false en caso contrario.
     */
    public boolean openKenkenByFile(String fileName) {
        return ctrlDomain.openKenkenByFile(fileName);
    }

    /**
     * Guarda el juego actual.
     *
     * @param gameName El nombre del juego a guardar.
     * @param values Los valores del juego a guardar.
     * @return true si el juego se guardó con éxito, false en caso contrario.
     */
    public boolean saveCurrentGame(String gameName, int[] values) {
        return ctrlDomain.saveCurrent(gameName, values);
    }

    /**
     * Continúa un juego desde un fichero.
     *
     * @param file El fichero del juego a continuar.
     * @return true si el juego se continuó con éxito, false en caso contrario.
     */
    public boolean continueGame(File file) {
        return ctrlDomain.continueGame(file);
    }

    /**
     * Obtiene el tamaño del juego Kenken.
     *
     * @return El tamaño del juego Kenken.
     */
    public int getKenkenSize() {
        return ctrlDomain.getKenkenSize();
    }

    /**
     * Inicia sesión de un usuario.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return true si el inicio de sesión fue exitoso, false en caso contrario.
     */
    public boolean loginUser(String username, String password) {
        return ctrlDomain.loginUser(username,password);
    }

    /**
     * Verifica si un usuario existe.
     *
     * @param username El nombre de usuario a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean isUserExist(String username) {
        return ctrlDomain.isUserExist(username);
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param username El nombre de usuario a registrar.
     * @param password La contraseña del usuario a registrar.
     * @return true si el registro fue exitoso, false en caso contrario.
     */
    public boolean registerUser(String username, String password) {
        return ctrlDomain.registerUser(username,password);
    }

    /**
     * Obtiene el nombre del usuario que ha iniciado sesión.
     *
     * @return El nombre del usuario que ha iniciado sesión.
     */
    public String getLoggedUserName() {
        return ctrlDomain.getLoggedUserName();
    }

    /**
     * Obtiene el número de regiones en el juego Kenken.
     *
     * @return El número de regiones en el juego Kenken.
     */
    public int getNCages() {
        return ctrlDomain.getNCages();
    }

    /**
     * Obtiene las coordenadas X de las celdas de una región.
     *
     * @param index El índice de la región.
     * @return Un array de enteros con las coordenadas X de las celdas de la región.
     */
    public int[] getCageCellsX(int index) {
        return ctrlDomain.getCageCellsX(index);
    }

    /**
     * Obtiene las coordenadas Y de las celdas de una región.
     *
     * @param index El índice de la región.
     * @return Un array de enteros con las coordenadas Y de las celdas de la región.
     */
    public int[] getCageCellsY(int index) {
        return ctrlDomain.getCageCellsY(index);
    }

    /**
     * Obtiene la operación de una región.
     *
     * @param index El índice de la región.
     * @return Un carácter que representa la operación de la región.
     */
    public char getCageOp(int index) {
        return ctrlDomain.getCageOp(index);
    }

    /**
     * Obtiene el resultado de una región.
     *
     * @param index El índice de la región.
     * @return Un entero que representa el resultado de la región.
     */
    public int getCageRes(int index) {
        return ctrlDomain.getCageRes(index);
    }

    /**
     * Establece el tamaño del creador de juegos.
     *
     * @param selectedSize El tamaño seleccionado para el creador de juegos.
     */
    public void setGameCreatorSize(int selectedSize) {
        gameCreatorView.initGameCreator(selectedSize);
    }

    /**
     * Obtiene el tablero del juego.
     *
     * @return Un array de enteros que representa el tablero del juego.
     */
    public int[] getBoard() {
        return ctrlDomain.getCurrentBoard();
    }

    /**
     * Obtiene las celdas del juego.
     *
     * @return Un array de enteros que representa las celdas del juego.
     */
    public int[] getCells() {
        return ctrlDomain.getCells();
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
        ctrlDomain.setCage(cellsX, cellsY, op, res);
    }

    /**
     * Inicia el juego actual.
     *
     * @param size El tamaño del juego a iniciar.
     */
    public void initCurrentGame(int size) {
        ctrlDomain.initCurrentGame(size);
    }

    /**
     * Resuelve el juego actual.
     *
     * @return true si el juego se resolvió con éxito, false en caso contrario.
     */
    public boolean solve() {
        return ctrlDomain.solveCurrent();
    }

    /**
     * Verifica el juego actual.
     *
     * @param values Los valores del juego a verificar.
     * @return true si el juego se verificó con éxito, false en caso contrario.
     */
    public boolean check(int[] values) {
        return ctrlDomain.checkCurrent(values);
    }

    /**
     * Obtiene una pista para el juego actual.
     *
     * @param values Los valores del juego para obtener la pista.
     * @return Un array de enteros que representa la pista.
     */
    public int[] hint(int[] values) {
        return ctrlDomain.hintCurrent(values);
    }

    /**
     * Obtiene los puntos del juego.
     *
     * @return Un entero que representa los puntos del juego.
     */
    public int getGamePoints() {
        return ctrlDomain.getGamePoints();
    }

    /**
     * Actualiza el máximo de puntos del usuario actual.
     *
     * @param gamePoints Los puntos del juego a actualizar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean updateMaxPoint(int gamePoints) {
        return ctrlDomain.newMaxPoint(gamePoints);
    }

    /**
     * Obtiene las celdas que no cambian.
     * @return una array de las celdas bloqueadas no igual que 0.
     */
    public int[] getLockedCells() { return ctrlDomain.getLockedCells();}

    /**
     * Asigna las celdas que no cambian.
     */
    public void setLockedCells(int[] valCells) {
        ctrlDomain.setLockedCells(valCells);
    }
}