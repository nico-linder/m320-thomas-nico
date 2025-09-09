package Impl1;

class EBook extends Publication {
    private String downloadUrl;
    private double fileSize;
    private String format;

    public EBook(String title, String author, int publishYear, String genre,
                 int pages, String downloadUrl, double fileSize, String format) {
        super(title, author, publishYear, genre, pages);
        this.downloadUrl = downloadUrl;
        this.fileSize = fileSize;
        this.format = format;
    }

    @Override
    public String getPublicationType() {
        return "Book";
    }

    @Override
    public double getReadingTime() {
        return (pages / 60.0) * 0.8;
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("Format: %s, Size: %.1f MB", format, fileSize);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    public String download() {
        return "Download started from: " + downloadUrl;
    }

    public double getFileSize() { return fileSize; }
    public String getFormat() { return format; }
}