package Package7;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;

public class MainFrame extends JFrame {
    private static final String FRAME_TITLE = "Клиент мгновенных сообщений";
    private static final int FRAME_MINIMUM_WIDTH = 500;
    private static final int FRAME_MINIMUM_HEIGHT = 500;
    private static final int FROM_FIELD_DEFAULT_COLUMNS = 10;
    private static final int TO_FIELD_DEFAULT_COLUMNS = 20;
    private static final int INCOMING_AREA_DEFAULT_ROWS = 10;
    private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;
    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 10;
    private static final int LARGE_GAP = 15;
    private static final int SERVER_PORT = 4567;
    private final JTextField textFieldFrom;
    private final JTextField textFieldTo;
    private final JTextArea textAreaIncoming;
    private final JTextArea textAreaOutgoing;
    private InstantMessenger messenger;

    public int getServerPort() {
        return 4567;
    }

    public JTextArea getTextAreaIncoming() {
        return this.textAreaIncoming;
    }

    public JTextArea getTextAreaOutgoing() {
        return this.textAreaOutgoing;
    }

    public InstantMessenger getMessenger() {
        return this.messenger;
    }

    public MainFrame() {
        super("Клиент мгновенных сообщений");
        this.setMinimumSize(new Dimension(500, 500));
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((kit.getScreenSize().width - this.getWidth()) / 2, (kit.getScreenSize().height - this.getHeight()) / 2);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu chatMenu = new JMenu("Меню");
        Action logInAction = new AbstractAction("Вход") {
            public void actionPerformed(ActionEvent arg0) {
                String value = JOptionPane.showInputDialog(MainFrame.this, "Введите имя для общения", "Вход", 3);
                if (MainFrame.this.messenger.getDataBase().getUser(value) == null) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Пользователя с таким именем не существует", "Ошибка", 0);
                } else {
                    MainFrame.this.messenger.setSender(value);
                }

            }
        };
        Action Register = new AbstractAction("Регистрация") {
            public void actionPerformed(ActionEvent arg0) {
                String value = JOptionPane.showInputDialog(MainFrame.this, "Введите имя", "Продолжить", 3);
                if (MainFrame.this.messenger.getDataBase().getUser(value) != null) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Пользователя с таким именем уже существует", "Ошибка", 0);
                } else {
                    String value1 = JOptionPane.showInputDialog(MainFrame.this, "Введите ip-адрес", "Зарегистрироваться", 3);
                    if (MainFrame.this.messenger.getDataBase().getUser(value) != null) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Пользователя с таким именем уже существует", "Ошибка", 0);
                    } else {
                        MainFrame.this.messenger.getDataBase().saveData(value, value1);
                    }
                }
            }
        };
        Action findUserAction = new AbstractAction("Поиск пользователя") {
            public void actionPerformed(ActionEvent arg0) {
                String value = JOptionPane.showInputDialog(MainFrame.this, "Введите имя для поиска", "Поиск пользователя", 3);
                if (MainFrame.this.messenger.getDataBase().getUser(value) == null) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Пользователя " + value + " не существует", "Ошибка", 0);
                } else {
                    User user = MainFrame.this.messenger.getDataBase().getUser(value);
                    JOptionPane.showMessageDialog(MainFrame.this, "Пользователь найден!\n " + user.getName() + " находится в базе данных.", "Пользователь " + user.getName(), 1);
                }
            }
        };
        Action PrintList = new AbstractAction("Список пользователей") {
            public void actionPerformed(ActionEvent arg0) {
                //System.out.println(MainFrame.this.messenger.getDataBase().CollectData());
                MainFrame.this.messenger.setSender("System");
                MainFrame.this.messenger.sendMessage("Admin", MainFrame.this.messenger.getDataBase().CollectData());
            }
        };
        Action openPrivateDialogAction = new AbstractAction("Личное сообщение") {
            public void actionPerformed(ActionEvent arg0) {
                String value = JOptionPane.showInputDialog(MainFrame.this, "Кому: ", "Поиск пользователя", 3);
                if (MainFrame.this.messenger.getDataBase().getUser(value) == null) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Пользователя " + value + " не существует", "Ошибка", 0);
                } else {
                    User user = MainFrame.this.messenger.getDataBase().getUser(value);
                    new DialogFrame(user, MainFrame.this);
                }
            }
        };
        chatMenu.add(logInAction);
        chatMenu.add(Register);
        chatMenu.add(PrintList);
        chatMenu.add(findUserAction);
        chatMenu.add(openPrivateDialogAction);
        menuBar.add(chatMenu);
        this.textAreaIncoming = new JTextArea(10, 0);
        this.textAreaIncoming.setEditable(false);
        JScrollPane scrollPaneIncoming = new JScrollPane(this.textAreaIncoming);
        JLabel labelFrom = new JLabel("От");
        JLabel labelTo = new JLabel("Получатель");
        this.textFieldFrom = new JTextField(10);
        this.textFieldTo = new JTextField(20);
        this.textAreaOutgoing = new JTextArea(5, 0);
        JScrollPane scrollPaneOutgoing = new JScrollPane(this.textAreaOutgoing);
        JPanel messagePanel = new JPanel();
        messagePanel.setBorder(BorderFactory.createTitledBorder("Сообщение"));
        JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.this.messenger.getSender() != null) {
                    MainFrame.this.messenger.sendMessage(MainFrame.this.textFieldTo.getText(), MainFrame.this.textAreaOutgoing.getText());
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Войдите в систему!", "Ошибка", 0);
                }
            }
        });
        GroupLayout layout2 = new GroupLayout(messagePanel);
        messagePanel.setLayout(layout2);
        layout2.setHorizontalGroup(layout2.createSequentialGroup().addContainerGap().addGroup(layout2.createParallelGroup(Alignment.TRAILING).addGroup(layout2.createSequentialGroup().addComponent(labelFrom).addGap(5).addComponent(this.textFieldFrom).addGap(15).addComponent(labelTo).addGap(5).addComponent(this.textFieldTo)).addComponent(scrollPaneOutgoing).addComponent(sendButton)).addContainerGap());
        layout2.setVerticalGroup(layout2.createSequentialGroup().addContainerGap().addGroup(layout2.createParallelGroup(Alignment.BASELINE).addComponent(labelFrom).addComponent(this.textFieldFrom).addComponent(labelTo).addComponent(this.textFieldTo)).addGap(10).addComponent(scrollPaneOutgoing).addGap(10).addComponent(sendButton).addContainerGap());
        GroupLayout layout1 = new GroupLayout(this.getContentPane());
        this.setLayout(layout1);
        layout1.setHorizontalGroup(layout1.createSequentialGroup().addContainerGap().addGroup(layout1.createParallelGroup().addComponent(scrollPaneIncoming).addComponent(messagePanel)).addContainerGap());
        layout1.setVerticalGroup(layout1.createSequentialGroup().addContainerGap().addComponent(scrollPaneIncoming).addGap(10).addComponent(messagePanel).addContainerGap());
        this.messenger = new InstantMessenger(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setDefaultCloseOperation(3);
                frame.setVisible(true);
            }
        });
    }
}
