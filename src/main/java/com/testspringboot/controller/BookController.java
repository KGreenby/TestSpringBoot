package com.testspringboot.controller;

import com.testspringboot.dto.BookReqDTO;
import com.testspringboot.dto.BookReqFormDTO;
import com.testspringboot.dto.BookResDTO;
import com.testspringboot.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/index")
    public ModelAndView index() {
        List<BookResDTO> bookResDTOList = bookService.getBooks();
        return new ModelAndView("index", "books", bookResDTOList);
    }

    @GetMapping("/addBook")
    public String addBookForm(BookReqDTO bookReqDTO) {
        return "add-book";
    }

    @PostMapping("/addBook")
    public String addBook(@Valid BookReqDTO bookReqDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-book";
        }
        bookService.saveBook(bookReqDTO);
        model.addAttribute("books", bookService.getBooks());
        return "redirect:/books/index";
    }

    @GetMapping("/updateBook/{id}")
    public String updateBooksForm(@PathVariable Long id, Model model) {
        BookResDTO bookResDTO = bookService.getBookById(id);
        model.addAttribute("book", bookResDTO);
        return "update-book";
    }

    @PostMapping("/updateBook/{id}")
    public String updateBook(@Valid @ModelAttribute("book")BookReqFormDTO bookReqFormDTO,
                             BindingResult result, Model model, @PathVariable Long id) {
        if (result.hasErrors()) {
            model.addAttribute("books", bookReqFormDTO);
            model.addAttribute("errors", result.getAllErrors());
            return "update-book";
        }
        bookService.updateBookForm(bookReqFormDTO);
        return "redirect:/books/index";
    }

    @GetMapping("deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/books/index";
    }


}
