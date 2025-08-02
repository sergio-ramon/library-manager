package dao;

import domain.Author;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthorDAO {
    private Set<Author> registeredAuthors = new HashSet<>();

    public String saveAuthor(Author author) {
        if (!registeredAuthors.add(author)) {
            return "Autor já cadastrado! Verifique o nome e tente novamente!";
        }
        return "Autor cadastrado com sucesso!";
    }

    public List<Author> listAuthors() {
        return new ArrayList<>(registeredAuthors);
    }

    public void deleteAuthors(Author author) {
        registeredAuthors.removeIf(a -> a.equals(author));
    }

    public Author findAuthorById(long id) {
        for (Author author : registeredAuthors) {
            if (author.getAuthorId() == id) {
                return author;
            }
        }
        return null;
    }
}
