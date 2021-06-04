package logic.persistence;

import logic.entities.Persona;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IPersonaPersistence {

    void save(Persona persona) throws IOException;

    List<Persona> read(String s) throws IOException, ClassNotFoundException;

    List<String> importPersonas(File file) throws IOException, Exception;
}
