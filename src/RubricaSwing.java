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

        // Creazione della barra degli strumenti
        // Creazione della barra degli strumenti
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); // Impedisce il trascinamento della barra degli strumenti
        add(toolBar, BorderLayout.NORTH);

        // Creazione dei bottoni e aggiunta alla barra degli strumenti
        ImageIcon nuovoIcon = new ImageIcon("nuovo.png"); // Immagine per il pulsante Nuovo
        JButton nuovoButton = new JButton("Nuovo", nuovoIcon);
        nuovoButton.setPreferredSize(new Dimension(100, 30)); // Imposta la dimensione preferita del pulsante
        nuovoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apriFinestraEditorPersona(null);
            }
        });
        toolBar.add(nuovoButton);

        ImageIcon modificaIcon = new ImageIcon("modifica.png"); // Immagine per il pulsante Modifica
        JButton modificaButton = new JButton("Modifica", modificaIcon);
        modificaButton.setPreferredSize(new Dimension(100, 30)); // Imposta la dimensione preferita del pulsante

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
        toolBar.add(modificaButton);

        ImageIcon eliminaIcon = new ImageIcon("elimina.png"); // Immagine per il pulsante Elimina
        JButton eliminaButton = new JButton("Elimina", eliminaIcon);
        eliminaButton.setPreferredSize(new Dimension(100, 30)); // Imposta la dimensione preferita del pulsante

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
        // Aggiunta spazio vuoto per allineare i bottoni a destra della barra degli
        // strumenti
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(eliminaButton);

        // Creazione della tabella
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome");
        tableModel.addColumn("Cognome");
        tableModel.addColumn("Telefono");
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void apriFinestraEditorPersona(Persona persona) {
        EditorPersonaFrame editorPersonaFrame = new EditorPersonaFrame(this, persona);
        editorPersonaFrame.setVisible(true);
    }

    public void aggiungiPersona(Persona persona) {
        rubrica.add(persona);
        Object[] rowData = { persona.getNome(), persona.getCognome(), persona.getTelefono() };
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
