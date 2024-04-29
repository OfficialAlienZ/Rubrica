import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinestraLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public FinestraLogin() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 1));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        panel.add(usernameLabel);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton loginButton = new JButton("LOGIN");
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (verificaCredenziali(username, password)) {
                    apriFinestraPrincipale();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(FinestraLogin.this, "Login errato.");
                }
            }
        });
    }

    private boolean verificaCredenziali(String username, String password) {
        // Implementazione della verifica delle credenziali
        // In questo esempio, verifichiamo solo se username e password sono entrambi
        // "admin"
        return username.equals("admin") && password.equals("admin");
    }

    private void apriFinestraPrincipale() {
        RubricaSwing rubricaSwing = new RubricaSwing();
        rubricaSwing.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FinestraLogin finestraLogin = new FinestraLogin();
                finestraLogin.setVisible(true);
            }
        });
    }
}
