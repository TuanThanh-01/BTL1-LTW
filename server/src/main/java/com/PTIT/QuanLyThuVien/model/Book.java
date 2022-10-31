package com.PTIT.QuanLyThuVien.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String dateRelease;
    private int totalPage;
    private String typeBook;

    @ElementCollection
    @JoinTable(name = "tbl_image", joinColumns = @JoinColumn(name = "book_id"))
    private List<Image> bookImages;

    public Book(String title, String author, String description, String dateRelease, int totalPage, String typeBook, List<Image> bookImages) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.dateRelease = dateRelease;
        this.totalPage = totalPage;
        this.typeBook = typeBook;
        this.bookImages = bookImages;
    }
}
