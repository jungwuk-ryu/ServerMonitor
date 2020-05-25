import net.kronos.rkon.core.Rcon;

import javax.swing.*;
import java.awt.*;
import java.net.ConnectException;

public class UpdateThread extends Thread{
    private final JFrame frame;
    private final JLabel label;
    private final String ip;
    private final String pw;
    private final int port;
    private final int coolTime;

    public UpdateThread(JFrame frame, JLabel label, String ip, int port, String pw, int coolTime){
        this.frame = frame;
        this.label = label;
        this.ip = ip;
        this.port = port;
        this.pw = pw;
        this.coolTime = coolTime;
        this.label.setFont(new Font("나눔스퀘어라운드", Font.PLAIN, 15));
    }

    @Override
    public void run() {
        while (true){
            String result = "";
            try {
                try {
                    Rcon rcon = new Rcon(this.ip, this.port, this.pw.getBytes());
                    String content = rcon.command("list");
                    String[] strings = content.split(":");
                    result = ("<html>" + strings[0] + "\n" + strings[1] + "</html>").replace(",", "님\n").replace("\n", "<p>");
                    this.frame.setTitle(this.ip + ":" + this.port + "  |  " + strings[0]);
                    //System.out.println(result);
                } catch (ConnectException e){
                    result = e.getMessage();
                } catch (Exception e) {
                    result = e.getMessage();
                    e.printStackTrace();
                } finally {
                    this.label.setText(result);
                }
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
