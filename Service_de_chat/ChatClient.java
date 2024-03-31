import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {


    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Connecté au serveur de chat");

            Thread receiveThread = new Thread(() -> receiveMessages(socket));
            receiveThread.start();

            OutputStream outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String message = scanner.nextLine();
                outputStream.write(message.getBytes());
                outputStream.flush();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveMessages(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();

            while (true) {
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                if (bytesRead == -1) {
                    System.out.println("Serveur déconnecté.");
                    break;
                }

                String message = new String(buffer, 0, bytesRead);
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

