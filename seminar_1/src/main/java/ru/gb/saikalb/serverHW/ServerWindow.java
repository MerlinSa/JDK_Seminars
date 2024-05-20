package ru.gb.saikalb.serverHW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final int POS_X = 500;
    private static final int POS_Y = 500;

    List<ClientWindow> clientWindowList;
    public static final String LOG_PATH = "./logs.txt";

    private JButton btnStart;
    private JButton btnStop;
    private JTextArea log;
    private boolean isServerWorking;


    public ServerWindow() {
        isServerWorking = false;
        clientWindowList = new ArrayList<ClientWindow>();
        createPanel();


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        setVisible(true);
    }

    private void createPanel() {
        log = new JTextArea();
        add(log);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerWorking){
                    addLog("Сервер работает");
                } else {
                    isServerWorking = true;
                    addLog("Сервер запущен");
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerWorking){
                    addLog("Сервер отключен");
                } else {
                    isServerWorking = false;
                    while (!clientWindowList.isEmpty()){
                        disconnectUser(clientWindowList.get(clientWindowList.size()-1));
                    }
                    addLog("Сервер остановлен");
                }
            }
        });

        panel.add(btnStart);
        panel.add(btnStop);
        return panel;
    }

    public void pushMessage(String text){
        if (!isServerWorking){
            return;
        }
        text += "";
        addLog(text);
        pullAnswerAll(text);
        saveInLog(text);
    }

    private void addLog(String text){
        log.append(text + "\n");
    }

    private void pullAnswerAll(String text){
        for (ClientWindow clientWindow: clientWindowList){
            clientWindow.pullAnswer(text);
        }
    }

    private void saveInLog(String text){
        try (FileWriter writer = new FileWriter(LOG_PATH, true)){
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String readLog(){
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH);){
            int letter;
            while ((letter = reader.read()) != -1){
                stringBuilder.append((char) letter);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean connectUser(ClientWindow clientWindow){
        if (!isServerWorking){
            return false;
        }
        clientWindowList.add(clientWindow);
        return true;
    }

    public void disconnectUser(ClientWindow clientWindow){
        clientWindowList.remove(clientWindow);
        if (clientWindow != null){
            clientWindow.disconnectFromServer();
        }
    }

}
