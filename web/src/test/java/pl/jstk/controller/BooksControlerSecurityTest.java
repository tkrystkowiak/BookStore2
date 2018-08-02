package pl.jstk.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.jstk.enumerations.BookStatus;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BooksControlerSecurityTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@InjectMocks
	BooksController booksControler;

	@Mock
	private BookService bookServiceMock;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
	}

	@Test
	public void shouldAuthenticateBooksPageTest() throws Exception {
		// given
		List<BookTo> testList = new ArrayList<BookTo>();
		testList.add(new BookTo(1L, "Obcy", "Albert Camus", BookStatus.FREE));
		when(bookServiceMock.findAllBooks()).thenReturn(testList);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books"));
		// then
		resultActions.andExpect(status().isOk());
	}

	@Test
	public void shouldRedirectBookDetailPageTest() throws Exception {
		// given
		BookTo detailedBook = new BookTo(1L, "Obcy", "Albert Camus", BookStatus.FREE);
		when(bookServiceMock.findBookById(1L)).thenReturn(detailedBook);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/book?id=1"));
		// then
		resultActions.andExpect(status().isFound());
	}

	@Test
	@WithMockUser(roles = { "ADMIN", "USER" })
	public void shouldAuthenticateBookDetailPageTest() throws Exception {
		// given
		BookTo detailedBook = new BookTo(2L, "Obcy", "Albert Camus", BookStatus.FREE);
		when(bookServiceMock.findBookById(2L)).thenReturn(detailedBook);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/book?id=2"));
		// then
		resultActions.andExpect(status().isOk());
	}

	@Test
	public void shouldRedirectAddBookPageTest() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/add"));
		// then
		resultActions.andExpect(status().isFound());
	}

	@Test
	@WithMockUser(roles = { "ADMIN", "USER" })
	public void shouldAuthenticateAddBookPageTest() throws Exception {
		// given
		BookTo addedBook = new BookTo(1L, "Obcy", "Albert Camus", BookStatus.FREE);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/add").flashAttr("newBook", addedBook));
		// then
		resultActions.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = { "ADMIN" })
	public void shouldAuthenticateDeletePageTest() throws Exception {
		BookTo deletedBook = new BookTo(1L, "Obcy", "Albert Camus", BookStatus.FREE);
		when(bookServiceMock.findBookById(1L)).thenReturn(deletedBook);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete?id=1"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("delete"));
	}

	@Test
	@WithMockUser(roles = { "USER" })
	public void shouldForbidDeletePageTest() throws Exception {
		BookTo deletedBook = new BookTo(1L, "Obcy", "Albert Camus", BookStatus.FREE);
		when(bookServiceMock.findBookById(1L)).thenReturn(deletedBook);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete?id=1"));
		// then
		resultActions.andExpect(status().isForbidden());
	}

	@Test
	public void shouldRedirectDeletePageTest() throws Exception {
		BookTo deletedBook = new BookTo(1L, "Obcy", "Albert Camus", BookStatus.FREE);
		when(bookServiceMock.findBookById(1L)).thenReturn(deletedBook);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete?id=1"));
		// then
		resultActions.andExpect(status().isFound());
	}

	@Test
	public void shouldRedirectSearchPageTest() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(get("/search"));
		// then
		resultActions.andExpect(status().isFound());
	}

	@Test
	@WithMockUser(roles = { "ADMIN", "USER" })
	public void shouldAuthenticateSearchPageTest() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(get("/search"));
		// then
		resultActions.andExpect(status().isOk());
	}

}
