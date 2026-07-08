import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Manages student records by providing methods to add, display,
 * search, delete, and analyze student information.
 */
public class StudentManager {

    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * Available academic majors.
     */
    private static final List<String> MAJOR_OPTIONS =
            List.of(
                    "CS",
                    "IT",
                    "MATH",
                    "PHYSICS"
    );

    /**
     * Available graduate research topics.
     */
    private static final List<String> RESEARCH_TOPIC_OPTIONS =
            List.of(
                    "Artificial Intelligence",
                    "Cybersecurity",
                    "Data Science",
                    "Machine Learning",
                    "Computer Networks",
                    "Software Engineering",
                    "Cloud Computing",
                    "Human-Computer Interaction"
    );

    // -----------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------

    /**
     * Stores all registered students.
     */
    private static final List<Student> students = new ArrayList<>();

    /**
     * Reads user input from the console.
     */
    private static final Scanner SCANNER = new Scanner(System.in);

    // -----------------------------
    // Initialization Method
    // -----------------------------

    /**
     * Displays the program menu and executes the selected
     * student management operation until the user chooses to exit.
     */
    static void displayMenu() {
        List<String> menuOptions = List.of(
                "1", "2", "3", "4",
                "5", "6", "7", "8"
        );

        String choice;

        do {
            System.out.println(
                    """
                    Choose one of the options below:
                    1- Add Student
                    2- Add Graduate Student
                    3- Display Student
                    4- Display All Students
                    5- Delete Student
                    6- Display Highest Grade
                    7- Display Students Above Average
                    8- Exit
                    """
            );

            choice = StudentManager.getListValidatedInput(
                    "Enter your choice: ",
                    menuOptions
            );

            switch (choice) {
                case "1" -> StudentManager.addStudent();
                case "2" -> StudentManager.addGraduateStudent();
                case "3" -> StudentManager.displayStudent();
                case "4" -> {
                    StudentDisplayThread displayThread =
                            new StudentDisplayThread();

                    displayThread.start();

                    try {
                        displayThread.join();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(
                                "Display thread was interrupted."
                        );
                    }
                }
                case "5" -> StudentManager.deleteStudent();
                case "6" -> StudentManager.displayHighestGrade();
                case "7" -> StudentManager.displayStudentsAboveAverage();
                case "8" -> System.out.println("Goodbye!");
            }

            System.out.println();
        } while (!choice.equals("8"));
    }

    // -----------------------------
    // Student Management Methods
    // -----------------------------

    /**
     * Prompts the user for student information and
     * adds the new student to the student list.
     */
    static void addStudent() {
        System.out.print("Enter student name: ");
        String name = SCANNER.nextLine().trim();

        int age = getRangeValidatedInput(
                "Enter student age: ",
                5,
                19
        );

        String selectedMajor = getListValidatedInput(
                "Enter student major (CS, IT, MATH, PHYSICS): ",
                MAJOR_OPTIONS
        );

        List<Integer> grades = getGrades();

        students.add(
                new Student(
                        name,
                        age,
                        Major.valueOf(selectedMajor),
                        grades
                )
        );

        System.out.println("\nStudent added successfully.\n");
    }

    /**
     * Prompts the user for graduate student information
     * and adds the new graduate student to the student list.
     */
    static void addGraduateStudent() {
        System.out.print("Enter student name: ");
        String name = SCANNER.nextLine().trim();

        int age = getRangeValidatedInput(
                "Enter student age: ",
                5,
                19
        );

        String selectedMajor = getListValidatedInput(
                "Enter student major (CS, IT, MATH, PHYSICS): ",
                MAJOR_OPTIONS
        );

        List<Integer> grades = getGrades();

        System.out.println(
                """
                Enter research topic:
                - Artificial Intelligence
                - Cybersecurity
                - Data Science
                - Machine Learning
                - Computer Networks
                - Software Engineering
                - Cloud Computing
                - Human-Computer Interaction
                """
        );

        String researchTopic = getListValidatedInput(
                "Enter your choice: ",
                RESEARCH_TOPIC_OPTIONS
        );

        students.add(
                new GraduateStudent(
                        name,
                        age,
                        Major.valueOf(selectedMajor),
                        grades,
                        researchTopic
                )
        );

        System.out.println("\nGraduate student added successfully.\n");
    }

    /**
     * Deletes a student by their ID.
     */
    static void deleteStudent() {
        if (students.isEmpty()) {
            System.out.println("\nNo students have been registered yet.\n");
            return;
        }

        while (true) {

            int studentId = getRangeValidatedInput(
                    "Enter student ID: ",
                    10000,
                    99999
            );

            boolean studentRemoved =
                    students.removeIf(
                            student -> student.getId() == studentId
                    );

            if (studentRemoved) {
                System.out.println("\nStudent deleted successfully.\n");
                return;
            }

            System.out.println("\nStudent not found.\n");
        }
    }

    // -----------------------------------------------------------------
    // Student Display Methods
    // -----------------------------------------------------------------

    /**
     * Searches for a student by name and
     * displays their information.
     */
    static void displayStudent() {
        if (students.isEmpty()) {
            System.out.println("\nNo students have been registered yet.\n");
            return;
        }

        while (true) {
            System.out.print("Enter student name: ");
            String studentName = SCANNER.nextLine().trim();

            for (Student student : students) {
                if (student.getName().equalsIgnoreCase(studentName)) {
                    displayStudentInformation(student);
                    return;
                }
            }

            System.out.println("\nStudent not found.\n");
        }
    }

    /**
     * Displays all registered students.
     */
    static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("\nNo students have been registered yet.\n");
            return;
        }

        List<Student> sortedStudents = new ArrayList<>(students);

        sortedStudents.sort(
                (firstStudent, secondStudent) ->
                        Double.compare(
                                secondStudent.calculateAverage(),
                                firstStudent.calculateAverage()
                        )
        );

        System.out.println("\n---------------- Registered Students ----------------");

        for (Student student : sortedStudents) {
            displayStudentInformation(student);
        }
    }

    /**
     * Displays the student with the highest average grade.
     */
    static void displayHighestGrade() {
        if (students.isEmpty()) {
            System.out.println("\nNo students have been registered yet.\n");
            return;
        }

        Student highestAverageStudent = students.getFirst();

        for (Student student : students) {
            if (student.calculateAverage() > highestAverageStudent.calculateAverage()) {
                highestAverageStudent = student;
            }
        }

        displayStudentInformation(highestAverageStudent);
    }

    /**
     * Displays all students with an average
     * grade above the overall average.
     */
    static void displayStudentsAboveAverage() {
        if (students.isEmpty()) {
            System.out.println("\nNo students have been registered yet.\n");
            return;
        }

        double overallAverage = calculateOverallAverage();
        boolean foundAboveAverage = false;

        System.out.printf(
                "%nOverall average: %.2f%n%n",
                overallAverage
        );

        System.out.println("Students above the overall average:");

        for (Student student : students) {
            if (student.calculateAverage() > overallAverage) {
                foundAboveAverage = true;
                displayStudentInformation(student);
            }
        }

        if (!foundAboveAverage) {
            System.out.println("\nNo students are above the overall average.\n");
        }
    }

    // -----------------------------------------------------------------
    // Statistics Methods
    // -----------------------------------------------------------------

    /**
     * Calculates the overall average grade of all students.
     *
     * @return the overall student average
     */
    static double calculateOverallAverage() {
        if (students.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;

        for (Student student : students) {
            sum += student.calculateAverage();
        }

        return sum / students.size();
    }

    // -----------------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------------

    /**
     * Displays the information of the specified student.
     * If the student is a graduate student, the research
     * topic is displayed as well.
     *
     * @param student the student to display
     */
    private static void displayStudentInformation(Student student) {
        System.out.printf(
                """
                %nID: %d
                Name: %s
                Age: %d
                Major: %s
                Letter Grade: %c
                """,
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getMajor(),
                student.calculateGrade()
        );

        if (student instanceof GraduateStudent graduateStudent) {
            System.out.printf(
                    "Research Topic: %s%n",
                    graduateStudent.getResearchTopic()
            );
        }

        System.out.println();
    }

    /**
     * Prompts the user to enter student grades.
     *
     * @return a list of validated student grades
     */
    private static List<Integer> getGrades() {
        int gradeCount = getMinValidatedInput(
                "Enter number of grades: ",
                1
        );

        List<Integer> grades = new ArrayList<>();

        for (int gradeNumber = 1; gradeNumber <= gradeCount; gradeNumber++) {
            String gradePrompt = String.format(
                    "Enter grade #%d: ",
                    gradeNumber
            );

            grades.add(
                    getRangeValidatedInput(
                            gradePrompt,
                            0,
                            100
                    )
            );
        }

        return grades;
    }

    // -----------------------------
    // Input Validation Methods
    // -----------------------------

    /**
     * Prompts the user until one of the valid options in the provided
     * list is entered.
     *
     * @param prompt      message displayed to the user
     * @param optionsList list of valid input options
     * @return a validated option from the provided list
     */
    private static String getListValidatedInput(
            String prompt,
            List<String> optionsList
    ) {
        String input;

        while (true) {
            System.out.print(prompt);

            input = SCANNER.nextLine().trim();

            for (String option : optionsList) {
                if (input.equalsIgnoreCase(option)) {
                    return option;
                }
            }

            System.out.printf(
                    "Invalid input. Please enter one of the available options (e.g., %s).%n%n",
                    optionsList.getFirst()
            );
        }
    }

    /**
     * Prompts the user until a valid whole number greater than or equal to
     * the specified minimum value is entered.
     *
     * @param prompt   message displayed to the user
     * @param minValue minimum acceptable value
     * @return a validated integer greater than or equal to minValue
     */
    private static int getMinValidatedInput(
            String prompt,
            int minValue
    ) {
        int input;

        while (true) {
            try {
                System.out.print(prompt);

                input = SCANNER.nextInt();
                SCANNER.nextLine();

                if (input >= minValue) {
                    return input;
                }
                System.out.printf(
                        "Value must be at least %d.%n%n",
                        minValue
                );

            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid input. Please enter a whole number.\n"
                );
                SCANNER.nextLine();

            } catch (Exception e) {
                System.out.println(
                        "An unexpected error occurred. Please try again.\n"
                );
                SCANNER.nextLine();
            }
        }
    }

    /**
     * Prompts the user until a valid whole number within the
     * specified range is entered.
     *
     * @param prompt   message displayed to the user
     * @param minValue minimum acceptable value
     * @param maxValue maximum acceptable value
     * @return a validated integer within the specified range
     */
    private static int getRangeValidatedInput(
            String prompt,
            int minValue,
            int maxValue
    ) {
        int input;

        while (true) {
            try {
                System.out.print(prompt);

                input = SCANNER.nextInt();
                SCANNER.nextLine();

                if (input >= minValue && input <= maxValue) {
                    return input;
                }
                System.out.printf(
                        "Value must be between %d and %d.%n%n",
                        minValue,
                        maxValue
                );

            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid input. Please enter a whole number.\n"
                );
                SCANNER.nextLine();

            } catch (Exception e) {
                System.out.println(
                        "An unexpected error occurred. Please try again.\n"
                );
                SCANNER.nextLine();
            }
        }
    }
}
