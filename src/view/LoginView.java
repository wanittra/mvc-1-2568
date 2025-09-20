package view;
import controller.MainController;
import java.awt.*;
import javax.swing.*;
public class LoginView {
    private final MainController controller;
    private JFrame frame;
    private JTextField userField;
    private JPasswordField passField;

    public LoginView(MainController c){ controller = c; init(); }

    private void init(){
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p = new JPanel(new GridLayout(4,1));
        userField = new JTextField();
        passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        p.add(new JLabel("Username:")); p.add(userField);
        p.add(new JLabel("Password:")); p.add(passField);
        frame.getContentPane().add(p, BorderLayout.CENTER);
        frame.getContentPane().add(loginBtn, BorderLayout.SOUTH);
        p.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        frame.setSize(500,300);
        loginBtn.addActionListener(e -> controller.handleLogin(userField.getText().trim(), new String(passField.getPassword())));
    }

    public void show(){ frame.setVisible(true); }
    public void close(){ frame.dispose(); }
    public void showError(String msg){ JOptionPane.showMessageDialog(frame, msg); }
}
