import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс из одной кнопки
 */
public class SettingWindow extends JFrame {
    private static final int WIDTH = 280;//размеры
    private static final int HEIGHT = 350;

    private JButton btnStart; //кнопка старт

    private static final String SELECT_FIELD_SIZES ="Выбранный размер поля: ";
    private static final String INSTALLED_LENGTH ="Выбранная длина для победы: ";

    /**
     * Конструктор класса инициализирует кнопку принимает
     * @param gameWindow стартовое игровое окно.
     */

    SettingWindow(GameWindow gameWindow){
        btnStart = new JButton("Start new game");

        setLocationRelativeTo(gameWindow); //расположение относительно стратого окна.
        setSize(WIDTH, HEIGHT);//установка размеров

//слушатель нажатий:Добавляем поведение при нажатии на кнопку страта игры
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //при нажатии
                setVisible(false); //как только начинаем новую игру окно настроек скрывается.
                gameWindow.startNewGame(0, 3, 3, 3); //передаются константы в параметры при нажатии кнопки старт.
                setVisible(false);
            }
        });

        //создание блока настроек
        JPanel panBottom = new JPanel(new GridLayout(3, 1));

        //выбор типа игры
        JPanel typeGame = new JPanel(new GridLayout(3, 1));
        typeGame.add(new JLabel("Выберите режим игры"));
        ButtonGroup group1 = new ButtonGroup();
        JRadioButton btn1 = new JRadioButton("Игрок против компьютера");
        btn1.setSelected(true);
        JRadioButton btn2 = new JRadioButton("Игрок против игрока");
        group1.add(btn1);
        group1.add(btn2);
        typeGame.add(btn1);
        typeGame.add(btn2);


        //Выбор длины победы
        JPanel sizeWin = new JPanel(new GridLayout(3,1));
        sizeWin.add(new JLabel("Выберите длину для победы"));
        JLabel labelInstallLen = new JLabel(INSTALLED_LENGTH);
        sizeWin.add(labelInstallLen);
        JSlider sizeW = new JSlider(3,10);

        sizeW.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                int length =sizeW.getValue();
                labelInstallLen.setText(INSTALLED_LENGTH + length);
            }
        });
        sizeWin.add(sizeW);

        //Выбор размеров поля
        JPanel sizeFeild = new JPanel(new GridLayout(3,1));
        sizeFeild.add(new JLabel("Выберите размер поля"));
        JLabel labelCurrentSize =new JLabel(SELECT_FIELD_SIZES);
        sizeFeild.add(labelCurrentSize);
        JSlider sizeF = new JSlider(3,10);

        sizeF.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                int size =sizeF.getValue();
                labelCurrentSize.setText(SELECT_FIELD_SIZES + size);
                sizeW.setMaximum(size);

            }
        });

        sizeFeild.add(sizeF);

//Заполнения окна настроек
        panBottom.add(typeGame);
        panBottom.add(sizeFeild);
        panBottom.add(sizeWin);
        //Добавляем панель настроек на основное окно
        add(panBottom);
//Добавляем кнопку начала игры
        add(btnStart);//кнопка добавляется на панелку т.е. вся панелка это 1кнопка.
    }
}
