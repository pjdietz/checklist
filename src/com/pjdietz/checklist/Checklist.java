package com.pjdietz.checklist;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

/**
 * Performs an action once all of given set of conditions are met.
 *
 * The conditions are determined by proving an enum to the instance. Each field of the enum is used as a "check" in the
 * checklist. Check or uncheck a given field by calling Checklist#check(E) and Checklist#uncheck(E). If a call to
 * check(E) results in all fields being checked, the instance will call its onReady() method.
 *
 * @param <E> Enum of fields to use as checks
 */
public abstract class Checklist<E extends Enum<E>> {

    private final Map<E, Boolean> checks;
    private final EnumSet<E> enumSet;

    /**
     * Create a new CheckList using the fields from the given enum.
     * @param enumClass Class for the enum to use as checks.
     */
    public Checklist(Class<E> enumClass) {
        checks = new EnumMap<E, Boolean>(enumClass);
        enumSet = EnumSet.allOf(enumClass);
        reset();
    }

    /**
     * Examine each check in the checklist. If all are true, run onReady.
     */
    public void check() {
        if (isReady()) {
            onReady();
        }
    }

    /**
     * Set a given field to true. This calls onReady if all are true after setting.
     * @param check Field to set to true.
     */
    public void check(E check) {
        synchronized (checks) {
            checks.put(check, true);
        }
        check();
    }

    /**
     * Set a given field to false.
     * @param check Field to set to false.
     */
    public void uncheck(E check) {
        synchronized (checks) {
            checks.put(check, false);
        }
    }

    /**
     * Set each check in the checklist to false.
     */
    public void reset() {
        synchronized (checks) {
            for (E eValue : enumSet) {
                checks.put(eValue, false);
            }
        }
    }

    /**
     * Return the state of the given check.
     */
    public boolean isChecked(E check) {
        synchronized (checks) {
            return checks.get(check);
        }
    }

    /**
     * Examine each check in the checklist. Return true if all are true.
     */
    public boolean isReady() {
        synchronized (checks) {
            for (Map.Entry<E, Boolean> entry : checks.entrySet()) {
                if (!entry.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method called when all checks in the checklist are true.
     */
    protected abstract void onReady();

}
