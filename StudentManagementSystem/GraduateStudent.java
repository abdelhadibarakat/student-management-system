import java.util.List;

/**
 * Represents a graduate student with
 * an additional research topic.
 */
public class GraduateStudent extends Student {

    /**
     * The student's research topic.
     */
    private final String researchTopic;

    /**
     * Creates a new graduate student.
     *
     * @param name student's name
     * @param age student's age
     * @param major student's academic major
     * @param grades list of student grades
     * @param researchTopic student's research topic
     */
    public GraduateStudent(
            String name,
            int age,
            Major major,
            List<Integer> grades,
            String researchTopic
    ) {
        super(
                name,
                age,
                major,
                grades
        );

        this.researchTopic = researchTopic;
    }

    /**
     * Returns the student's research topic.
     *
     * @return student's research topic
     */
    public String getResearchTopic() {
        return researchTopic;
    }

    /**
     * Calculates the graduate student's letter grade
     * based on the average of all recorded grades.
     *
     * @return A, B, C, D, or F
     */
    @Override
    public char calculateGrade() {
        double average = calculateAverage();

        if (average >= 85) {
            return 'A';
        } else if (average >= 75) {
            return 'B';
        } else if (average >= 65) {
            return 'C';
        } else if (average >= 55) {
            return 'D';
        }

        return 'F';
    }
}
