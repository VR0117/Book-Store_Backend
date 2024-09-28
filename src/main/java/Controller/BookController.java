package Controller;

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
    public List<Controller.Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Retrieve books by filters (title, author, year, rating)
    @GetMapping("/search")
    public List<Controller.Book> searchBooks(
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
        Controller.Book book = (Controller.Book) bookRepository.findById(id).orElseThrow();

        // Calculate the new average rating
        double newRating;
        newRating = (rating + book.getRating()) / 2.0;
        book.setRating(newRating);

        bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    // Add a new book
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // Update a book's details
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow();

        // Update the book's details
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setYear(bookDetails.getYear());
        book.setRating(bookDetails.getRating());
        book.setGenre(bookDetails.getGenre());

        Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok(updatedBook);
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
        return ResponseEntity.noContent().build();
    }
}