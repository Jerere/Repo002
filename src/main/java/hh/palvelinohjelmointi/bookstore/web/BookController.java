package hh.palvelinohjelmointi.bookstore.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.palvelinohjelmointi.bookstore.model.Book;
import hh.palvelinohjelmointi.bookstore.model.BookRepository;
import hh.palvelinohjelmointi.bookstore.model.GategoryRepository;

@Controller
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private GategoryRepository gategoryrepository;
	
	// LOG IN 
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}

	// FIND ALL BOOKS (REST)
	@RequestMapping("/books")
	public @ResponseBody List<Book> findBookRest() {
		return (List<Book>) bookRepository.findAll();
	}

	// FIND BOOK WITH ID (REST)
	@RequestMapping("/books/{id}")
	public @ResponseBody Optional<Book> findBookRest(@PathVariable("id") Long bookId) {
		return bookRepository.findById(bookId);
	}

	// SHOW BOOK LIST
	@RequestMapping(value = "/booklist", method = RequestMethod.GET)
	public String bookStoreController(Model model) {
		model.addAttribute("books", bookRepository.findAll());
		return "booklist";
	}

	// ADD BOOK
	@RequestMapping(value = "/add")
	public String addBook(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("gategories", gategoryrepository.findAll());
		return "addbook";
	}

	// SAVE BOOK
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Book book) {
		bookRepository.save(book);
		return "redirect:booklist";
	}

	// DELETE BOOK
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteBook(@PathVariable("id") Long bookId, Model model) {
		bookRepository.deleteById(bookId);
		return "redirect:../booklist";
	}

	// EDIT BOOK
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editBook(@PathVariable("id") Long bookId, Model model) {
		model.addAttribute("book", bookRepository.findById(bookId));
		model.addAttribute("gategories", gategoryrepository.findAll());
		return "editbook";
	}
}
