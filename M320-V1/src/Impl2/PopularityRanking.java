package Impl2;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

class PopularityRanking extends TreeSet<Publication> {
    public PopularityRanking() {
        super((p1, p2) -> {
            int popularityCompare = Integer.compare(p2.getPopularity(), p1.getPopularity());
            if (popularityCompare != 0) return popularityCompare;

            int ratingCompare = Double.compare(p2.getRating(), p1.getRating());
            if (ratingCompare != 0) return ratingCompare;

            return p1.getTitle().compareTo(p2.getTitle());
        });
    }

    public List<Publication> getTopPopular(int count) {
        return this.stream().limit(count).collect(Collectors.toList());
    }

    public Publication getMostPopular() {
        return isEmpty() ? null : first();
    }

    public void simulateBorrowing(Publication publication) {
        remove(publication);
        publication.increasePopularity();
        add(publication);
    }
}