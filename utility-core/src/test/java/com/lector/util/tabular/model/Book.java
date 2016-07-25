package com.lector.util.tabular.model;

import com.lector.util.tabular.annotation.Field;
import com.lector.util.tabular.annotation.Row;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
@Row(name = "Test 556")
public class Book implements Serializable{

    private static final long serialVersionUID = -2005362657272715250L;

    private String title;
    private String author;
    private BigDecimal price;
    private LocalDate releaseDate;
    private String publisher;
    private String language;
    private String isbn;

    @Field(position = 1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Field(position = 2)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Field(position = 3, name = "PRIS")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Field(position = 4)
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    //@Field(position = 5)
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Field(position = 6)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Field(position = 9)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (price != null ? !price.equals(book.price) : book.price != null) return false;
        if (releaseDate != null ? !releaseDate.equals(book.releaseDate) : book.releaseDate != null) return false;
        if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) return false;
        if (language != null ? !language.equals(book.language) : book.language != null) return false;
        return isbn != null ? isbn.equals(book.isbn) : book.isbn == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Book{");
        stringBuilder.append("title='").append(title).append('\'');
        stringBuilder.append(", author='").append(author).append('\'');
        stringBuilder.append(", price=").append(price);
        stringBuilder.append(", releaseDate=").append(releaseDate);
        stringBuilder.append(", publisher='").append(publisher).append('\'');
        stringBuilder.append(", language='").append(language).append('\'');
        stringBuilder.append(", isbn='").append(isbn).append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}