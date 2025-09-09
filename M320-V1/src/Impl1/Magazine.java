package Impl1;

class Magazine extends Publication {
    private int issueNumber;
    private String publisher;
    private boolean isCurrentIssue;

    public Magazine(String title, String author, int publishYear, String genre,
                    int pages, int issueNumber, String publisher) {
        super(title, author, publishYear, genre, pages);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
        this.isCurrentIssue = (publishYear == 2024);
    }

    @Override
    public String getPublicationType() {
        return "Impl1.Magazine";
    }

    @Override
    public double getReadingTime() {
        return pages / 120.0;
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("Issue #%d, Publisher: %s", issueNumber, publisher);
    }

    @Override
    public boolean isAvailable() {
        return isCurrentIssue;
    }

    public int getIssueNumber() { return issueNumber; }
    public String getPublisher() { return publisher; }
}