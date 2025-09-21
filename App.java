import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Snake");
        int boardheight=600;
        int boardwidth=boardheight;

        frame.setSize(boardheight,boardwidth);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Snakegame snakegame = new Snakegame(boardwidth,boardheight);
        frame.add(snakegame);
        frame.pack();
        snakegame.requestFocus();
    }
}
