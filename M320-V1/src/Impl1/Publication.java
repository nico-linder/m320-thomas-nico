package Impl1;

abstract class Publication {
    protected String title;
    protected String author;
    protected int publishYear;
    protected String genre;
    protected double rating;
    protected int pages;

    public Publication(String title, String author, int publishYear, String genre, int pages) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.genre = genre;
        this.rating = 0.0;
        this.pages = pages;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublishYear() { return publishYear; }
    public String getGenre() { return genre; }
    public double getRating() { return rating; }
    public int getPages() { return pages; }

    public void setRating(double rating) {
        if (rating >= 1.0 && rating <= 5.0) {
            this.rating = rating;
        }
    }

    public abstract String getPublicationType();
    public abstract double getReadingTime();
    public abstract String getAdditionalInfo();
    public abstract boolean isAvailable();

    @Override
    public String toString() {
        return String.format("%s: '%s' by %s (%d) - Genre: %s, Rating: %.1f⭐",
                getPublicationType(), title, author, publishYear, genre, rating);
    }
}