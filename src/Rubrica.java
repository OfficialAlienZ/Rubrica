import java.util.Vector;

public class Rubrica {
    private Vector<Persona> rubrica = new Vector<>();

    public void aggiungiPersona(Persona persona) {
        rubrica.add(persona);
    }

    public void modificaPersona(int indice, Persona persona) {
        if (indice >= 0 && indice < rubrica.size()) {
            rubrica.set(indice, persona);
        } else {
            throw new IllegalArgumentException("Indice non valido");
        }
    }

    public void eliminaPersona(int indice) {
        if (indice >= 0 && indice < rubrica.size()) {
            rubrica.remove(indice);
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
}
