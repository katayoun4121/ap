package ap.projects.finalproject;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 2L;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, int publicationYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    // Getter methods
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return isAvailable; }

    // Setter methods
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        String status = isAvailable ? "Available" : "Borrowed";
        return "Title: " + title +
                " | Author: " + author +
                " | Year: " + publicationYear +
                " | ISBN: " + isbn +
                " | Status: " + status;
    }

    public boolean matchesSearch(String title, String author, Integer year) {
        boolean titleMatch = title == null || title.isEmpty() ||
                this.title.toLowerCase().contains(title.toLowerCase());

        boolean authorMatch = author == null || author.isEmpty() ||
                this.author.toLowerCase().contains(author.toLowerCase());

        boolean yearMatch = year == null || year == 0 ||
                this.publicationYear == year;

        return titleMatch && authorMatch && yearMatch;
    }
}