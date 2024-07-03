import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApplication {

    // questions, options, and answers
    private String[][] questions = {
            { "What is the capital of France?", "Paris", "London", "Berlin", "Madrid", "A" },
            { " what  is the capital of India?", "New Delhi", "Mumbai", "kolkata", "Jaipur", "A" },
            { " what  is the capital of Germany", "Berlin", "Hamburg", "Stuttgart", "Düsseldorf", "A" },
            { "What is the pH value of the human body?", "9.2 to 9.8", "7.0 to 7.8", "6.1 to 6.3", "5.4 to 5.6", "B" },
            { "What is 2 + 2?", "3", "4", "5", "6", "B" },
            { "Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Saturn", "B" },
            { "Which of the following Himalayan regions is called Shivalik ?", "Upper Himalayas", "Lower Himalayas",
                    "Outer Himalayas", "Inner Himalayas", "B" }
    };

    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timerSeconds = 10;
    private Timer timer;

    // GUI
    private JFrame frame;
    private JLabel questionLabel;
    private JRadioButton[] options = new JRadioButton[4];
    private ButtonGroup optionsGroup;
    private JButton submitButton;
    private JLabel timerLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApplication().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        questionLabel = new JLabel();
        panel.add(questionLabel);

        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionsGroup.add(options[i]);
            panel.add(options[i]);
        }

        timerLabel = new JLabel("Time left: " + timerSeconds + " seconds");
        panel.add(timerLabel);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        panel.add(submitButton);

        frame.add(panel);
        frame.setVisible(true);

        loadNextQuestion();
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex < questions.length) {
            String[] questionData = questions[currentQuestionIndex];
            questionLabel.setText(questionData[0]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(questionData[i + 1]);
            }
            optionsGroup.clearSelection();
            startTimer();
        } else {
            showResults();
        }
    }

    private void startTimer() {
        timerSeconds = 10;
        timerLabel.setText("Time left: " + timerSeconds + " seconds");
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000, new TimerListener());
        timer.start();
    }

    private void submitAnswer() {
        if (timer != null) {
            timer.stop();
        }
        String selectedOption = "";
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selectedOption = String.valueOf((char) ('A' + i));
                break;
            }
        }
        if (selectedOption.equals(questions[currentQuestionIndex][5])) {
            score++;
        }
        currentQuestionIndex++;
        loadNextQuestion();
    }

    private void showResults() {
        JOptionPane.showMessageDialog(frame, "Quiz over! Your score is: " + score + "/" + questions.length);
        frame.dispose();
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            submitAnswer();
        }
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timerSeconds--;
            if (timerSeconds <= 0) {
                submitAnswer();
            } else {
                timerLabel.setText("Time left: " + timerSeconds + " seconds");
            }
        }
    }
}
