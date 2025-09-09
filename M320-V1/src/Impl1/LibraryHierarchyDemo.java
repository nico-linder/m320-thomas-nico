package Impl1;

public class LibraryHierarchyDemo {
    public static void main(String[] args) {
        System.out.println("=== DIGITAL LIBRARY - HIERARCHY DEMO ===\n");

        Publication[] publications = {
                new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, "Fantasy",
                        1200, "978-3-608-93830-2", "Shelf A-12"),
                new EBook("Clean Code", "Robert C. Martin", 2008, "Computer Science",
                        464, "https://ebooks.library.com/cleancode", 15.2, "PDF"),
                new Audiobook("Sapiens", "Yuval Noah Harari", 2011, "History",
                        512, 15.25, "Derek Perkins", 20),
                new Magazine("National Geographic", "Various Authors", 2024, "Science",
                        128, 3, "National Geographic Society")
        };

        System.out.println("📚 ALL PUBLICATIONS IN LIBRARY:");
        for (Publication pub : publications) {
            System.out.println(pub.toString());
            System.out.println("   ⏱️  Reading time: " + String.format("%.1f", pub.getReadingTime()) + " hours");
            System.out.println("   ℹ️  " + pub.getAdditionalInfo());
            System.out.println("   " + (pub.isAvailable() ? "✅ Available" : "❌ Not available"));
            System.out.println();
        }

        System.out.println("\n🎯 SPECIFIC FUNCTIONS:");

        Book book = (Book) publications[0];
        System.out.println("Loaning book: " + book.getTitle());
        book.loanOut();
        System.out.println("Available after loan: " + book.isAvailable());

        EBook ebook = (EBook) publications[1];
        System.out.println("\n" + ebook.download());

        Audiobook audiobook = (Audiobook) publications[2];
        System.out.println(audiobook.play());

        System.out.println("\n⭐ ADDING RATINGS:");
        for (Publication pub : publications) {
            pub.setRating(Math.random() * 4 + 1);
            System.out.println(pub.getTitle() + ": " + String.format("%.1f", pub.getRating()) + "⭐");
        }
    }
}