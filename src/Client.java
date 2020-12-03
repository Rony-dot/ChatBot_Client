import java.net.*;
import java.io.*;

public class Client {
    BufferedReader br;
    PrintWriter out;

    Socket socket;

    public Client() {
        try {
            System.out.println("Sending request");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connected to server");
            
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            
            startReading();
            startWriting();
            

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void startReading() {
        Runnable r1 = ()->{
            System.out.println("Reader started...");
            try {
            while(true) {
                    String  msg = br.readLine().trim();
                    if(msg.equals("exit")){
                        System.out.println("Server terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Server :"+msg);
                }
            }
            catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Connection closde");
            }

        };
        new Thread(r1).start();
    }
    private void startWriting() {
        Runnable r2=()-> {
            System.out.println("Writer started...");
            try {
            while(!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }

            }
            }catch (Exception e){
//                e.printStackTrace();
                System.out.println("Connection closed");
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("Client is Running...");
        new Client();

    }
}
