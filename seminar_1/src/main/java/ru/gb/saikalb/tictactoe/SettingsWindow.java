package ru.gb.saikalb.tictactoe;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 //todo Task1.
/*
Добавить на экран компоновщик-сетку с одним столбцом и добавить над существующей
кнопкой следующие компоненты в заданном порядке:
JLabel с заголовком«Выберите режим игры», сгруппированные в ButtonGroup
переключаемые JRadioButton с указанием режимов «Человек против компьютера»
и «Человек против человека»,
JLabel с заголовком «Выберите размеры поля»,
JLabel с заголовком «Установленный размер поля: »,
JSlider со значениями 3…10, JLabel с заголовком «Выберите длину для победы»,
JLabel с заголовком «Установленная длина: »,
JSlider со значениями 3…10.
*/
public class SettingsWindow extends JFrame {
    public static final String BTN_START = "Start new game";
    public static final String LABEL_CHOICE_MODE = "Выберите режим игры: ";
    public static final String BTN_HUMAN_VERSUS_AI = "Человек против компьютера";
    public static final String BTN_HUMAN_VERSUS_HUMAN = "Человек против человека";
    public static final String SIZE_PREFIX = "Установленный размер поля: ";
    public static final String WIN_LENGTH_PREFIX = "Установленная длина: ";
    public static final String LABEL_CHOICE_SIZE = "Выберите размеры поля: ";
    public static final String LABEL_CHOICE_WIN_LENGTH = "Выберите длину для победы";
    public static final int MODE_HVA = 0;
    public static final int MODE_HVH = 1;
    public static final int MIN_SIZE = 3;
    public static final int MAX_SIZE = 10;

    private static final int WIDTH = 230;
    private static final int HEIGHT = 350;
    JButton btnStart;
    JRadioButton humanVHuman, humanVAI;
    Label labelCurSize, labelWinLength;
    JSlider sizeSlider, winSlider;
    GameWindow gameWindow;
    JSlider slideWinLen;


    /*
    Данный код реализует перенос окна игры в центр родительского окна
    и добавляет основную панель и кнопку старта*/

     /**
      * Конструктор класса инициализирует кнопку принимает
      * @param gameWindow стартовое игровое окно.
      */
    SettingsWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        int centerGameWindowX = gameWindow.getX() + gameWindow.getWidth() / 2;
        int centerGameWindowY = gameWindow.getY() + gameWindow.getHeight() / 2;
        setLocation(centerGameWindowX - WIDTH / 2, centerGameWindowY - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);

        add(createMainPanel());
        add(createButtonStart(), BorderLayout.SOUTH);
    }
     //создание блока настроек
    private Component createMainPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));

        panel.add(createChoiceModePanel());
        panel.add(createChoiceSizePanel());
        panel.add(createChoiceWinLengthPanel());
        return panel;
    }

    private Component createButtonStart() {
        btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                startGame();
            }
        });
        return btnStart;
    }


    private void startGame() {
        int mode;
        if (humanVAI.isSelected()) {
            mode = MODE_HVA;
        } else if (humanVHuman.isSelected()) {
            mode = MODE_HVH;
        } else {
            throw new RuntimeException("Unknown game mode");
        }
        int size = sizeSlider.getValue();
        int winLength = winSlider.getValue();
        gameWindow.startNewGame(mode, size, size, winLength);
    }


    private void btnStartDelegate() {
        int gameMode;

        if (humanVAI.isSelected()) {
            gameMode = Map.MODE_HVA;
        } else if (humanVHuman.isSelected()) {
            gameMode = Map.MODE_HVH;
        } else {
            throw new RuntimeException("Unknown game mode");
        }
        int fieldSize = winSlider.getValue();
        int winLength = slideWinLen.getValue();
        gameWindow.startNewGame(gameMode, fieldSize, fieldSize, winLength);
        setVisible(false);
    }

    private Component createChoiceModePanel(){
        JPanel panel = new JPanel(new GridLayout(3, 1));
        Label title = new Label(LABEL_CHOICE_MODE);
        ButtonGroup buttonGroup = new ButtonGroup();
        humanVAI = new JRadioButton(BTN_HUMAN_VERSUS_AI);
        humanVHuman = new JRadioButton(BTN_HUMAN_VERSUS_HUMAN);
        buttonGroup.add(humanVAI);
        buttonGroup.add(humanVHuman);
        humanVAI.setSelected(true);

        panel.add(title);
        panel.add(humanVAI);
        panel.add(humanVHuman);

        return panel;
    }

    private Component createChoiceSizePanel(){
        JPanel panel = new JPanel(new GridLayout(3, 1));
        Label title = new Label(LABEL_CHOICE_SIZE);
        labelCurSize = new Label(SIZE_PREFIX + MIN_SIZE);
        sizeSlider = new JSlider(MIN_SIZE, MAX_SIZE, MIN_SIZE);
        sizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int curSize = sizeSlider.getValue();
                labelCurSize.setText(SIZE_PREFIX + curSize);
                winSlider.setMaximum(curSize);
            }
        });

        panel.add(title);
        panel.add(labelCurSize);
        panel.add(sizeSlider);
        return panel;
    }

    private Component createChoiceWinLengthPanel(){
        JPanel panel = new JPanel(new GridLayout(3, 1));
        Label title = new Label(LABEL_CHOICE_WIN_LENGTH);
        labelWinLength = new Label(WIN_LENGTH_PREFIX + MIN_SIZE);
        winSlider = new JSlider(MIN_SIZE, MAX_SIZE, MIN_SIZE);
        winSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelWinLength.setText(SIZE_PREFIX + winSlider.getValue());
            }
        });

        panel.add(title);
        panel.add(labelWinLength);
        panel.add(winSlider);
        return panel;
    }
}
/* todo Task2.
Добавить компонентам интерактивности, а именно, при перемещении ползунка слайдера
в соответствующих лейблах должны появляться текущие значения слайдеров.
Для этого необходимо добавить к слайдеру слушателя изменений
(как это было сделано для действия кнопки).
 */

/* todo Task3.
В методе обработчика нажатия кнопки необходимо заменить константы в аргументе
вызова метода старта игры на текущие показания компонентов (какая радио-кнопка
активна, значение слайдера размеров поля, значение слайдера выигрышной длины).
*/