Checklist
=========

The Checklist library provides classes for performing an action once all of given set of conditions are met.

To use, begin by creating a `enum` with one field for each step.

```java
enum CHECKS {
    STEP_1,
    STEP_2,
    STEP_3
}
```

Next, create a `CheckList` instance and pass the class for your `enum` to the constructor. You will also need to implement the `onReady` method. This is the method the instance calls as soon as each item in the list is "checked".

```java
final Checklist<CHECKS> checklist = new Checklist<CHECKS>(CHECKS.class) {
    @Override
    protected void onReady() {
        System.out.println("Ready!");
    }
};
```

Finally, call `check(E)` method to tell the object a given field is "checked". Once all are checked, the instance will call its `onReady` method.

```java
checklist.check(CHECKS.STEP_1); // not ready...
checklist.check(CHECKS.STEP_2); // not ready...
checklist.check(CHECKS.STEP_3); // ...ready!
```
