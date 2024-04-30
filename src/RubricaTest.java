import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RubricaTest {
    public static void main(String[] args) {
        // testAggiungiPersona();
        testModificaPersona();
        // testEliminaPersona();
        testCaricaFileTesto();

    }

    public static void testAggiungiPersona() {
        Rubrica rubrica = new Rubrica();

        Persona persona = new Persona("Mario", "Rossi", "Via Roma 1", "123456789", 30);
        rubrica.aggiungiPersona(persona);

        // Verifica se la persona è stata aggiunta correttamente
        assert rubrica.getSize() == 1;
        assert rubrica.getPersona(0).equals(persona);
    }

    public static void testModificaPersona() {
        Rubrica rubrica = new Rubrica();

        // Aggiungi una persona
        Persona persona = new Persona("Mario", "Rossi", "Via Roma 1", "123456789", 30);
        rubrica.aggiungiPersona(persona);

        // Modifica la persona
        Persona personaModificata = new Persona("Luigi", "Verdi", "Via Napoli 2", "987654321", 40);
        rubrica.modificaPersona(0, personaModificata);

        // Verifica se la persona è stata modificata correttamente
        assert rubrica.getSize() == 1;
        assert rubrica.getPersona(0).equals(personaModificata);
    }

    public static void testEliminaPersona() {
        Rubrica rubrica = new Rubrica();

        // Aggiungi una persona
        Persona persona = new Persona("Mario", "Rossi", "Via Roma 1", "123456789", 30);
        rubrica.aggiungiPersona(persona);

        // Elimina la persona
        rubrica.eliminaPersona(0);

        // Verifica se la persona è stata eliminata correttamente
        assert rubrica.getSize() == 0;
    }

    // Metodo di test per caricare e mostrare i file di testo
    public static void testCaricaFileTesto() {
        System.out.println("Caricamento dei file di testo dalla cartella informazioni:");

        File directory = new File("informazioni");
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        System.out.println("Contenuto di " + file.getName() + ":");
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            System.out.println("La cartella informazioni non esiste o non è una directory.");
        }
    }

}
