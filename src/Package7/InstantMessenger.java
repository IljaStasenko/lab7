package Package7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class InstantMessenger {
    private MainFrame frame;
    private DialogFrame dialogFrame;
    private String sender;
    private ChatStorage dataBase = new ChatStorage();

    public ChatStorage getDataBase() {
        return this.dataBase;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public InstantMessenger(MainFrame f) {
        this.frame = f;
        this.startServer();
    }

    public void sendMessage(User user, String message, DialogFrame frame) {
        this.dialogFrame = frame;

        try {
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Введите текст сообщения", "Ошибка", 0);
                return;
            }

            Socket socket = new Socket(user.getAddres(), this.frame.getServerPort());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(this.sender);
            out.writeUTF(message);
            out.writeUTF(user.getName());
            out.writeUTF("true");
            socket.close();
            frame.getTextAreaIn().append("You -> (" + user.getAddres() + "): " + message + "\n");
            frame.getTextAreaOut().setText("");
        } catch (UnknownHostException var6) {
            var6.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Не удалось отправить сообщение: адресат не найден", "Ошибка", 0);
        } catch (IOException var7) {
            var7.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Не удалось отправить сообщение", "Ошибка", 0);
        }

    }

    public void sendMessage(String destinationName, String message) {
        //try {
            if (destinationName.isEmpty()) {
                JOptionPane.showMessageDialog(this.frame, "Введите имя получателя", "Ошибка", 0);
                return;
            }

            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(this.frame, "Введите текст сообщения", "Ошибка", 0);
                return;
            }

            User temp = null;
            if (this.dataBase.getUser(destinationName) == null) {
                JOptionPane.showMessageDialog(this.frame, "Такого пользователя не существует", "Ошибка", 0);
                return;
            }

            temp = this.dataBase.getUser(destinationName);
            //Socket socket = new Socket(temp.getAddres(), this.frame.getServerPort());
            //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
           // out.writeUTF(this.sender);
            //out.writeUTF(message);
           // out.writeUTF(temp.getName());
            //out.writeUTF("false");
            //socket.close();
            this.frame.getTextAreaIncoming().append("You -> (" + temp.getAddres() + "): " + message + "\n");
            this.frame.getTextAreaOutgoing().setText("");
        //} catch (UnknownHostException var6) {
           // var6.printStackTrace();
           // JOptionPane.showMessageDialog(this.frame, "Не удалось отправить сообщение: адресат не найден", "Ошибка", 0);
       // } catch (IOException var7) {
           // var7.printStackTrace();
           // JOptionPane.showMessageDialog(this.frame, "Не удалось отправить сообщение", "Ошибка", 0);
        //}

    }

    private void startServer() {
        (new Thread(new Runnable() {
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(InstantMessenger.this.frame.getServerPort());

                    while(!Thread.interrupted()) {
                        Socket socket = serverSocket.accept();
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        String senderName = in.readUTF();
                        String message = in.readUTF();
                        String name = in.readUTF();
                        String flag = in.readUTF();
                        socket.close();
                        String address = ((InetSocketAddress)socket.getRemoteSocketAddress()).getAddress().getHostAddress();
                        if (flag.equals("true")) {
                            InstantMessenger.this.dialogFrame.getTextAreaIn().append(" ");
                        } else {
                           InstantMessenger.this.frame.getTextAreaIncoming().append(" ");

                        }

                    }
                } catch (IOException var9) {
                    var9.printStackTrace();
                    JOptionPane.showMessageDialog(InstantMessenger.this.frame, "Ошибка в работе сервера", "Ошибка", 0);
                }

            }
        })).start();
    }
}
