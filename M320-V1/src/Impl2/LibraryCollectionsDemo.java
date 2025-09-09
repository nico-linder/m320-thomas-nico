package Impl2;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LibraryCollectionsDemo {
    public static void main(String[] args) {
        System.out.println("=== DIGITAL LIBRARY - COLLECTIONS & INTERFACES DEMO ===\n");

        SmartLibrary library = new SmartLibrary("City Central Library");

        Publication[] publications = {
                new Publication("1984", "George Orwell", 1949, "Dystopian Fiction"),
                new Publication("To Kill a Mockingbird", "Harper Lee", 1960, "Classic Literature"),
                new Publication("The Great Gatsby", "F. Scott Fitzgerald", 1925, "Classic Literature"),
                new Publication("Harry Potter", "J.K. Rowling", 1997, "Fantasy"),
                new Publication("Dune", "Frank Herbert", 1965, "Science Fiction"),
                new Publication("Clean Code", "Robert C. Martin", 2008, "Programming"),
                new Publication("The Hobbit", "J.R.R. Tolkien", 1937, "Fantasy"),
                new Publication("Pride and Prejudice", "Jane Austen", 1813, "Romance")
        };

        for (Publication pub : publications) {
            pub.setRating(Math.random() * 4 + 1);
            for (int i = 0; i < (int)(Math.random() * 20); i++) {
                pub.increasePopularity();
            }
            library.addSorted(pub);
        }

        System.out.println("📚 LIBRARY CONTENTS (sorted by rating):");
        for (Publication pub : library) {
            System.out.println(pub);
        }

        System.out.println("\n🔍 SEARCH FUNCTIONS:");
        System.out.println("Books by Tolkien:");
        List<Publication> tolkienBooks = library.findByAuthor("Tolkien");
        tolkienBooks.forEach(System.out::println);

        System.out.println("\nFantasy books:");
        List<Publication> fantasyBooks = library.findByType("Fantasy");
        fantasyBooks.forEach(System.out::println);

        System.out.println("\nTop 3 rated books:");
        List<Publication> topRated = library.getTopRated(3);
        topRated.forEach(System.out::println);

        System.out.println("\nBooks published after 1950:");
        List<Publication> modern = library.getPublishedAfter(1950);
        modern.forEach(System.out::println);

        System.out.println(String.format("\n📊 STATISTICS:"));
        System.out.println("Average rating: " + String.format("%.2f", library.getAverageRating()));
        System.out.println("Type distribution:");
        Map<String, Long> stats = library.getTypeStatistics();
        stats.forEach((type, count) -> System.out.println("  " + type + ": " + count));

        System.out.println("\n📖 READING LIST DEMO:");
        ReadingList myReadingList = new ReadingList("My Reading List");
        myReadingList.addWithPriority(library.get(0), true);
        myReadingList.addWithPriority(library.get(2), false);
        myReadingList.addWithPriority(library.get(1), true);
        myReadingList.addWithPriority(library.get(4), false);

        System.out.println("Reading list order:");
        for (Publication pub : myReadingList) {
            System.out.println("  " + pub.getTitle() + " by " + pub.getAuthor());
        }

        System.out.println("\nReading progress:");
        while (myReadingList.getRemainingCount() > 0) {
            Publication current = myReadingList.getNext();
            System.out.println("Now reading: " + current.getTitle() +
                    " (Remaining: " + myReadingList.getRemainingCount() + ")");
        }

        System.out.println("\n🔀 SHUFFLE ITERATOR:");
        Iterator<Publication> shuffled = myReadingList.shuffleIterator();
        System.out.println("Shuffled order:");
        while (shuffled.hasNext()) {
            Publication pub = shuffled.next();
            System.out.println("  " + pub.getTitle());
        }

        System.out.println("\n🏆 POPULARITY RANKING:");
        PopularityRanking ranking = new PopularityRanking();
        ranking.addAll(library);

        System.out.println("Most popular books:");
        List<Publication> topPopular = ranking.getTopPopular(5);
        for (int i = 0; i < topPopular.size(); i++) {
            Publication pub = topPopular.get(i);
            System.out.println((i+1) + ". " + pub.getTitle() + " - " +
                    pub.getPopularity() + " borrows, " +
                    String.format("%.1f", pub.getRating()) + "⭐");
        }

        System.out.println("\nSimulating borrowing of '" + ranking.getMostPopular().getTitle() + "':");
        ranking.simulateBorrowing(ranking.getMostPopular());
        System.out.println("New most popular: " + ranking.getMostPopular().getTitle() +
                " (" + ranking.getMostPopular().getPopularity() + " borrows)");

        System.out.println("\n🎯 FILTERED ITERATOR (Fantasy books only):");
        Iterator<Publication> fantasyIterator = myReadingList.filteredIterator("Fantasy");
        while (fantasyIterator.hasNext()) {
            Publication pub = fantasyIterator.next();
            System.out.println("  Fantasy: " + pub.getTitle());
        }
    }
}