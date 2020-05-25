import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    public final static Color WHITE_COLOR = new Color(248, 248, 248);
    public JLabel label = new JLabel("정보를 입력해주세요.");

    private JPanel panel;
    private JButton alwaysOnButton;
    private JTextField fieldPORT;
    private JTextField fieldIP;
    private JPasswordField fieldPW;
    private JButton button = new JButton("확인");

    public MainWindow(){
        this.setTitle("서버 상태");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(WHITE_COLOR);
        this.setLayout(new FlowLayout());
        this.setSize(500,300);

        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        this.fieldIP = new JTextField();
        this.fieldPW = new JPasswordField();
        this.fieldPORT = new JTextField();
        this.panel.add(new JLabel("IP"));
        this.panel.add(this.fieldIP);
        this.panel.add(new JLabel("PORT"));
        this.panel.add(this.fieldPORT);
        this.panel.add(new JLabel("PASSWORD"));
        this.panel.add(this.fieldPW);
        this.button.setBackground(WHITE_COLOR);
        this.button.setBorder(new LineBorder(new Color(104, 202, 241)));
        this.button.setMargin(new Insets(10, 10, 10, 10));
        this.button.addActionListener(e -> {
            if(fieldIP.getText().isEmpty() || fieldPW.getPassword().length < 1) return;
            int port = 0;
            try{
                port = Integer.parseInt(this.fieldPORT.getText());
            }catch (NumberFormatException ignore){

            }
            if(port == 0) return;
            this.panel.setVisible(false);
            this.label.setText("연결중...");
            this.alwaysOnButton.setVisible(true);
            new UpdateThread(this, label, fieldIP.getText(), port, fieldPW.getText(), 7).start();
        });
        this.panel.add(button);
        JPanel subPanel = new JPanel();
        subPanel.setBackground(WHITE_COLOR);
        subPanel.setLayout(new BoxLayout(subPanel,BoxLayout.Y_AXIS));
        this.alwaysOnButton = new JButton("항상 위");
        this.alwaysOnButton.setFont(new Font("나눔스퀘어라운드", Font.PLAIN, 15));
        this.alwaysOnButton.setBackground(WHITE_COLOR);
        this.alwaysOnButton.setVisible(false);
        this.alwaysOnButton.addActionListener(e -> {
            this.setAlwaysOnTop(!this.isAlwaysOnTop());
        });
        subPanel.add(this.alwaysOnButton);
        subPanel.add(this.label);
        this.getContentPane().add(this.panel);
        this.getContentPane().add(subPanel);
        this.setVisible(true);
    }
}
