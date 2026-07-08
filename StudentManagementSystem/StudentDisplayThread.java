/**
 * Displays all registered students in a separate thread.
 */
public class StudentDisplayThread extends Thread {

    /**
     * Executes the student display task.
     */
    @Override
    public void run() {
        StudentManager.displayStudents();
    }
}
