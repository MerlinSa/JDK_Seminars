import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;

public class Map extends JPanel {
    private static final Random RANDOM = new Random();//объект рандом для случайных значений
    private static final int HUMAN_DOT = 1;//человек свою фигуру поставил
    private static final int AI_DOT = 2;//ии фигуру поставил
    private static final int EMPTY_DOT = 0; //ничего не стоит
    private static final int PADDING = 10; //отступы

    private int gameStateType; //переменная обозначает состояние игры/кто победил
    private static final int STATE_GAME = 0;//игра
    private static final int STATE_WIN_HUMAN = 1;//победил чел
    private static final int STATE_WIN_AI = 2;
    private static final int STATE_DRAW = 3; //ничья
//сообщения для всех состояний:
    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_AI = "Победил компьютер!";
    private static final String MSG_DRAW = "Ничья!";

    private int width, height, cellWidth, cellHeight;
    private int mode, fieldSizeX, fieldSizeY, winLen; //мод,размер ячейуи поX поY, кол-во подряд идущ-х значений для победы.
    private int[][] field; //массив для хранения ходов игроков.
    private boolean gameWork; //флажок озночает что игра еще не закончена.

    /**
     * Конструктор класса, в нем установ-м цвет фона,доб. слушаетль нажатий по нажатию мышки
     */

    Map() {
        setBackground(Color.WHITE); //установ цвет фона
        addMouseListener(new MouseAdapter() {//доб слушатель нажатий по нажатию мышки
            @Override
            public void mouseReleased(MouseEvent e) {
                if (gameWork) { //если флаг=тру
                    update(e);//делаем апдейт, передавая параметр е , е-это событие(event),что произошло.
                    //ивент содержит коорд-ты нажатия т.е. в объекте будет сохранена адрес, куда кликнула мышка.
                }
            }
        });
    }

    /**
     * метод создания массива для хранения крестиков и ноликов
     */
    private void initMap() {
        field = new int[fieldSizeY][fieldSizeX]; //по дефолту значения [0] [0]
    }

    /**
     * метод для начала игры
     * @param mode
     * @param sizeX
     * @param sizeY
     * @param winLen
     */
    void startNewGame(int mode, int sizeX, int sizeY, int winLen) {
        this.mode = mode;
        this.fieldSizeX = sizeX;
        this.fieldSizeY = sizeY;
        this.winLen = winLen;

        initMap(); //создаем нов поле, для того чтобы несколько раз начинать игру заново.
        gameWork = true; //игра пошла
        gameStateType = STATE_GAME; //игра идет

        repaint(); //отрисовка игры
    }

    /**
     * в метод принимает в себя
     * @param mouseEvent, обнаруживает позицию мышки, в какую ячейку нажатие произошло.
     */
    private void update(MouseEvent mouseEvent) {
        int x = mouseEvent.getX() / cellWidth; //коорд. по X
        int y = mouseEvent.getY() / cellHeight;//коорд. по Y узнаем
        if (!isValidCell(x, y) || !isEmptyCell(x, y)) {//проверяем в нужное ли место
            return;//!isValidCell(x, y) ничего не произойдет, можно кликать сколько угодно с таким условием.
        }//!isEmptyCell(x, y) будем ходить
        field[y][x] = HUMAN_DOT;//если все ок в ячейку записываем ход игрока.
        if (checkEndGame(HUMAN_DOT, STATE_WIN_HUMAN)) {
            return;
        }
        aiTurn(); //если игра еще незакончена тогда ии ходит
        repaint();
        checkEndGame(AI_DOT, STATE_WIN_AI); //после хода ии делаем снова проверку.
    }

    private void testBoard(){ //выводит состояние каждой ячейки
        for (int i = 0; i < 3; i++) {
            System.out.println(Arrays.toString(field[i]));
        }
        System.out.println();
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == EMPTY_DOT;
    }

    private void aiTurn() {//много раз сделает рандом ходов пока не попадет в пусту ячейку.
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field[y][x] = AI_DOT;
    }

    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkEndGame(int dot, int gameOverType) {
        if (checkWin(dot)) {
            this.gameStateType = gameOverType;
            repaint();
            return true;
        } else if (isMapFull()) {
            this.gameStateType = STATE_DRAW;
            repaint();
            return true;
        }
        return false;
    }

    private boolean checkWin(int dot){
        for (int i = 0; i < fieldSizeX; i++) {//цикл по горизонтали
            for (int j = 0; j < fieldSizeY; j++) {//цикл по вертикали
                if (checkLine(i, j, 1, 0, winLen, dot)) return true;//метод проверки
                if (checkLine(i, j, 1, 1, winLen, dot)) return true;
                if (checkLine(i, j, 0, 1, winLen, dot)) return true;
                if (checkLine(i, j, 1, -1, winLen, dot)) return true;
            }
        }
        return false;
    }

    /**
     * метод проверки линии для победы
     * @param x the x coordinate коорд.щелкнутой ячейки
     * @param y the y coordinate
     * @param vx коорд. по X вектор движения, куда надо двигатьсч
     * @param vy коорд. по Y вектор движения, чтобы проверить наличие подряд ид.знач.
     * @param len длина которая нас интересует.
     * @param dot число ходов (placeholder) чьи ходы?
     * @return
     */

    private boolean checkLine(int x, int y, int vx, int vy, int len, int dot){
        int far_x = x + (len - 1) * vx; //находим коор.след ячейки
        int far_y = y + (len - 1) * vy;
        if (!isValidCell(far_x, far_y)){ //проверяем что ячейка лежит на игровом поле
            return false;
        }//если все ок, то дальше в цикле собираемся сделать столько шагов сколько нас просили.
        for (int i = 0; i < len; i++) {
            if (field[y + i * vy][x + i * vx] != dot){
                return false;
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) { //вызывется когда вызывается метод repaint().
        super.paintComponent(g); //ghjdthztv
        if (gameWork) { //если игра идет
            render(g); //то мы ее перерисовываем
        }
    }

    private void render(Graphics g) {
        width = getWidth(); //получаем размер одного поля
        height = getHeight();
        cellWidth = width / fieldSizeX; //получаем размер 1 ячейки
        cellHeight = height / fieldSizeY;

        g.setColor(Color.BLACK); //выбираем цвет
        for (int h = 0; h < fieldSizeY; h++) {
            int y = h * cellHeight;
            g.drawLine(0, y, width, y);
        }
        for (int w = 0; w < fieldSizeX; w++) {
            int x = w * cellWidth;
            g.drawLine(x, 0, x, height);
        }

        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == EMPTY_DOT){ //ячейка пустая то мы с ней ничего не делаем.
                    continue;
                }
                if (field[y][x] == HUMAN_DOT) {
                    g.drawLine(x * cellWidth + PADDING, y * cellHeight + PADDING,
                            (x + 1) * cellWidth - PADDING, (y + 1) * cellHeight - PADDING);
                    g.drawLine(x * cellWidth + PADDING, (y + 1) * cellHeight - PADDING,
                            (x + 1) * cellWidth - PADDING, y * cellHeight + PADDING);
                } else if (field[y][x] == AI_DOT) {
                    g.drawOval(x * cellWidth + PADDING, y * cellHeight + PADDING,
                            cellWidth - PADDING * 2, cellHeight - PADDING * 2);
                } else {
                    throw new RuntimeException("unchecked value " + field[y][x] +
                            " in cell: x=" + x + " y=" + y);
                }
            }
        }
        if (gameStateType != STATE_GAME){
            showMessage(g);
        }
    }

    private void showMessage(Graphics g) {
        g.setColor(Color.DARK_GRAY);//выбираем цвет
        g.fillRect(0, getHeight() / 2, getWidth(), 70); //заливка фона
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman", Font.BOLD, 48));
        switch (gameStateType){
            case STATE_DRAW -> g.drawString(MSG_DRAW, 180, getHeight() / 2 + 60);
            case STATE_WIN_HUMAN -> g.drawString(MSG_WIN_HUMAN, 20, getHeight() / 2 + 60);
            case STATE_WIN_AI -> g.drawString(MSG_WIN_AI, 70, getHeight() / 2 + 60);
            default -> throw new RuntimeException("Unchecked gameOverState: " + gameStateType);
        }
        gameWork = false;
    }
}
