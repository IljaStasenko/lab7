package Package7;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;

public class DialogFrame extends JFrame {
    private static final int FROM_FIELD_DEFAULT_COLUMNS = 10;
    private static final int TO_FIELD_DEFAULT_COLUMNS = 20;
    private static final int INCOMING_AREA_DEFAULT_ROWS = 10;
    private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;
    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 10;
    private static final int LARGE_GAP = 15;
    private static final int WIDTH = 250;
    private static final int HEIGHT = 400;
    private InstantMessenger messenger;
    private final JTextArea textAreaIn;
    private final JTextArea textAreaOut;

    public JTextArea getTextAreaIn() {
        return this.textAreaIn;
    }

    public JTextArea getTextAreaOut() {
        return this.textAreaOut;
    }

    public DialogFrame(final User user, MainFrame frame) {
        this.messenger = frame.getMessenger();
        this.setTitle("Беседа с " + user.getName());
        this.setMinimumSize(new Dimension(250, 400));
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((kit.getScreenSize().width - this.getWidth()) / 2, (kit.getScreenSize().height - this.getHeight()) / 2);
        this.textAreaIn = new JTextArea(10, 0);
        this.textAreaIn.setEditable(false);
        JScrollPane scrollPaneIncoming = new JScrollPane(this.textAreaIn);
        this.textAreaOut = new JTextArea(5, 0);
        JScrollPane scrollPaneOutgoing = new JScrollPane(this.textAreaOut);
        JPanel messagePanel = new JPanel();
        messagePanel.setBorder(BorderFactory.createTitledBorder("Сообщение"));
        JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (DialogFrame.this.messenger.getSender() != null) {
                    DialogFrame.this.messenger.sendMessage(user, DialogFrame.this.textAreaOut.getText(), DialogFrame.this);
                } else {
                    JOptionPane.showMessageDialog(DialogFrame.this, "Войдите в систему!", "Ошибка", 0);
                }
            }
        });
        GroupLayout layout2 = new GroupLayout(messagePanel);
        messagePanel.setLayout(layout2);
        layout2.setHorizontalGroup(layout2.createSequentialGroup().addContainerGap().addGroup(layout2.createParallelGroup(Alignment.TRAILING).addGroup(layout2.createSequentialGroup()).addComponent(scrollPaneOutgoing).addComponent(sendButton)).addContainerGap());
        layout2.setVerticalGroup(layout2.createSequentialGroup().addContainerGap().addGroup(layout2.createParallelGroup(Alignment.BASELINE)).addGap(10).addComponent(scrollPaneOutgoing).addGap(10).addComponent(sendButton).addContainerGap());
        GroupLayout layout1 = new GroupLayout(this.getContentPane());
        this.setLayout(layout1);
        layout1.setHorizontalGroup(layout1.createSequentialGroup().addContainerGap().addGroup(layout1.createParallelGroup().addComponent(scrollPaneIncoming).addComponent(messagePanel)).addContainerGap());
        layout1.setVerticalGroup(layout1.createSequentialGroup().addContainerGap().addComponent(scrollPaneIncoming).addGap(10).addComponent(messagePanel).addContainerGap());
        this.setVisible(true);
    }
}
