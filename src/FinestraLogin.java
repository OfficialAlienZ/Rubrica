import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class FinestraLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public FinestraLogin() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Creazione della barra degli strumenti
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); // Impedisce il trascinamento della barra degli strumenti
        add(toolBar, BorderLayout.NORTH);

        // Creazione dei bottoni e aggiunta alla barra degli strumenti
        ImageIcon loginIcon = new ImageIcon("login.png"); // Immagine per il pulsante LOGIN
        JButton loginButton = new JButton("LOGIN", loginIcon);
        loginButton.setPreferredSize(new Dimension(100, 30)); // Imposta la dimensione preferita del pulsante
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
        toolBar.add(loginButton);

        // Aggiunta spazio vuoto per allineare i bottoni a destra della barra degli strumenti
        toolBar.add(Box.createHorizontalGlue());

        // Creazione dei campi di testo per username e password
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        panel.add(usernameLabel);
        panel.add(usernameField);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        panel.add(passwordLabel);
        panel.add(passwordField);
        add(panel, BorderLayout.CENTER);
    }

    private boolean verificaCredenziali(String username, String password) {
        // Implementazione della verifica delle credenziali
        // In questo esempio, verifichiamo solo se username e password sono entrambi "admin"
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
