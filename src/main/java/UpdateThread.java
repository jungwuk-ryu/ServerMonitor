import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;

public class UpdateThread extends Thread{
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'일 'HH:mm:ss'초'");
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

    public Rcon connect(){
        Rcon rcon = null;
        String message = "";
        int cnt = 0;
        while (true){
            try {
                rcon = new Rcon(this.ip, this.port, this.pw.getBytes());
            } catch (IOException | AuthenticationException e) {
                message = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                message = e.getMessage();
                e.printStackTrace();
            } finally {
                if(rcon != null) break;
                label.setText("(" + message + ") 재접속중..." + cnt++);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return rcon;
    }

    @Override
    public void run() {
        Rcon rcon = this.connect();
        while (true){
            String result = "";
            try {
                try {
                    if(rcon.getSocket().isClosed()){
                        rcon = connect();
                    }
                    String content = rcon.command("list");
                    if(!content.contains(":")) content += ":";
                    String[] strings = content.split(":");
                    result = ("<html> (" + SIMPLE_DATE_FORMAT.format(System.currentTimeMillis()) + " )\n" + strings[0] + "\n" + strings[1] + "</html>").replace(",", "님\n").replace("\n", "<p>");
                    this.frame.setTitle(this.ip + ":" + this.port + "  |  " + strings[0]);
                    //System.out.println(result);
                    rcon.disconnect();
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
