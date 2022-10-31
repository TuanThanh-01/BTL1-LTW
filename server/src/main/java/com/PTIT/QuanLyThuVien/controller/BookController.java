package com.PTIT.QuanLyThuVien.controller;

import com.PTIT.QuanLyThuVien.error.BookNotFoundException;
import com.PTIT.QuanLyThuVien.model.Book;
import com.PTIT.QuanLyThuVien.model.Image;
import com.PTIT.QuanLyThuVien.service.BookService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class BookController {

    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    private List<Book> fetchBookList(@Param("keyword") String keyword) {
        return bookService.fetchBookList(keyword);
    }

    @GetMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book fetchBookById(@PathVariable("id") int bookId) throws BookNotFoundException {
        return bookService.fetchBookById(bookId);
    }

    @PostMapping("/book/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Book saveBook(@RequestParam String title, @RequestParam String author,
                         @RequestParam String description, @RequestParam String dateRelease,
                         @RequestParam String totalPage, @RequestParam String typeBook,
                         @RequestBody MultipartFile[] images) throws IOException {
        List<Image> imageList = new ArrayList<>();
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        Path folderName = Paths.get(title);
        // create folder save image in path like /static/images/title_book/image_name
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(folderName))) {

            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(folderName));
        }
        for(int i = 0;i < images.length;i++) {
            Path file = CURRENT_FOLDER.resolve(staticPath)
                    .resolve(imagePath).resolve(folderName).resolve(images[i].getOriginalFilename());

            try (OutputStream os = Files.newOutputStream(file)) {
                os.write(images[i].getBytes());
            }
            imageList.add(new Image(folderName.resolve(images[i].getOriginalFilename()).toString()));
        }
        int totalPageNum = Integer.parseInt(totalPage);
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setDateRelease(dateRelease);
        book.setTotalPage(totalPageNum);
        book.setTypeBook(typeBook);
        book.setBookImages(imageList);

        return bookService.save(book);
    }

    @PutMapping("/book/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(@PathVariable("id") int bookId, @RequestParam String title,
                           @RequestParam String author,
                           @RequestParam String description, @RequestParam String dateRelease,
                           @RequestParam String totalPage, @RequestParam String typeBook,
                           @RequestBody MultipartFile[] images) throws BookNotFoundException, IOException {
        List<Image> imageList = new ArrayList<>();
        Book bookDB = bookService.fetchBookById(bookId);
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        Path folderName = Paths.get(bookDB.getTitle());
        Path newFolderName = Paths.get(title);
        String pathDirectory = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(folderName).toString();
        FileUtils.deleteDirectory(new File(pathDirectory));
        // create folder save image in path like /static/images/title_book/image_name
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(newFolderName))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(newFolderName));
        }
        for(int i = 0;i < images.length;i++) {
            Path file = CURRENT_FOLDER.resolve(staticPath)
                    .resolve(imagePath).resolve(newFolderName).resolve(images[i].getOriginalFilename());

            try (OutputStream os = Files.newOutputStream(file)) {
                os.write(images[i].getBytes());
            }
            imageList.add(new Image(newFolderName.resolve(images[i].getOriginalFilename()).toString()));
        }

        int totalPageNum = Integer.parseInt(totalPage);
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setDateRelease(dateRelease);
        book.setTotalPage(totalPageNum);
        book.setTypeBook(typeBook);
        book.setBookImages(imageList);

        return bookService.updateBook(bookId, book);
    }

    @DeleteMapping("/book/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteBookById(@PathVariable("id") int bookId) throws BookNotFoundException, IOException {
        Book book = bookService.fetchBookById(bookId);
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        Path folderName = Paths.get(book.getTitle());

        String pathDirectory = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(folderName).toString();
        FileUtils.deleteDirectory(new File(pathDirectory));
        bookService.deleteBookById(bookId);
        return "Delete book successfully!!!";
    }

}
