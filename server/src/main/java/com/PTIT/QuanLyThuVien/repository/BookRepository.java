package com.PTIT.QuanLyThuVien.repository;

import com.PTIT.QuanLyThuVien.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("Select b FROM Book b WHERE b.title LIKE %?1%")
    public List<Book> search(String keyword);
}
