package ru.gb.saikalb.serverchat;

/**
 * Задача: Создать простейшее окно управления сервером (по сути, любым),
 * содержащее две кнопки (JButton) – запустить сервер и остановить сервер.
 * Кнопки должны просто логировать нажатие (имитировать запуск и остановку
 * сервера, соответственно) и выставлять внутри интерфейса соответствующее
 * булево isServerWorking.
 */
/**
 * Отправлять сообщения из текстового поля сообщения в лог по нажатию кнопки или по нажатию клавиши Enter на поле
 * ввода сообщения;
 * Продублировать импровизированный лог (историю) чата в файле; При запуске клиента чата заполнять
 * поле истории из файла, если он существует.
 * Обратите внимание, что чаще всего история сообщений хранится на сервере и
 * заполнение истории чата лучше делать при соединении с сервером, а не при открытии окна клиента.
 */


import javax.swing.*;
import java.awt.*;

/**
 * ServerWindow - класс, наследующий JFrame, предназначенный для создания окна управления сервером.
 */

public class ServerWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 340;
    private static final int WINDOW_WIDTH = 350;
    private static final int WINDOW_POS_X = 300;
    private static final int WINDOW_POS_Y = 100;

    private final JTextField loginField = new JTextField("  ЛОГИН ");

    ServerWindow(){
        setTitle("ПОДКЛЮЧЕНИЕ К СЕРВЕРУ");
        setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel grid = new JPanel(new GridLayout(3, 2));
        grid.add(loginField);
        JTextField passwordField = new JPasswordField("  ПАРОЛЬ ");
        grid.add(passwordField);
        JTextField serverField = new JTextField("  АДРЕС СЕРВЕРА ");
        grid.add(serverField);
        JTextField portField = new JTextField("  ПОРТ ");
        grid.add(portField);
        JTextField forgotP = new JTextField("  ЗАБЫЛИ ПАРОЛЬ? ");
        grid.add(forgotP);
        JButton buttonConnect = new JButton("ПОДКЛЮЧИТЬСЯ");
        grid.add(buttonConnect);

        add(grid);
        buttonConnect.addActionListener(e -> {
            String login = loginField.getText().trim();
            new ClientGUI(login);
        });

        setVisible(true);
    }
}