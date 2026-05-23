import java.awt.*;
import java.awt.event.*;

class AWTCalculator extends Frame implements ActionListener {

    TextField text;
    Panel panel;
    String button[] = {"7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", "AC", "0", "/", "=" };
    Button btn[] = new Button[16];
    int A = 0; int B = 0; int output = 0;
    char opt;

    public AWTCalculator () {

        Font f = new Font("Arial", Font.ITALIC, 20);
        text = new TextField(10);
        text.setFont(f);

        panel = new Panel();
        add(text, "North");
        add(panel, "Center");
        panel.setLayout(new GridLayout(4,4));

        for (int i = 0; i < 16; i++) {
            btn[i] = new Button(button[i]);
            btn[i].setFont(f);
            btn[i].addActionListener(this);
            panel.add(btn[i]);
        }

        addWindowListener(new WindowAdapter() { public void WindowClosing(WindowEvent we) { System.exit(0); } } );
    }

    public void actionPerformed (ActionEvent ae) {

        String str = ae.getActionCommand();

        switch (str) {
            case "+" :
                opt = '+';
                A = Integer.parseInt(text.getText());
                text.setText("");
                break;
            case "-" :
                opt = '-';
                A = Integer.parseInt(text.getText());
                text.setText("");
                break;    
            case "*" :
                opt = '*';
                A = Integer.parseInt(text.getText());
                text.setText("");
                break;
            case "/" :
                opt = '/';
                A = Integer.parseInt(text.getText());
                text.setText("");
                break;
            case "=" :

                B = Integer.parseInt(text.getText());
                switch (opt) {

                    case '+' :
                    output = A + B;
                    break;
                    case '-' :
                    output = A - B;
                    break;
                    case '*' :
                    output = A * B;
                    break;
                    case '/' :
                    output = A / B;
                    break;
                    
                }

                text.setText(output + "");
                output = 0;

                break;
            case "AC" :
                text.setText("");
                A = B = output = 0;
                break;
            default :
            text.setText(text.getText() + str);
            break;
        }
    }

    public static void main(String kean[]) {
        AWTCalculator awtCalculator = new AWTCalculator();
        awtCalculator.setTitle("GUI - AWT Calculator");
        awtCalculator.setSize(300, 400);
        awtCalculator.setBackground(Color.YELLOW);
        awtCalculator.setForeground(Color.MAGENTA);
        awtCalculator.setVisible(true);
    }

}