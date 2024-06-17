package ru.gb.saikalb;

public class Fork {
    public Action action; //поле action типа Action представляет собой enum(перечисление) с двумя возможными значениями:
    // поле action представляет состояние разветвления: использовалось оно или нет.
    public enum Action{
        used, notUsed
    }

    /**
     * Метод getAction возвращает текущее состояние Вилки Fork/
     * @return возвращает значение поля action.
     */
    public Action getAction(){
        return action;
    }
}
