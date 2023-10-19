package com.testspringboot.service;

import com.testspringboot.dto.BookReqDTO;
import com.testspringboot.dto.BookReqFormDTO;
import com.testspringboot.dto.BookResDTO;
import com.testspringboot.entity.BookEntity;
import com.testspringboot.exception.BusinessException;
import com.testspringboot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BookResDTO saveBook(BookReqDTO bookReqDTO) {
        BookEntity bookEntity = modelMapper.map(bookReqDTO, BookEntity.class);
        BookEntity saveBook = bookRepository.save(bookEntity);
        return modelMapper.map(saveBook, BookResDTO.class);
    }

    @Transactional(readOnly = true)
    public BookResDTO getBookById(Long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(id + " Not Found", HttpStatus.NOT_FOUND));
        BookResDTO bookResDTO = modelMapper.map(bookEntity, BookResDTO.class);
        return bookResDTO;
    }

    @Transactional(readOnly = true)
    public BookResDTO getBookByIsbn(String isbn) {
        BookEntity bookEntity = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException(isbn + " Not Found", HttpStatus.NOT_FOUND));
        BookResDTO bookResDTO = modelMapper.map(bookEntity, BookResDTO.class);
        return bookResDTO;
    }

    @Transactional(readOnly = true)
    public List<BookResDTO> getBooks() {
        List<BookEntity> bookEntity = bookRepository.findAll();
        List<BookResDTO> bookResDTOList = bookEntity.stream()
                .map(book -> modelMapper.map(book, BookResDTO.class))
                .collect(Collectors.toList());
        return bookResDTOList;
    }

    public BookResDTO updateBook(Long id, BookReqDTO bookReqDTO) {
        BookEntity existBook = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(id + " Not Found", HttpStatus.NOT_FOUND));
        if (bookReqDTO.getAuthor() != null) {
            existBook.setAuthor(bookReqDTO.getAuthor());
        }
        if (bookReqDTO.getTitle() != null) {
            existBook.setTitle(bookReqDTO.getTitle());
        }
        return modelMapper.map(existBook, BookResDTO.class);
    }

    public void updateBookForm(BookReqFormDTO bookReqFormDTO) {
        BookEntity updateBook = bookRepository.findById(bookReqFormDTO.getId())
                .orElseThrow(() ->
                        new BusinessException(bookReqFormDTO.getId() + " Not Found", HttpStatus.NOT_FOUND));
        if (bookReqFormDTO.getAuthor() != null) {
            updateBook.setAuthor(bookReqFormDTO.getAuthor());
        }
        if (bookReqFormDTO.getTitle() != null) {
            updateBook.setTitle(bookReqFormDTO.getTitle());
        }
    }

    public void deleteBook(Long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(id + " Not Found", HttpStatus.NOT_FOUND));
        bookRepository.delete(bookEntity);

    }


}
