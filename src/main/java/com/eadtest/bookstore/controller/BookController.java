package com.eadtest.bookstore.controller;

import com.eadtest.bookstore.entity.Book;
import com.eadtest.bookstore.repository.BookRepository;
import com.eadtest.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        List<Book> books = bookService.books();
        model.addAttribute("books", books);
        return "book/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String detail(@PathVariable int id, Model model) {
        Book book = bookService.getById(id);
        if (book == null) {
            return "error/404";
        }
        model.addAttribute("book", book);
        return "hero/detail";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/create")
    public String create(Model model) {
        model.addAttribute("book", new Book());
        return "hero/form";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String store(Model model, @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            return "book/form";
        }
        bookService.create(book);
        return "redirect:/books";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Book book = bookService.getById(id);
        if (book == null) {
            return "error/404";
        }
        model.addAttribute("book", book);
        return "book/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit/{id}")
    public String update(@PathVariable int id, Model model, Book updateBook) {
        Book book = bookService.getById(id);
        if (book == null) {
            return "error/404";
        }
        book.setBook_name(updateBook.getBook_name());
        book.setAuthor(updateBook.getAuthor());
        bookService.update(book);
        return "redirect:/books";
    }

    // viết ajax call.
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable int id) {
        HashMap<String, Object> mapResponse = new HashMap<>();
        Book book = bookService.getById(id);
        if (book == null) {
            mapResponse.put("status", HttpStatus.NOT_FOUND.value());
            mapResponse.put("message", "Hero is not found!");
            return new ResponseEntity<>(mapResponse, HttpStatus.NOT_FOUND);
        }
        bookService.delete(book);
        mapResponse.put("status", HttpStatus.OK.value());
        mapResponse.put("message", "Delete success");
        return new ResponseEntity<>(mapResponse, HttpStatus.OK);
    }
}
