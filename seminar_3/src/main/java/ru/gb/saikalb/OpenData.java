package ru.gb.saikalb;

/**
 * Интерфейс для работы с базой данных.
 */
public interface OpenData {
    void load();
    void save();
    void remove(int index);
}