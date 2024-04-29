import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorPersonaFrame extends JFrame {
    private JTextField nomeField = new JTextField(20);
    private JTextField cognomeField = new JTextField(20);
    private JTextField indirizzoField = new JTextField(20);
    private JTextField telefonoField = new JTextField(20);
    private JTextField etaField = new JTextField(20);

    public EditorPersonaFrame(RubricaSwing parent, Persona persona) {
        setTitle("Editor Persona");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Cognome:"));
        panel.add(cognomeField);
        panel.add(new JLabel("Indirizzo:"));
        panel.add(indirizzoField);
        panel.add(new JLabel("Telefono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("Età:"));
        panel.add(etaField);
        getContentPane().add(panel);

        JButton salvaButton = new JButton("Salva");
        JButton annullaButton = new JButton("Annulla");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(salvaButton);
        buttonPanel.add(annullaButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        salvaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String indirizzo = indirizzoField.getText();
                String telefono = telefonoField.getText();
                int eta = Integer.parseInt(etaField.getText());

                if (persona == null) {
                    parent.aggiungiPersona(new Persona(nome, cognome, indirizzo, telefono, eta));
                } else {
                    int rowIndex = parent.rubrica.indexOf(persona);
                    parent.modificaPersona(rowIndex, new Persona(nome, cognome, indirizzo, telefono, eta));
                }

                dispose();
            }
        });

        annullaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        if (persona != null) {
            nomeField.setText(persona.getNome());
            cognomeField.setText(persona.getCognome());
            // Indirizzo non viene utilizzato nell'esempio
            telefonoField.setText(persona.getTelefono());
            // Età non viene utilizzata nell'esempio
        }
    }
}
