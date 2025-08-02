package dao;

import domain.Book;

import java.util.Comparator;

public class ComparatorByAuthor implements Comparator<Book> {


    @Override
    public int compare(Book book1, Book book2) {
        return book1.getAuthor().getName().compareTo(book2.getAuthor().getName());
    }
}
