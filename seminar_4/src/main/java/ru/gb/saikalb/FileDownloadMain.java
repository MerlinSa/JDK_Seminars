package ru.gb.saikalb;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  TODO 4.
 *  Создайте приложение, которое скачивает файлы из интернета в несколько потоков.
 *  У пользователя должна быть возможность указать, сколько потоков использовать для загрузки.
 */

/**
 * Программа для загрузки файлов из интернета в несколько потоков.
 */
public class FileDownloadMain {

    /**
     * Точка входа в программу.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите URL-адреса файлов (через пробел): ");
        String[] urls = scanner.nextLine().split(" ");

        System.out.print("Введите количество потоков: ");
        int numThreads = scanner.nextInt();

        scanner.nextLine(); // Очистка буфера после ввода числа потоков

        System.out.print("Введите путь для сохранения файлов: ");
        String downloadPath = scanner.nextLine().trim();

        List<String> fileUrls = new ArrayList<>();
        for (String url : urls) {
            fileUrls.add(url.trim());
        }

        downloadFiles(fileUrls, numThreads, downloadPath);

        scanner.close();
    }

    /**
     * Загружает файлы из указанных URL-адресов с использованием нескольких потоков.
     *
     * @param fileUrls     Список URL-адресов файлов для загрузки.
     * @param numThreads   Количество потоков для параллельной загрузки.
     * @param downloadPath Путь для сохранения загруженных файлов.
     */
    private static void downloadFiles(List<String> fileUrls, int numThreads, String downloadPath) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (String fileUrl : fileUrls) {
            executor.execute(new FileDownload(fileUrl, downloadPath));
        }

        executor.shutdown();
    }
}
