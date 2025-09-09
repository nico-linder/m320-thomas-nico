package Impl1;

class Book extends Publication {
    private String isbn;
    private boolean isLoaned;
    private String location;

    public Book(String title, String author, int publishYear, String genre,
                int pages, String isbn, String location) {
        super(title, author, publishYear, genre, pages);
        this.isbn = isbn;
        this.isLoaned = false;
        this.location = location;
    }

    @Override
    public String getPublicationType() {
        return "Book";
    }

    @Override
    public double getReadingTime() {
        return pages / 60.0;
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("ISBN: %s, Location: %s", isbn, location);
    }

    @Override
    public boolean isAvailable() {
        return !isLoaned;
    }

    public void loanOut() { isLoaned = true; }
    public void returnBook() { isLoaned = false; }
    public String getIsbn() { return isbn; }
    public String getLocation() { return location; }
}