package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@Controller
public class BooksController {

	@Autowired
	BookService bookService;

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public ModelAndView books() {
		ModelAndView model = new ModelAndView("books");
		model.addObject("bookList", bookService.findAllBooks());
		return model;
	}

	@RequestMapping(value = "/books/book", method = RequestMethod.GET)
	public ModelAndView book(@RequestParam("id") long id, ModelAndView model) {
		model = new ModelAndView("book");
		model.addObject("book", bookService.findBookById(id));
		return model;
	}

	@RequestMapping(value = "/books/add", method = RequestMethod.GET)
	public String adBook(Model model) {
		model.addAttribute("newBook", new BookTo());
		return "addBook";
	}

	@RequestMapping(value = "/greeting", method = RequestMethod.POST)
	public String greeting(@ModelAttribute("newBook") BookTo bookTo, Model model) {
		bookService.saveBook(bookTo);
		return "greeting";
	}

	@RequestMapping(value = "/books/delete", method = RequestMethod.GET)
	public ModelAndView deleting(@RequestParam("id") long id, ModelAndView model) {
		model = new ModelAndView("delete");
		model.addObject("book", bookService.findBookById(id));
		bookService.deleteBook(id);
		return model;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searching(ModelAndView model) {
		model = new ModelAndView("search");
		model.addObject("queryBook", new BookTo());
		return model;
	}

	@RequestMapping(value = "/search/searchResult", method = RequestMethod.POST)
	public ModelAndView searchingResult(@ModelAttribute("queryBook") BookTo bookTo, ModelAndView model) {
		model = new ModelAndView("searchResult");
		model.addObject("bookList", bookService.searchBooks(bookTo));
		return model;
	}

}
