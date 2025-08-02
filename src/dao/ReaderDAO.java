package dao;

import domain.Reader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReaderDAO {
    private Set<Reader> registeredReaders = new HashSet<>();

    public String saveReader(Reader reader) {
        if(!registeredReaders.add(reader)) {
            return "Leitor já cadastrado. Tente novamente!";
        }
        return "Leitor cadastrado com sucesso!";
    }

    public List<Reader> listReaders() {
        return new ArrayList<>(registeredReaders);
    }

    public void deleteReader(Reader reader) {
        registeredReaders.remove(reader);
    }

    public Reader findReaderById(long id) {
        for (Reader reader : this.registeredReaders) {
            if (reader.getReaderId() == id) {
                return reader;
            }
        }
        return null;
    }
}
