package Impl1;

class Audiobook extends Publication {
    private double duration;
    private String narrator;
    private int chapters;

    public Audiobook(String title, String author, int publishYear, String genre,
                     int pages, double duration, String narrator, int chapters) {
        super(title, author, publishYear, genre, pages);
        this.duration = duration;
        this.narrator = narrator;
        this.chapters = chapters;
    }

    @Override
    public String getPublicationType() {
        return "Impl1.Audiobook";
    }

    @Override
    public double getReadingTime() {
        return duration;
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("Narrator: %s, Chapters: %d, Duration: %.1fh",
                narrator, chapters, duration);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    public String play() {
        return "▶️ Playing: " + title;
    }

    public double getDuration() { return duration; }
    public String getNarrator() { return narrator; }
    public int getChapters() { return chapters; }
}