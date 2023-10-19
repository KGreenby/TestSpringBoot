package com.testspringboot.controller;

import com.testspringboot.entity.BookEntity;
import com.testspringboot.exception.BusinessException;
import com.testspringboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookBasicRestController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public BookEntity addBook(@RequestBody BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    @GetMapping
    public List<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public BookEntity getBook(@PathVariable Long id) {
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);
        BookEntity bookEntity = optionalBookEntity
                .orElseThrow(() -> new BusinessException("Not Found", HttpStatus.NOT_FOUND));
        return bookEntity;
    }

    @GetMapping("/isbn/{isbn}")
    public BookEntity getBookByIsbn(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new BusinessException("Not Found", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Not Found", HttpStatus.NOT_FOUND));
        bookRepository.delete(bookEntity);
        return ResponseEntity.ok(id + "book이 삭제되었습니다.");
    }

    @PutMapping("/{id}")
    public BookEntity updateBook(@PathVariable Long id, @RequestBody BookEntity bookEntity) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Not Found", HttpStatus.NOT_FOUND));
        book.setGenre(bookEntity.getGenre());
        book.setAuthor(bookEntity.getAuthor());
        BookEntity updateBook = bookRepository.save(book);
        return updateBook;
    }


}
