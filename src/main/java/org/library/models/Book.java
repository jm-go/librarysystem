package org.library.models;

public class Book {
    private int number;
    private String title;
    private String author;
    private String genre;
    private String subGenre;
    private String publisher;
    private boolean isLoaned;
    private String loanedTo;
    private int timesLoaned;

    public Book(int number, String title, String author, String genre, String subGenre, String publisher) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.subGenre = subGenre;
        this.publisher = publisher;
        this.isLoaned = false;
        this.loanedTo = null;
        this.timesLoaned = 0;
    }

    public Book() {
    }

    public Book(int number, String title, String author, String genre, String subGenre, String publisher, boolean isLoaned, String loanedTo) {
        this(number, title, author, genre, subGenre, publisher);
        this.isLoaned = isLoaned;
        this.loanedTo = loanedTo;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSubGenre() {
        return subGenre;
    }

    public void setSubGenre(String subGenre) {
        this.subGenre = subGenre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public boolean getIsLoaned() {
        return isLoaned;
    }

    public void setIsLoaned(boolean loaned) {
        isLoaned = loaned;
    }

    public String getLoanedTo() {
        return loanedTo;
    }

    public void setLoanedTo(String loanedTo) {
        this.loanedTo = loanedTo;
    }

    public boolean isLoaned() {
        return isLoaned;
    }

    public void setLoaned(boolean loaned) {
        isLoaned = loaned;
    }

    public int getTimesLoaned() {
        return timesLoaned;
    }

    public void setTimesLoaned(int timesLoaned) {
        this.timesLoaned = timesLoaned;
    }

    public void incrementLoanCounter() {
        this.timesLoaned++;
    }

    @Override
    public String toString() {
        return String.format("%d. Title: \"%s\" by %s (Genre: %s, Sub-genre: %s, Publisher: %s)",
                number, title, author, genre, subGenre, publisher);
    }
}