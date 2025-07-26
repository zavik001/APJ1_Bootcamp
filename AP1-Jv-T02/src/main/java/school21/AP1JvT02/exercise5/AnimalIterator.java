package school21.AP1JvT02.exercise5;

import java.util.List;

public class AnimalIterator<T> implements BaseIterator<T> {

    private List<T> animals;
    private Integer index;

    public AnimalIterator(List<T> animals) {
        this.animals = animals;
    }

    @Override
    public T next() {
        T animal = this.animals.get(index);
        this.index++;
        return animal;
    }

    @Override
    public Boolean hasNext() {
        return index < this.animals.size();
    }

    @Override
    public void reset() {
        this.index = 0;
    }
}
