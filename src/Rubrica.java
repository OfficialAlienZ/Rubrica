import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Rubrica {
    private Vector<Persona> rubrica = new Vector<>();
    private static final String DIRECTORY_PATH = "informazioni";

    public Rubrica() {
        testCaricaFileTesto();
    }

    public static void testCaricaFileTesto() {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Caricamento dei file di testo dalla cartella informazioni:\n\n");

        File directory = new File("informazioni");
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        contentBuilder.append("Contenuto di ").append(file.getName()).append(":\n");
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                contentBuilder.append(line).append("\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        contentBuilder.append("\n\n");
                    }
                }
            }
        } else {
            contentBuilder.append("La cartella informazioni non esiste o non è una directory.");
        }

        // Mostra il contenuto nella finestra di dialogo
        JOptionPane.showMessageDialog(null, contentBuilder.toString(), "Contenuto dei file di testo",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void caricaDaFile() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            return; // Non c'è nulla da caricare
        }

        File[] files = directory.listFiles();
        if (files == null) {
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
                        rubrica.add(new Persona(nome, cognome, indirizzo, telefono, eta));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void aggiungiPersona(Persona persona) {
        rubrica.add(persona);
        System.out.println("salvataggio persona su file");
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

    public void modificaPersona(int indice, Persona persona) {
        if (indice >= 0 && indice < rubrica.size()) {
            rubrica.set(indice, persona);
            System.out.println("salvataggio persona su file");
            salvaPersonaSuFile(persona);
        } else {
            throw new IllegalArgumentException("Indice non valido");
        }
    }

    public void eliminaPersona(int indice) {
        if (indice >= 0 && indice < rubrica.size()) {
            Persona persona = rubrica.remove(indice);
            eliminaFilePersona(persona);
        } else {
            throw new IllegalArgumentException("Indice non valido");
        }
    }

    private void eliminaFilePersona(Persona persona) {
        String nomeFile = persona.getNome() + "-" + persona.getCognome() + ".txt";
        File file = new File(DIRECTORY_PATH, nomeFile);
        if (file.exists()) {
            file.delete();
        }
    }

    public Persona getPersona(int indice) {
        if (indice >= 0 && indice < rubrica.size()) {
            return rubrica.get(indice);
        } else {
            throw new IllegalArgumentException("Indice non valido");
        }
    }

    public int getSize() {
        return rubrica.size();
    }
}
