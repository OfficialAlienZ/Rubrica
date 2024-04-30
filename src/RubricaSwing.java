import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class RubricaSwing extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    public Vector<Persona> rubrica = new Vector<>();
    private static final String DIRECTORY_PATH = "informazioni";

    public RubricaSwing() {
        // Creazione della tabella
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome");
        tableModel.addColumn("Cognome");
        tableModel.addColumn("Telefono");

        caricaDaFile();
        setTitle("Rubrica");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creazione della barra degli strumenti
        // Creazione della barra degli strumenti
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); // Impedisce il trascinamento della barra degli strumenti
        add(toolBar, BorderLayout.NORTH);

        // Creazione dei bottoni e aggiunta alla barra degli strumenti
        ImageIcon nuovoIcon = new ImageIcon(getClass().getResource("nuovo.png")); // Immagine per il pulsante Nuovo
        Image scaledNuovoImage = nuovoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Ridimensiona
                                                                                                     // l'immagine
        nuovoIcon.setImage(scaledNuovoImage); // Imposta l'immagine ridimensionata nel pulsante
        JButton nuovoButton = new JButton("Nuovo", nuovoIcon);
        nuovoButton.setPreferredSize(new Dimension(100, 30)); // Imposta la dimensione preferita del pulsante

        nuovoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apriFinestraEditorPersona(null);
            }
        });
        toolBar.add(nuovoButton);

        ImageIcon modificaIcon = new ImageIcon(getClass().getResource("modifica.png")); // Immagine per il pulsante Modifica
        Image scaledModificaImage = modificaIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Ridimensiona
                                                                                                           // l'immagine
        modificaIcon.setImage(scaledModificaImage); // Imposta l'immagine ridimensionata nel pulsante
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
        ImageIcon eliminaIcon = new ImageIcon(getClass().getResource("elimina.png")); // Immagine per il pulsante Elimina
        Image scaledEliminaImage = eliminaIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Ridimensiona
                                                                                                         // l'immagine
        eliminaIcon.setImage(scaledEliminaImage); // Imposta l'immagine ridimensionata nel pulsante
        JButton eliminaButton = new JButton("Elimina", eliminaIcon);
        eliminaButton.setPreferredSize(new Dimension(100, 30)); // Imposta la dimensione preferita del pulsante

        eliminaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                    Persona personaDaEliminare = rubrica.get(selectedRow);
                    eliminaPersona(personaDaEliminare);
                    // rubrica.remove(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(RubricaSwing.this, "Seleziona una persona da eliminare.");
                }
            }
        });

        // Aggiunta spazio vuoto per allineare i bottoni a destra della barra degli
        // strumenti
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(eliminaButton);

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void apriFinestraEditorPersona(Persona persona) {
        EditorPersonaFrame editorPersonaFrame = new EditorPersonaFrame(this, persona);
        editorPersonaFrame.setVisible(true);
    }

    private void caricaDaFile() {
        System.out.println("caricamento file");
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            System.out.println("cartella mancante");
            return; // Non c'è nulla da caricare
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("nessun file da caricare");
            return; // Non ci sono file nella directory
        }

        for (File file : files) {
            if (file.isFile()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();
                    String[] parts = line.split(";");
                    if (parts.length == 5) {
                        String nome = parts[0];
                        String cognome = parts[1];
                        String indirizzo = parts[2];
                        String telefono = parts[3];
                        int eta = Integer.parseInt(parts[4]);
                        Persona persona = new Persona(nome, cognome, indirizzo, telefono, eta);
                        caricaPersona(persona);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void caricaPersona(Persona persona) {
        rubrica.add(persona);
        Object[] rowData = { persona.getNome(), persona.getCognome(), persona.getTelefono() };
        tableModel.addRow(rowData);
    }

    public void aggiungiPersona(Persona persona) {
        rubrica.add(persona);
        Object[] rowData = { persona.getNome(), persona.getCognome(), persona.getTelefono() };
        tableModel.addRow(rowData);
        salvaPersonaSuFile(persona);
    }

    private void salvaPersonaSuFile(Persona persona) {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String nomeFile = persona.getNome() + "-" + persona.getCognome() + ".txt";
        File file = new File(DIRECTORY_PATH, nomeFile);
        int counter = 1;
        while (file.exists()) {
            nomeFile = persona.getNome() + "-" + persona.getCognome() + "-" + counter + ".txt";
            file = new File(DIRECTORY_PATH, nomeFile);
            counter++;
        }

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(persona.getNome() + ";" + persona.getCognome() + ";" +
                    persona.getIndirizzo() + ";" + persona.getTelefono() + ";" +
                    persona.getEta());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void modificaPersona(int rowIndex, Persona persona) {
        if (rowIndex >= 0 && rowIndex < rubrica.size()) {
            Persona oldPersona = rubrica.get(rowIndex);
            eliminaFilePersona(oldPersona);
            rubrica.set(rowIndex, persona);
            tableModel.setValueAt(persona.getNome(), rowIndex, 0);
            tableModel.setValueAt(persona.getCognome(), rowIndex, 1);
            tableModel.setValueAt(persona.getTelefono(), rowIndex, 2);
            salvaPersonaSuFile(persona);
        } else {
            throw new IllegalArgumentException("Indice non valido");
        }
    }

    public void eliminaPersona(Persona persona) {
        if (rubrica.contains(persona)) {
            rubrica.remove(persona);
            eliminaFilePersona(persona);
        } else {
            System.err.println("Persona non trovata nella rubrica");
        }
    }

    private void eliminaFilePersona(Persona persona) {
        String nomeFile = persona.getNome() + "-" + persona.getCognome() + ".txt";
        File file = new File(DIRECTORY_PATH, nomeFile);
        if (file.exists()) {
            file.delete();
        }
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new Runnable() {
    // public void run() {
    // RubricaSwing rubricaSwing = new RubricaSwing();
    // rubricaSwing.setVisible(true);
    // }
    // });
    // }
}
