package Impl2;

import java.util.*;
import java.util.stream.Collectors;

class Publication implements Comparable<Publication> {
    protected String title;
    protected String author;
    protected int publishYear;
    protected double rating;
    protected String type;
    protected int popularity;

    public Publication(String title, String author, int publishYear, String type) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.type = type;
        this.rating = 0.0;
        this.popularity = 0;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublishYear() { return publishYear; }
    public double getRating() { return rating; }
    public String getType() { return type; }
    public int getPopularity() { return popularity; }

    public void setRating(double rating) {
        if (rating >= 1.0 && rating <= 5.0) {
            this.rating = rating;
        }
    }

    public void increasePopularity() { popularity++; }

    @Override
    public int compareTo(Publication other) {
        int ratingCompare = Double.compare(other.rating, this.rating);
        if (ratingCompare != 0) {
            return ratingCompare;
        }

        int popularityCompare = Integer.compare(other.popularity, this.popularity);
        if (popularityCompare != 0) {
            return popularityCompare;
        }

        return this.title.compareTo(other.title);
    }

    @Override
    public String toString() {
        return String.format("%s: '%s' by %s (%d) - %.1f⭐ [%dx borrowed]",
                type, title, author, publishYear, rating, popularity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Publication that = (Publication) obj;
        return Objects.equals(title, that.title) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author);
    }
}