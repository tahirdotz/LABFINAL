import javax.swing.*;

public class Main{
    public static void main(String[] args) {
        Controller controller = new Controller(126, 108.5, 110);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Graphics graphics = new Graphics(controller);
                graphics.setVisible(true);
            }
        });
    }

}