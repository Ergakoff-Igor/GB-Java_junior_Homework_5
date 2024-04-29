# Простой чат на основе сокетов
* [Запуск сервера](Chat-Server/src/main/java/ru/ergakov/gb/Program.java)
* [Запуск клиента](Chat-Client/src/main/java/ru/ergakov/gb/Program.java)

Добавлена функции отправки личных сообщений

Макет отправления личного сообщения:

@ + Никнейм + пробел + тело сообщения

```java
public void run() {
        String massageFromClient;

        while (socket.isConnected()) {
            try {
                // Чтение данных
                massageFromClient = bufferedReader.readLine();
                if (massageFromClient == null) {
                    // для  macOS
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                }

                String[] massageFromClientArray = massageFromClient.split(" ", 3);

                if(massageFromClientArray[1].charAt(0) == '@'){
                    // Отправка личных данных
                    String name = massageFromClientArray[1].substring( 1);
                    String massage = massageFromClientArray[0] + " " + massageFromClientArray[2];
                    System.out.println("name:" + name);
                    System.out.println("massage" + massage);
                    privateMessage(massage, name);
                } else {
                    // Отправка данных всем слушателям
                    broadcastMessage(massageFromClient);
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }

    }
```
```java
    /**
     * Отправка личного сообщения
     *
     * @param message сообщение
     * @param findName искомое имя клиента
     */
    private void privateMessage(String message, String findName) {
        for (ClientManager client : clients) {
            try {
                if (client.name.equals(findName) && message != null) {
                    client.bufferedWriter.write(message);
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                }
            }
            catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }
```
