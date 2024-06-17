package ru.gb.saikalb;

import java.util.Iterator;
import java.util.List;

/**
 * class TableIterator реализует интерфейс Iterator.
 * Это Обобщенный класс, что означает, что он может работать с любыми типами данных, если это указано при создании экземпляра класса.
 * @param <T>
 */

public class TableIterator <T> implements Iterator<T> {
    List <T> table; //список элементов обобщенного типа
    int index; //индекс указывающий текущую позицию

    public TableIterator (List <T> table){ // конструктор класса
        this.table = table;
        index = 0;
    }

    /**
     * Метод hasNext() проверяет, осталось ли в списке больше элементов, сравнивая индекс с размером таблицы.
     * Если индекс меньше размера таблицы, это означает, что осталось больше элементов,
     * поэтому метод возвращает значение true. В противном случае он возвращает значение false.
     * @return
     */
    @Override
    public boolean hasNext() {
        return index<table.size();
    }

    /**
     * Метод next() извлекает следующий элемент в списке, вызывая метод get() для списка таблиц с текущим индексом,
     * а затем увеличивает индекс на 1. постинкремент
     * @return
     */
    @Override
    public T next() {
        return table.get(index++);
    }
}