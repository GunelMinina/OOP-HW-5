import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Array;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Сервер запущен, ожидаем соединение....");
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключился к серверу!");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            while (true) {
                String clientRequest = dataInputStream.readUTF();
                if (clientRequest.equals("end")) break;

                String operators[]=clientRequest.split("[0-9]+");
                System.out.println(operators);
                String operands[]=clientRequest.split("[*/+-]");
                double agregate = Double.parseDouble(operands[0]);

                if (operators.length > 1) {
                    dataOutputStream.writeUTF("Могу вычислять только одно действие :(");
                }
                else {
                    if(operators[1].equals("+"))
                        agregate += Double.parseDouble(operands[1]);
                    else if (operators[1].equals("*")) {
                        agregate *= Double.parseDouble(operands[1]);
                    }
                    else if (operators[1].equals("/")) {
                        agregate /= Double.parseDouble(operands[1]);
                    }
                    else
                        agregate -= Double.parseDouble(operands[1]);

                    dataOutputStream.writeUTF("Результат: " + agregate);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}