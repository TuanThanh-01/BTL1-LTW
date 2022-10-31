package com.PTIT.QuanLyThuVien.service;

import com.PTIT.QuanLyThuVien.error.BookNotFoundException;
import com.PTIT.QuanLyThuVien.model.Book;
import com.PTIT.QuanLyThuVien.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> fetchBookList(String keyword) {
        if(keyword != null) {
            return bookRepository.search(keyword);
        }
        return bookRepository.findAll();
    }

    @Override
    public Book fetchBookById(int bookId) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(bookId);
        if(!book.isPresent()) {
            throw new BookNotFoundException("Book not available!!!");
        }
        return book.get();
    }

    @Override
    public Book updateBook(int bookId, Book book) throws BookNotFoundException {
        Optional<Book> bookTmp = bookRepository.findById(bookId);
        if(!bookTmp.isPresent()) {
            throw new BookNotFoundException("Book not available!!!");
        }
        Book bookDB = bookTmp.get();

        if(Objects.nonNull(book.getTitle()) && !"".equalsIgnoreCase(book.getTitle())) {
            bookDB.setTitle(book.getTitle());
        }

        if(Objects.nonNull(book.getAuthor()) && !"".equalsIgnoreCase(book.getAuthor())) {
            bookDB.setAuthor(book.getAuthor());
        }

        if(Objects.nonNull(book.getDescription()) && !"".equalsIgnoreCase(book.getDescription())) {
            bookDB.setDescription(book.getDescription());
        }

        if(Objects.nonNull(book.getTitle()) && !"".equalsIgnoreCase(book.getTitle())) {
            bookDB.setTitle(book.getTitle());
        }

        if(Objects.nonNull(book.getDateRelease()) && !"".equalsIgnoreCase(book.getDateRelease())) {
            bookDB.setDateRelease(book.getDateRelease());
        }

        if(Objects.nonNull(book.getTotalPage()) && book.getTotalPage() > 0) {
            bookDB.setTotalPage(book.getTotalPage());
        }

        if(Objects.nonNull(book.getTypeBook()) && !"".equalsIgnoreCase(book.getTypeBook())) {
            bookDB.setTypeBook(book.getTypeBook());
        }

        if(Objects.nonNull(book.getBookImages()) && book.getBookImages().size() > 0) {
            bookDB.setBookImages(book.getBookImages());
        }

        return bookRepository.save(bookDB);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(int bookId) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(bookId);
        if(!book.isPresent()) {
            throw new BookNotFoundException("Book not available!!!");
        }

        bookRepository.deleteById(bookId);
    }

}
