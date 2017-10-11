package ru.gb.space_shooter.game;

import java.util.ArrayList;
import java.util.List;

public abstract class ObjectPool<T extends Poolable> {
    protected List<T> activeList;
    protected List<T> inactiveList;

    public List<T> getActiveList() {
        return activeList;
    }

    public List<T> getInactiveList() {
        return inactiveList;
    }

    protected abstract T newObject();

    public ObjectPool(int size) {
        this.activeList = new ArrayList<T>();
        this.inactiveList = new ArrayList<T>();
        for (int i = 0; i < size; i++) {
            inactiveList.add(newObject());
        }
    }

    public T activateElement() {
        if (inactiveList.size() == 0) {
            inactiveList.add(newObject());
        }
        T temp = inactiveList.remove(inactiveList.size() - 1);
        activeList.add(temp);
        temp.setActive(true);
        return temp;
    }

    public void deactivateElement(int index) {
        activeList.get(index).setActive(false);
        inactiveList.add(activeList.remove(index));
    }

    public void checkPool() {
        for (int i = activeList.size() - 1; i >= 0; i--) {
            if (!activeList.get(i).isActive()) {
                deactivateElement(i);
            }
        }
    }
}
