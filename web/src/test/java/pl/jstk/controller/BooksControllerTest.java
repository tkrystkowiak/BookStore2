package pl.jstk.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.jstk.enumerations.BookStatus;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BooksControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	BooksController booksControler;

	@Mock
	private BookService bookServiceMock;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(booksControler).setViewResolvers(new StandaloneMvcTestViewResolver())
				.build();
	}

	@Test
	public void testBooksPageGet() throws Exception {
		// given
		List<BookTo> testList = new ArrayList<BookTo>();
		testList.add(new BookTo(1L, "Albert Camus", "Obcy", BookStatus.FREE));
		when(bookServiceMock.findAllBooks()).thenReturn(testList);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("books"));
	}

	@Test
	public void testBooksPagePost() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(post("/books"));
		// then
		resultActions.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void testBookDetailPageGetWhenCorrectId() throws Exception {
		// given
		BookTo detailedBook = new BookTo(1L, "Albert Camus", "Obcy", BookStatus.FREE);
		when(bookServiceMock.findBookById(1L)).thenReturn(detailedBook);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/book?id=1"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("book"))
				.andExpect(model().attribute("book", detailedBook)).andExpect(model().attribute("header", "Book info"));
	}

	@Test
	public void testBookDetailPageGetWhenWrongId() throws Exception {
		// given
		BookTo detailedBook = new BookTo(1L, "Obcy", "Albert Camus", BookStatus.FREE);
		when(bookServiceMock.findBookById(1L)).thenReturn(detailedBook);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/book?id=7"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("book"))
				.andExpect(model().attribute("header", "Sorry, there is no such book"));
	}

	@Test
	public void testBookDetailPagePost() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(post("/books/book?id=1"));
		// then
		resultActions.andExpect(status().isMethodNotAllowed());
	}

	@Test

	public void testAddBookPageGet() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/add"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("addBook"));
	}

	@Test
	public void testAddBookPagePost() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(post("/books/add"));
		// then
		resultActions.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void testGreetingPageGet() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(get("/greeting"));
		// then
		resultActions.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void testGreetingPagePost() throws Exception {
		BookTo addeddBook = new BookTo(1L, "Albert Camus", "Obcy", BookStatus.FREE);
		// when
		ResultActions resultActions = mockMvc.perform(post("/greeting").flashAttr("newBook", addeddBook));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("greeting"));
	}

	@Test
	public void testSearchPageGet() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(get("/search"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("search"));
	}

	@Test
	public void testSearchPagePost() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(post("/search"));
		// then
		resultActions.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void testSearchResultPageGet() throws Exception {
		BookTo searchedBook = new BookTo(1L, "Albert Camus", "Obcy", BookStatus.FREE);
		List<BookTo> booksList = new ArrayList<BookTo>();
		booksList.add(searchedBook);
		when(bookServiceMock.searchBooks("Albert+Camus", "Obcy")).thenReturn(booksList);
		// when
		ResultActions resultActions = mockMvc.perform(get("/search/searchResult?title=Obcy&authors=Albert+Camus"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("searchResult"))
				.andExpect(model().attribute("bookList", booksList));
	}

	@Test
	public void testSearchResultPagePost() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(post("/search/searchResult?title=Obcy&authors=Albert+Camus"));
		// then
		resultActions.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void testDeletePageGet() throws Exception {
		BookTo deletedBook = new BookTo(1L, "Obcy", "Albert Camus", BookStatus.FREE);
		when(bookServiceMock.findBookById(1L)).thenReturn(deletedBook);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete?id=1"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("delete"))
				.andExpect(model().attribute("book", deletedBook));
	}

	@Test
	public void testDeletePagePost() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(post("/books/delete?id=1"));
		// then
		resultActions.andExpect(status().isMethodNotAllowed());
	}

}
