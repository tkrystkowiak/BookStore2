package pl.jstk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.jstk.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

	@Query("select book from BookEntity book where upper(book.title) like concat(upper(:title), '%')")
	List<BookEntity> findBookByTitle(@Param("title") String title);

	@Query("select book from BookEntity book where upper(book.authors) like concat('%', upper(:author), '%')")
	List<BookEntity> findBookByAuthor(@Param("author") String author);

	@Query("select book from BookEntity book where upper(book.authors) like concat('%', upper(:author), '%') and upper(book.title) like concat(upper(:title), '%')")
	List<BookEntity> findBookByAuthorAndTitle(@Param("author") String author, @Param("title") String title);

	@Query("select book from BookEntity book where upper(book.authors) like concat('%', upper(:author), '%') and upper(book.title) like concat(upper(:title), '%') and book.id like id")
	List<BookEntity> findBookByAuthorAndTitleAndId(@Param("author") String author, @Param("author") String title);

}
