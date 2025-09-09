package Impl2;

import java.util.*;
import java.util.stream.Collectors;

class ReadingList extends LinkedList<Publication> implements Iterable<Publication> {
    private String listName;
    private int currentPosition;

    public ReadingList(String name) {
        super();
        this.listName = name;
        this.currentPosition = 0;
    }

    public void addWithPriority(Publication publication, boolean highPriority) {
        if (highPriority) {
            this.addFirst(publication);
        } else {
            this.addLast(publication);
        }
    }

    public Publication getNext() {
        if (isEmpty() || currentPosition >= size()) {
            currentPosition = 0;
            return null;
        }
        Publication next = get(currentPosition);
        currentPosition++;
        return next;
    }

    public Publication getCurrentReading() {
        if (isEmpty() || currentPosition == 0) {
            return null;
        }
        return get(currentPosition - 1);
    }

    public void reset() {
        currentPosition = 0;
    }

    public int getRemainingCount() {
        return Math.max(0, size() - currentPosition);
    }

    @Override
    public Iterator<Publication> iterator() {
        return new ReadingListIterator();
    }

    public Iterator<Publication> shuffleIterator() {
        List<Publication> shuffled = new ArrayList<>(this);
        Collections.shuffle(shuffled);
        return shuffled.iterator();
    }

    public Iterator<Publication> filteredIterator(String type) {
        return this.stream()
                .filter(p -> p.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList())
                .iterator();
    }

    private class ReadingListIterator implements Iterator<Publication> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Publication next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return get(index++);
        }

        @Override
        public void remove() {
            if (index <= 0) {
                throw new IllegalStateException();
            }
            ReadingList.this.remove(--index);
        }
    }

    public String getListName() { return listName; }
}