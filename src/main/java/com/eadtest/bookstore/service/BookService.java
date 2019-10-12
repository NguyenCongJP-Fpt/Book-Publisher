package com.eadtest.bookstore.service;

import com.eadtest.bookstore.entity.Book;
import com.eadtest.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> books(){
        return bookRepository.findActiveBook(1);
    }

    public Book getById(int book_id) {
        return bookRepository.findById(book_id).orElse(null);
    }

    public Book create(Book book) {
        book.setStatus(1);
        book.setCreatedAt(Calendar.getInstance().getTimeInMillis());
        book.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        book.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
        return bookRepository.save(book);
    }

    public boolean delete(Book book) {
        book.setDeletedAt(Calendar.getInstance().getTimeInMillis());
        book.setStatus(-1);
        bookRepository.save(book);
        return true;
    }
}
