# Java Junior (семинары)
## Урок 5. Клиент/Сервер своими руками
### Задание
1. Разработайте простой чат на основе сокетов как это было 
показано на самом семинаре. Ваше приложение должно включать 
в себя сервер, который принимает сообщения от клиентов 
и пересылает их всем участникам чата. 
(Вы можете просто переписать наше приложение с семинара, 
этого будет вполне достаточно)
2. * Подумайте, как организовать отправку ЛИЧНЫХ сообщений 
в контексте нашего чата, доработайте поддержку отправки 
личных сообщений, небольшую подсказку я дал в конце семинара.
* [Решение](Chat-Server/src/main/java/ru/ergakov/gb/ClientManager.java)

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