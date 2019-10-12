package com.eadtest.bookstore.repository;


import com.eadtest.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor {
    List<Book> findActiveBook(@Param("status") int status);

    List<Book> findAllByStatus(int status);
}
