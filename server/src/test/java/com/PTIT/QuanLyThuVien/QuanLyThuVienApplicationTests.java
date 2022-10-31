package com.PTIT.QuanLyThuVien;

import com.PTIT.QuanLyThuVien.model.Book;
import com.PTIT.QuanLyThuVien.model.Image;
import com.PTIT.QuanLyThuVien.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuanLyThuVienApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private BookService bookService;

	@Test
	private void testBook() {
	}


}
