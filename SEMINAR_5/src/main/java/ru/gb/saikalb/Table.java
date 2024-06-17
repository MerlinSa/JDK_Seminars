package ru.gb.saikalb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Класс Table реализует интерфейс Iterable, Это означает, что можно выполнить итерацию по экземпляру класса Table,
 *  и каждый элемент в итерации будет иметь тип Fork.
 */

public class Table implements Iterable<Fork> {
    private List<Fork> table; // В этом поле будет храниться коллекция объектов типа Fork

    /**
     * Конструктор класса Tablr
     */
    public Table() {
        this.table = new ArrayList<>(); //инициализирует поле таблицы с помощью нового ArrayList<Fork>.
    }

    /**
     * Этот метод возвращает объект Fork с указанным индексом в таблице.
     * @param index указанный индекс в таблице
     * @return объект Fork по индексу
     */
    public Fork get(int index) {
        return table.get(index);
    }

    /**
     * Этот метод добавляет в таблицу новый объект Fork.
     * @param obj
     */

    public void add(Fork obj) {
        table.add(obj);
    }

    /**
     * Метод iterator() переопределен, возвращает новый экземпляр TableIterator<Fork>,
     * который будет выполнять итерацию по полю таблицы.
     * @return
     */
    @Override
    public Iterator<Fork> iterator() {
        return new TableIterator<>(table);
    }
}
