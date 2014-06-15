package com.pjdietz.checklist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Performs an action once all of given set of conditions are met.
 *
 * The conditions are determined by proving an enum to the instance. Each field of the enum is used as a "check" in the
 * checklist. Check or uncheck a given field by calling Checklist#check(E) and Checklist#uncheck(E). If a call to
 * check(E) results in all fields being checked, the instance will notify observers by calling their onReady methods.
 *
 * @param <E> Enum of fields to use as checks
 */
public class ObservableChecklist<E extends Enum<E>> extends Checklist<E> {

    private final Set<OnReadyObserver> observers;

    public ObservableChecklist(Class<E> enumClass) {
        super(enumClass);
        observers = new HashSet<OnReadyObserver>();
    }

    /**
     * Add an observer. If all checks are true at the time the observer is added, the onReady() method for this observer
     * only is called.
     *
     * @param observer Instance to notify when all items in the checklist are true.
     */
    public void registerObserver(OnReadyObserver observer) {
        synchronized (observers) {
            observers.add(observer);
        }
        if (isReady()) {
            observer.onReady();
        }
    }

    /**
     * @param observer Instance to remove from the list of observers.
     */
    public void unregisterObserver(OnReadyObserver observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    @Override
    protected void onReady() {
        List<OnReadyObserver> observers;
        synchronized (this.observers) {
            observers = new ArrayList<OnReadyObserver>(this.observers);
        }
        for (OnReadyObserver observer : observers) {
            observer.onReady();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    public interface OnReadyObserver {
        /** Called when all items in the checklist are true. */
        public void onReady();
    }

}
