package Impl2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class SmartLibrary extends ArrayList<Publication> {
    private String libraryName;

    public SmartLibrary(String name) {
        super();
        this.libraryName = name;
    }

    public boolean addSorted(Publication publication) {
        boolean result = super.add(publication);
        Collections.sort(this);
        return result;
    }

    public List<Publication> findByAuthor(String author) {
        return this.stream()
                .filter(p -> p.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Publication> findByType(String type) {
        return this.stream()
                .filter(p -> p.getType().equalsIgnoreCase(type))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Publication> getTopRated(int count) {
        return this.stream()
                .sorted()
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Publication> getPublishedAfter(int year) {
        return this.stream()
                .filter(p -> p.getPublishYear() > year)
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageRating() {
        return this.stream()
                .mapToDouble(Publication::getRating)
                .filter(rating -> rating > 0)
                .average()
                .orElse(0.0);
    }

    public Map<String, Long> getTypeStatistics() {
        return this.stream()
                .collect(Collectors.groupingBy(Publication::getType, Collectors.counting()));
    }

    public String getLibraryName() { return libraryName; }
}