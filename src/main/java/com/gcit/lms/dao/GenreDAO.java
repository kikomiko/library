package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO {

	public GenreDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void addGenre(Genre genre) throws SQLException, ClassNotFoundException {
		save("insert into tbl_genre (genre_name) values (?)", new Object[] { genre.getGenreName() });
	}

	public Integer addGenreWithID(Genre genre) throws ClassNotFoundException, SQLException {
		return saveWithID("insert into tbl_genre (genre_name) values (?)", new Object[] { genre.getGenreName() });
	}

	public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("update tbl_genre set genre_name = ? where genre_id = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("delete * from tbl_genre where genre_id= ? ", new Object[] { genre.getGenreId() });
	}

	public List<Genre> readAllGenres() throws ClassNotFoundException, SQLException {
		return readAll("select * from tbl_genre", null);
	}

	public Genre readAllGenresByPk(Integer genreId) throws ClassNotFoundException, SQLException {
		List<Genre> genres = readAll("select * from tbl_genre where genre_id = ?", new Object[] { genreId });
		if (genres != null && !genres.isEmpty()) {
			return genres.get(0);
		}
		return null;
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Genre> genres = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			g.setBooks(bdao.readAllFirstLevel(
					"Select * from tbl_book\r\n" + "join tbl_book_genres\r\n"
							+ "on tbl_book_genres.bookId = tbl_book.bookId\r\n" + "WHERE tbl_book_genres.genre_id = ?",
					new Object[] { rs.getInt("genre_id") }));
			genres.add(g);
		}
		return genres;

	}

	@Override
	public List<Genre> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			genres.add(g);
		}
		return genres;
	}

}
