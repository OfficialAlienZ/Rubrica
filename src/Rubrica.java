import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Vector;

public class Rubrica {
    private Vector<Persona> rubrica = new Vector<>();
    private static final String FILE_PATH = "informazioni.txt";

    public Rubrica() {
        System.out.println("creazione rubrica");
        caricaDaFile();
    }

    private void caricaDaFile() {
        System.out.println("loading file");
        System.out.flush();
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    String nome = parts[0];
                    String cognome = parts[1];
                    String indirizzo = parts[2];
                    String telefono = parts[3];
                    int eta = Integer.parseInt(parts[4]);
                    rubrica.add(new Persona(nome, cognome, indirizzo, telefono, eta));
                }
            }
        } catch (FileNotFoundException e) {
            // Il file non esiste, non fare nulla
        }
    }

    public void aggiungiPersona(Persona persona) {
        rubrica.add(persona);
        salvaSuFile();

    }

    public void modificaPersona(int indice, Persona persona) {
        if (indice >= 0 && indice < rubrica.size()) {
            rubrica.set(indice, persona);
            salvaSuFile();
        } else {
            throw new IllegalArgumentException("Indice non valido");
        }
    }

    public void eliminaPersona(int indice) {
        if (indice >= 0 && indice < rubrica.size()) {
            rubrica.remove(indice);
            salvaSuFile();
        } else {
            throw new IllegalArgumentException("Indice non valido");
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

    private void salvaSuFile() {
        System.out.println("writing to file");
        System.out.flush();
        try (PrintStream output = new PrintStream(new FileOutputStream(FILE_PATH))) {
            for (Persona persona : rubrica) {
                output.println(persona.getNome() + ";" + persona.getCognome() + ";" +
                        persona.getIndirizzo() + ";" + persona.getTelefono() + ";" +
                        persona.getEta());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
