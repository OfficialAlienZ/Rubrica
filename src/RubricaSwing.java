import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class RubricaSwing extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    public Vector<Persona> rubrica = new Vector<>();

    public RubricaSwing() {
        setTitle("Rubrica");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        getContentPane().add(panel);

        // Creazione della tabella
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome");
        tableModel.addColumn("Cognome");
        tableModel.addColumn("Telefono");
        table = new JTable(tableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Pannello dei bottoni
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton nuovoButton = new JButton("Nuovo");
        JButton modificaButton = new JButton("Modifica");
        JButton eliminaButton = new JButton("Elimina");
        buttonPanel.add(nuovoButton);
        buttonPanel.add(modificaButton);
        buttonPanel.add(eliminaButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        nuovoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apriFinestraEditorPersona(null);
            }
        });

        modificaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    apriFinestraEditorPersona(rubrica.get(selectedRow));
                } else {
                    JOptionPane.showMessageDialog(RubricaSwing.this, "Seleziona una persona da modificare.");
                }
            }
        });

        eliminaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    rubrica.remove(selectedRow);
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(RubricaSwing.this, "Seleziona una persona da eliminare.");
                }
            }
        });
    }

    private void apriFinestraEditorPersona(Persona persona) {
        EditorPersonaFrame editorPersonaFrame = new EditorPersonaFrame(this, persona);
        editorPersonaFrame.setVisible(true);
    }

    public void aggiungiPersona(Persona persona) {
        rubrica.add(persona);
        Object[] rowData = {persona.getNome(), persona.getCognome(), persona.getTelefono()};
        tableModel.addRow(rowData);
    }

    public void modificaPersona(int rowIndex, Persona persona) {
        rubrica.set(rowIndex, persona);
        tableModel.setValueAt(persona.getNome(), rowIndex, 0);
        tableModel.setValueAt(persona.getCognome(), rowIndex, 1);
        tableModel.setValueAt(persona.getTelefono(), rowIndex, 2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RubricaSwing rubricaSwing = new RubricaSwing();
                rubricaSwing.setVisible(true);
            }
        });
    }
}
