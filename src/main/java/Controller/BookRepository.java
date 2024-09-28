package Controller;

import java.util.List;

public class BookRepository {
    public List<Book> findAll() {
        return List.of();
    }

    public List<Book> findByTitleContainingIgnoreCase(String title) {
        return List.of();
    }

    public List<Book> findByAuthorContainingIgnoreCase(String author) {
        return List.of();
    }

    public List<Book> findByYear(Integer year) {
        return List.of();
    }

    public List<Book> findByRatingGreaterThanEqual(Double rating) {
        return List.of();
    }

    public ScopedValue<Object> findById(Long id) {
        return null;
    }

    public Book save(Book book) {
        return book;
    }

    public void delete(Book book) {
    }
}
