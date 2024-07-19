package onlinequize;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Question {
    protected String questionText;
    protected int marks;

    public Question(String questionText, int marks) {
        this.questionText = questionText;
        this.marks = marks;
    }

    public abstract boolean checkAnswer(String userAnswer);

    public abstract String getCorrectAnswer();
}

class MultipleChoiceQuestion extends Question {
    private String[] options;
    int correctOption;

    public MultipleChoiceQuestion(String questionText, int marks, String[] options, int correctOption) {
        super(questionText, marks);
        this.options = options;
        this.correctOption = correctOption - 1;
    }

    public String[] getOptions() {
        return options;
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null || userAnswer.isEmpty()) {
            return false;
        }

        boolean isInteger = true;
        for (int i = 0; i < userAnswer.length(); i++) {
            if (!Character.isDigit(userAnswer.charAt(i))) {
                isInteger = false;
                break;
            }
        }

        if (isInteger) {
            int userOption = Integer.parseInt(userAnswer) - 1;
            return userOption == correctOption;
        } else {
            return false;
        }
    }

    @Override
    public String getCorrectAnswer() {
        return options[correctOption];
    }
}

class Quiz {
    private Question[] questions;
    private int score;
    private int totalMarks;
    private String userName;
    private String userClass;
    private long startTime;
    private long endTime;
    private List<Question> correctQuestions;
    private List<Question> wrongQuestions;

    public Quiz(Question[] questions, String userName, String userClass) {
        this.questions = questions;
        this.score = 0;
        this.totalMarks = 0;
        this.userName = userName;
        this.userClass = userClass;
        this.correctQuestions = new ArrayList<>();
        this.wrongQuestions = new ArrayList<>();
        for (Question q : questions) {
            this.totalMarks += q.marks;
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.println("Welcome, " + userName + "!");
        System.out.println("You are in class " + userClass);
        System.out.println("Rules:");
        System.out.println("1. You will get " + totalMarks + " marks for this quiz.");
        System.out.println("2. Each correct answer will earn you marks based on the question.");
        System.out.println("3. There are no negative marks for wrong answers.");
        System.out.println("4. Type the number corresponding to your answer and press Enter.");
        System.out.println("Press Enter to start the quiz...");
        scanner.nextLine();

        startTime = System.currentTimeMillis();
        
        for (Question question : questions) {
        	System.out.println();
            System.out.println(question.questionText);

            if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
                for (int i = 0; i < mcq.getOptions().length; i++) {
                    System.out.println((i + 1) + ". " + mcq.getOptions()[i]);
                }
            }

            String userAnswer = scanner.nextLine();

            if (question.checkAnswer(userAnswer)) {
                score += question.marks;
                correctQuestions.add(question);
            } else {
                wrongQuestions.add(question);
            }
        }

        endTime = System.currentTimeMillis();
    }

    static class Grading {
        public static String calculateGrade(int score, int totalMarks) {
            double percentage = (double) score / totalMarks * 100;
            if (percentage >= 90) {
                return "A";
            } else if (percentage >= 80) {
                return "B";
            } else if (percentage >= 70) {
                return "C";
            } else if (percentage >= 60) {
                return "D";
            } else {
                return "F";
            }
        }
    }

    public void showResult() {
    	
    	Scanner scanner = new Scanner(System.in);
        long durationSeconds = (endTime - startTime) / 1000;
        System.out.println();
        System.out.println("Quiz ended. Your score: " + score + "/" + totalMarks);
        System.out.println("Grade: " + Grading.calculateGrade(score, totalMarks));
        System.out.println("Time taken: " + durationSeconds + " seconds");
        
        System.out.println("Press Enter to Show the Detailed Result!");
        scanner.nextLine();
        
        System.out.println();
        System.out.println("Correct answers:");
        for (Question question : correctQuestions) {
            System.out.println(question.questionText);
        }
        
        System.out.println();
        System.out.println("Wrong answers:");
        for (Question question : wrongQuestions) {
            System.out.println(question.questionText + " Correct answer: " + question.getCorrectAnswer());
        }
    }
}

class ChooseLevels {
    public void Levels(String level, String name, String className) {
        Question[] questions;

        switch (level.toLowerCase()) {
            case "easy":
                questions = new Question[]{
                        new MultipleChoiceQuestion("Who is Mickey Mouse's Sister?", 2, new String[]{"Minnie Mouse", "Daisy Duck", "Donald Duck", "Goofy"}, 1),
                        new MultipleChoiceQuestion("Which planet is closest to the Sun?", 2, new String[]{"Venus", "Mars", "Mercury", "Earth"}, 3),
                        new MultipleChoiceQuestion("What is the largest ocean on Earth?", 2, new String[]{"Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"}, 4),
                        new MultipleChoiceQuestion("What do caterpillars turn into?", 2, new String[]{"Butterflies", "Moths", "Dragonflies", "Bees"}, 1),
                        new MultipleChoiceQuestion("How many continents are there on Earth?", 2, new String[]{"6", "7", "5", "8"}, 2)
                };
                break;
            case "medium":
                questions = new Question[]{
                        new MultipleChoiceQuestion("Who painted the Mona Lisa?", 2, new String[]{"Leonardo da Vinci", "Michelangelo", "Vincent van Gogh", "Pablo Picasso"}, 1),
                        new MultipleChoiceQuestion("What is the largest planet in our solar system?", 2, new String[]{"Mars", "Jupiter", "Saturn", "Uranus"}, 2),
                        new MultipleChoiceQuestion("Which famous scientist developed the theory of relativity?", 2, new String[]{"Isaac Newton", "Albert Einstein", "Galileo Galilei", "Stephen Hawking"}, 2),
                        new MultipleChoiceQuestion("What is the main component of Earth's atmosphere?", 2, new String[]{"Oxygen", "Nitrogen", "Carbon Dioxide", "Helium"}, 2),
                        new MultipleChoiceQuestion("What year did the Titanic sink?", 2, new String[]{"1910", "1912", "1914", "1916"}, 2)
                };
                break;
            case "hard":
                questions = new Question[]{
                        new MultipleChoiceQuestion("Which chemical element has the symbol 'Sn'?", 2, new String[]{"Tin", "Titanium", "Tungsten", "Thallium"}, 1),
                        new MultipleChoiceQuestion("Who wrote the novel 'War and Peace'?", 2, new String[]{"Leo Tolstoy", "Fyodor Dostoevsky", "Anton Chekhov", "Ivan Turgenev"}, 1),
                        new MultipleChoiceQuestion("What is the only planet in our solar system known to support life?", 2, new String[]{"Mars", "Earth", "Venus", "Jupiter"}, 2),
                        new MultipleChoiceQuestion("Who painted the famous painting 'The Scream'?", 2, new String[]{"Edvard Munch", "Vincent van Gogh", "Pablo Picasso", "Claude Monet"}, 1),
                        new MultipleChoiceQuestion("In what year did World War II end?", 2, new String[]{"1943", "1945", "1947", "1950"}, 2)
                };
                break;
            default:
                questions = new Question[0];
                break;
        }

        Quiz quiz = new Quiz(questions, name, className);
        quiz.start();
        quiz.showResult();
    }
}

public class OnlineQuizSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your class: ");
        String className = scanner.nextLine();
        System.out.print("Enter the level of difficulty (easy, medium, hard): ");
        String level = scanner.nextLine();

        ChooseLevels chooseLevels = new ChooseLevels();
        chooseLevels.Levels(level, name, className);
    }
}
