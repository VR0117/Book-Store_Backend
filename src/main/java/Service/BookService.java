package Service;

import Controller.Book;
import Controller.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServices bookService;

    public BookServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindBooksByTitle() {
        // Mock data
        Book book1 = new Book(1L, "Test Title", "Author", 2020, 4.5, "Fiction");
        when(bookRepository.findByTitleContainingIgnoreCase("Test")).thenReturn(Arrays.asList(book1));

        // Test the service
        List<Book> books = bookService.findBooksByTitle("Test");
        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
    }
}
