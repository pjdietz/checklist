import com.pjdietz.checklist.Checklist;

public class ChecklistSample {

    enum CHECKS {
        CAT,
        DOG,
        BIRD
    }

    public static void main(String[] args) {

        System.out.println("Start");

        final Checklist<CHECKS> checks = new Checklist<CHECKS>(CHECKS.class) {
            @Override
            protected void onReady() {
                System.out.println("Ready!");
            }
        };

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                System.out.println("Setting Cat");
                checks.check(CHECKS.CAT);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
                System.out.println("Setting Cat");
                checks.check(CHECKS.CAT);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ignored) {
                }
                System.out.println("Setting Dog");
                checks.check(CHECKS.DOG);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ignored) {
                }
                System.out.println("Setting Bird");
                checks.check(CHECKS.BIRD);
            }
        }.start();

        System.out.println("Setup Complete");
    }
}
