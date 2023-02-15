package adrian.bookcrud.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;
import adrian.bookcrud.models.Book;
import adrian.bookcrud.services.BookService;

@RestController
public class MainController {

  // ArrayList<Book> books = new ArrayList<>();
  @Autowired
  BookService bookService;

  // create
  @RequestMapping("/books/new")
  public String newBook(@ModelAttribute("book") Book book) {
    return "new.jsp";
  }

  // @RequestMapping(value="/books" ,method= RequestMethod.POST) // this is the
  // same as createBook
  // public String createBook(){
  // return "redirect:/";
  // }

  @PostMapping("/books")
  public String createBook( // but this is simpler
      @RequestParam("title") String title,
      @RequestParam("author") String author,
      @RequestParam("pages") Integer pages, HttpSession session) {
    Book newBook = new Book(title, author, pages);
    bookService.createBook(newBook);
    // Book book = new Book(title,author,pages); // instance of book
    // System.out.println(title);
    // books.add(book);
    // session.setAttribute("title",title);
    // session.setAttribute("author",author);
    // session.setAttribute("pages",pages);
    return "redirect:/";
  }

  // // read one
  // @RequestMapping("/books/1")
  // public String show(Model model) {
  // model.addAttribute("book",books.get(1));
  // return "show.jsp";
  // }

  // read all // usually the read all is the root
  @RequestMapping("/")
  public String index(Model model) {
    List<Book> books = bookService.allBooks();
    System.out.println(books);
    model.addAttribute("books", books);
    return "index.jsp";
  }

  // update
  @RequestMapping(value="/books/{id}", method=RequestMethod.PUT)
    public Book update(
        @PathVariable("id") Long id, 
        @RequestParam(value="title") String title, 
        @RequestParam(value="author") String author, 
        @RequestParam(value="pages") Integer pages) {
        Book book = bookService.updateBook(id, title,author,pages);
        System.out.println(book);
        return book;
        }


  //delete
  @DeleteMapping("/books/{id}")
  public void deleteBook(@PathVariable("id") Long id) {
    bookService.deleteBookById(id);
  }

}
