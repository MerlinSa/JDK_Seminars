package ru.gb.saikalb;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Задача для загрузки файла из интернета в отдельном потоке.
 */
public class FileDownload implements Runnable {
    private String fileUrl;
    private String downloadPath;

    /**
     * Конструктор для создания задачи загрузки файла.
     *
     * @param fileUrl      URL-адрес файла для загрузки.
     * @param downloadPath Путь для сохранения загруженного файла.
     */
    public FileDownload(String fileUrl, String downloadPath) {
        this.fileUrl = fileUrl;
        this.downloadPath = downloadPath;
    }

    /**
     * Запускает выполнение задачи загрузки файла.
     */
    @Override
    public void run() {
        try {
            downloadFile(fileUrl, downloadPath);
            System.out.println("Загружено: " + fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загружает файл из указанного URL-адреса и сохраняет его по указанному пути.
     *
     * @param fileUrl      URL-адрес файла для загрузки.
     * @param downloadPath Путь для сохранения загруженного файла.
     * @throws IOException Если возникает ошибка ввода-вывода в процессе загрузки файла.
     */
    private void downloadFile(String fileUrl, String downloadPath) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Добавим проверку на успешный HTTP-статус перед чтением данных
        int responseCode = connection.getResponseCode();
        System.out.println("HTTP статус: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream in = connection.getInputStream();
                 FileOutputStream fos = new FileOutputStream(getFileName(url, downloadPath))) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
        } else {
            throw new IOException("Не удалось загрузить файл. HTTP-статус: " + responseCode);
        }

        connection.disconnect();
    }

    /**
     * Возвращает имя файла на основе URL-адреса и пути для сохранения.
     *
     * @param url          URL-адрес файла.
     * @param downloadPath Путь для сохранения файла.
     * @return Имя файла.
     */
    private String getFileName(URL url, String downloadPath) {
        String[] parts = url.getFile().split("/");
        return downloadPath + "/" + parts[parts.length - 1];
    }
}
