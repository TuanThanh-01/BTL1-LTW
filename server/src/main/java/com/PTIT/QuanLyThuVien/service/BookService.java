package com.PTIT.QuanLyThuVien.service;

import com.PTIT.QuanLyThuVien.error.BookNotFoundException;
import com.PTIT.QuanLyThuVien.model.Book;

import java.util.List;

public interface BookService {
    List<Book> fetchBookList(String keyword);

    Book fetchBookById(int bookId) throws BookNotFoundException;

    Book updateBook(int bookId, Book book) throws BookNotFoundException;

    Book save(Book book);

    void deleteBookById(int bookId) throws BookNotFoundException;
}
