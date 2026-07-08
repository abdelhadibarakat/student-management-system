import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a student with personal information,
 * academic major, and a collection of grades.
 */
public class Student {

    /**
     * Generates random five-digit student IDs.
     */
    private static final Random RANDOM = new Random();

    // Student information
    private final int id;
    private final String name;
    private final int age;
    private final Major major;
    private final List<Integer> grades;

    /**
     * Creates a new student.
     *
     * @param name   student's name
     * @param age    student's age
     * @param major  student's academic major
     * @param grades list of student grades
     */
    public Student(
            String name,
            int age,
            Major major,
            List<Integer> grades
    ) {
        this.id = RANDOM.nextInt(10000, 100000);
        this.name = name;
        this.age = age;
        this.major = major;
        this.grades = new ArrayList<>(grades);
    }

    /**
     * Returns the student's unique ID.
     *
     * @return student ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the student's name.
     *
     * @return student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the student's age.
     *
     * @return student age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the student's academic major.
     *
     * @return student's major
     */
    public Major getMajor() {
        return major;
    }

    /**
     * Returns a copy of the student's grades.
     *
     * @return copy of the grades list
     */
    public List<Integer> getGrades() {
        return new ArrayList<>(grades);
    }

    /**
     * Recursively calculates the total of all grades.
     *
     * @param grades list of grades
     * @param index current position in the list
     * @return sum of all grades
     */
    private static int sumGrades(
            List<Integer> grades,
            int index
    ) {
        if (index == grades.size()) {
            return 0;
        }

        return grades.get(index)
                + sumGrades(grades, index + 1);
    }

    /**
     * Calculates the student's average grade.
     *
     * @return the average of all recorded grades
     */
    public double calculateAverage() {
        if (grades.isEmpty()) {
            return 0.0;
        }

        return (double) sumGrades(grades, 0) / grades.size();
    }

    /**
     * Calculates the student's letter grade
     * based on the average of all recorded grades.
     *
     * @return A, B, C, D, or F
     */
    public char calculateGrade() {
        double average = calculateAverage();

        if (average >= 90) {
            return 'A';
        } else if (average >= 80) {
            return 'B';
        } else if (average >= 70) {
            return 'C';
        } else if (average >= 60) {
            return 'D';
        }

        return 'F';
    }

}