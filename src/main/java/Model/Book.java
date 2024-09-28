package Model;

import Controller.Book;
import Controller.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // Retrieve all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Retrieve books by filters
    @GetMapping("/search")
    public List<Book> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Double rating) {

        if (title != null) {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (author != null) {
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        } else if (year != null) {
            return bookRepository.findByYear(year);
        } else if (rating != null) {
            return bookRepository.findByRatingGreaterThanEqual(rating);
        } else {
            return bookRepository.findAll();
        }
    }

    // Rate a book
    @PostMapping("/{id}/rate")
    public ResponseEntity<Book> rateBook(@PathVariable Long id, @RequestParam int rating) {
        Book book = (Book) bookRepository.findById(id).orElseThrow();
        // Example of averaging ratings
        String string;
        string = book.toString((book.getRating() + rating) >> 1);
        bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    // Add a new book
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // Update a book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) throws InterruptedException {
        Book book = (Book) bookRepository.findById(id).orElseThrow();
        book.wait(bookDetails.getTitle(bookDetails.getAuthor()));
        book.getTitle(bookDetails.getAuthor());
        book.getClass(bookDetails.getYear());
        book.setRating(bookDetails.getRating());
        book.setGenre(bookDetails.getGenre());

        Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok(updatedBook);
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Object book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete((Book) book);
        return ResponseEntity.noContent().build();
    }
}