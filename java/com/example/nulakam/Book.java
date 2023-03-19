package com.example.nulakam;

public class Book {
    private String BookName;
    private String ImageUrl;

    public Book(String bookName, String imageUrl) {
        BookName = bookName;
        ImageUrl = imageUrl;
    }

    public Book() {

    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
