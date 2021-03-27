import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.NoSuchElementException;

public final class MyCollection<E> implements Collection<E> {

    private int size;
    private static final int INITSIZE = 10;
    private Object[] elementData = new Object[INITSIZE];

    @Override
    public boolean add(final E e) {
        final double increaseSize = 1.5f;
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * increaseSize));
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public boolean contains(final Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elementData[i], o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] myArr = new Object[size];
        for (int i = 0; i < size; i++) {
            myArr[i] = elementData[i];
        }
        return myArr;
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        Object[] myArr = new Object[a.length];
        if (a.length == 0) {
            myArr = Arrays.copyOf(elementData, size);
        }
        if (a.length == size) {
            myArr = Arrays.copyOf(elementData, size);
        }
        if (a.length < size) {
            myArr = Arrays.copyOf(elementData, a.length);
        }
        if (a.length > size) {
            myArr = Arrays.copyOf(elementData, a.length);
            for (int i = 0; i < size; i++) {
                myArr[i] = elementData[i];
            }
            for (int j = size; j < a.length; j++) {
                myArr[j] = a[j];
            }
        }

        return (T[]) myArr;
    }

    @Override
    public boolean remove(final Object o) {
        MyIterator iterator = new MyIterator();
        for (int i = 0; i < size; i++) {
            iterator.next();
            if (Objects.equals(elementData[i], o)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        int containsCounter = 0;
        for (Object e : c) {
            for (int i = 0; i < size; i++) {
                if (Objects.equals(elementData[i], e)) {
                    containsCounter++;
                    break;
                }
            }
        }
        return containsCounter == c.size();
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        } else {
            for (Object e : c) {
                if (size == elementData.length) {
                    elementData = Arrays.copyOf(elementData, (int) (size + 1));
                }
                elementData[size++] = e;
            }
            return true;
        }
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        boolean isRemovedAll = false;
        for (Object e : c) {
            int count = 0;
            for (int j = 0; j < size; j++) {
                if (Objects.equals(elementData[j], e)) {
                    count++;
                }
            }
            while (count > 0) {
                MyIterator iterator = new MyIterator();
                for (int i = 0; i < size; i++) {
                    iterator.next();
                    if (Objects.equals(elementData[i], e)) {
                        iterator.remove();
                        count--;
                        break;
                    }
                }

                isRemovedAll = true;
            }
        }
        return isRemovedAll;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection is null");
        }
        MyIterator iterator = new MyIterator();
        boolean isRetainAll = false;
        while (iterator.hasNext()) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
                isRetainAll = true;
            }
        }

        return isRetainAll;
    }

    @Override
    public void clear() {
        MyIterator iterator = new MyIterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    private class MyIterator<T> implements Iterator<T> {

        private int cursor = 0;
        private boolean removeAgain;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            removeAgain = false;
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            if (cursor == 0) {
                throw new IllegalStateException();
            }
            if (removeAgain) {
                throw new IllegalStateException();
            }
            if (!hasNext()) {
                size--;
            } else {
                for (Integer i = cursor - 1; i < size - 1; i++) {
                    elementData[i] = elementData[i + 1];
                }
                size--;
                cursor--;
                removeAgain = true;
            }

//            throw new UnsupportedOperationException("remove");
        }
    }
}
