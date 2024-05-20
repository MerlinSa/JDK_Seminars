import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private static final int WIDTH = 555; //размеры
    private static final int HEIGHT = 507;

    private JButton btnStart, btnExit; //виджеты-кнопки
    private SettingWindow settingWindow; //
    private Map map; //объект мап, отвечает за игру

    /**
     * Конструктор класса GameWindow, в нем задаем значения
     */

    GameWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //закрытие по нажатию на крестик
        setSize(WIDTH, HEIGHT); //размеры
        setLocationRelativeTo(null); //метод исп-зован для того, чтобы экран появлялся по центру.

        setTitle("TicTacToe"); //задаем заголовак/название игры
        setResizable(false); //возможность изменения размера, состояние фолс
        btnStart = new JButton("New Game"); //инициализировали кнопку старта нов игры
        btnExit = new JButton("Exit");//иниц-ли кнопку выхода из игры
        settingWindow = new SettingWindow(this); //создали окно настроек. this указывает ссылку на текущий объект.указываем ссылку на GameWindow.
        map = new Map();//создается мап и обработчики нажатий для каждого из кнопок:
//для этого вызываем метод addActionLis-r и внутри создаем анон.класс кот-й реализует интерфейс ActionLis-r.
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //переопределеям метод
                System.exit(0); //идет закрытие.
            }
        });

        btnStart.addActionListener(new ActionListener() { //нажатие кнопки старт
            @Override
            public void actionPerformed(ActionEvent e) {
                settingWindow.setVisible(true); //делает видимом окно настроек при нажатии кнопки старт
            }
        });
//создаются панелки на которых размещаются кнопки, панелка в 1 строчку и две колонки:
        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnExit);

        add(panBottom, BorderLayout.SOUTH);//саму панелку добавляем вниз, юг это вниз
        add(map); // добавляем мап, мап это игра, будет расположена по центру по дефолту(если 2го аргумента нет)

        setVisible(true);
    }
//метод "начать новую игру" принимает 4 аргумента: мод, размеры, и длина победы(сколько подряд крестиков или ноликов).
    void startNewGame(int mode, int sizeX, int sizeY, int winLen){
        map.startNewGame(mode, sizeX, sizeY, winLen);//обращаемся к игре(мап) и передаем все те же параметры.
    }
}
