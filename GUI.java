import util.MessageWindow;
import util.Question;
import util.Window;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

//用户界面
public class GUI {
    private static final util.Window mainWindow;

    private static final java.util.List<Question> questions = Launcher.questionBank.getRandomQuestions(25);

    private static final ArrayList<String> answers = new ArrayList<>();

    static {
        mainWindow = new Window("考试系统", new Dimension(500, 500));
        mainWindow.getJFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainWindow.setLocation(300, 300);
        mainWindow.setBackground(Color.GRAY);
        for (int i = 0; i < 25; i++) {
            answers.add("");
        }
    }

    public static void show() {
        menu();
        mainWindow.show();
    }

    //主菜单布局
    public static void menu() {
        //字体设置
        Font titleFont = new Font("黑体", Font.BOLD, 24);

        JPanel menu = new JPanel();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER, 70, 100));


        JLabel label = new JLabel("Java高级语言程序设计考试");
        label.setFont(titleFont);
        menu.add(label);

        JPanel login = new JPanel();
        login.setLayout(new GridLayout(2, 2));
        JLabel number = new JLabel("学号：");
        JTextField numberTextField = new JTextField(20);
        JLabel password = new JLabel("密码：");
        JPasswordField passwordField = new JPasswordField(20);
        login.add(number);
        login.add(numberTextField);
        login.add(password);
        login.add(passwordField);
        menu.add(login);

        JButton button = new JButton("开始考试");
        button.addActionListener(actionEvent -> {
            if (Launcher.accountManager.check(numberTextField.getText(), passwordField.getText())) {
                menu.setVisible(false);
                exam();
            } else {
                new MessageWindow("用户名或密码不正确！");
            }
        });
        menu.add(button);

        mainWindow.addComp(menu);
    }

    //考试界面
    public static void exam() {
        JPanel exam = new JPanel();

        JPanel title = new JPanel();
        title.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel question = new JLabel("题目：");
        title.add(question);
        exam.add(title);

        AtomicInteger number = new AtomicInteger(0);//记录题号
        ArrayList<JPanel> questionPanels = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            questionPanels.add(nextQuestion(i));
        }
        AtomicReference<JPanel> nowPanel = new AtomicReference<>(questionPanels.get(0));

        JPanel p1 = new JPanel();
        JButton prev = new JButton("上一题");
        JButton next = new JButton("下一题");
        prev.addActionListener(actionEvent -> {
            if (number.get() > 0) {
                nowPanel.get().setVisible(false);
                nowPanel.set(questionPanels.get(number.get() - 1));
                p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
                p1.add(prev);
                p1.add(next);
                exam.add(nowPanel.get());
                exam.add(p1);
                number.getAndDecrement();
                nowPanel.get().setVisible(true);
            }
        });
        next.addActionListener(actionEvent -> {
            if (number.get() < 24) {
                nowPanel.get().setVisible(false);
                nowPanel.set(questionPanels.get(number.get() + 1));
                p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
                p1.add(prev);
                p1.add(next);
                exam.add(nowPanel.get());
                exam.add(p1);
                number.getAndIncrement();
                nowPanel.get().setVisible(true);
            }
        });
        p1.add(prev);
        p1.add(next);
        exam.add(nowPanel.get());
        exam.add(p1);
        mainWindow.addComp(exam);
        exam.setVisible(true);
    }
//生成每一道题的答题面板
    public static JPanel nextQuestion(int number) {
        JPanel res = new JPanel();
        res.setLayout(new GridLayout(4, 1));
        TextArea questionTextArea = new TextArea(4, 30);
        res.add(questionTextArea);

        Question question = questions.get(number);

        questionTextArea.setText("第 " + (number + 1) + " 题：" + question.getContent());
        questionTextArea.setEditable(false);

        JLabel answerLabel = new JLabel("您的回答：");
        res.add(answerLabel);

        JRadioButton choiceA;
        JRadioButton choiceB;
        JRadioButton choiceC;
        JRadioButton choiceD;
        TextField answerTextField;

        if (question.getType() == Question.FILLING) {
            answerTextField = new TextField(5);
            answerTextField.addTextListener(e -> answers.set(number,answerTextField.getText()));
            res.add(answerTextField);
        } else if (question.getType() == Question.SELECTION) {
            JPanel choices = new JPanel();
            choices.setLayout(new GridLayout(2, 2, 150, 20));
            choiceA = new JRadioButton(question.getChoices().get(0));
            choiceB = new JRadioButton(question.getChoices().get(1));
            choiceC = new JRadioButton(question.getChoices().get(2));
            choiceD = new JRadioButton(question.getChoices().get(3));
            choiceA.addActionListener(actionEvent -> {
                answers.set(number, choiceA.getText());
                choiceB.setSelected(false);
                choiceC.setSelected(false);
                choiceD.setSelected(false);
            });
            choiceB.addActionListener(actionEvent -> {
                answers.set(number, choiceB.getText());
                choiceA.setSelected(false);
                choiceC.setSelected(false);
                choiceD.setSelected(false);
            });
            choiceC.addActionListener(actionEvent -> {
                answers.set(number, choiceC.getText());
                choiceA.setSelected(false);
                choiceB.setSelected(false);
                choiceD.setSelected(false);
            });
            choiceD.addActionListener(actionEvent -> {
                answers.set(number, choiceD.getText());
                choiceA.setSelected(false);
                choiceB.setSelected(false);
                choiceC.setSelected(false);
            });
            choices.add(choiceA);
            choices.add(choiceB);
            choices.add(choiceC);
            choices.add(choiceD);
            res.add(choices);
        } else if (question.getType() == Question.JUDGE) {
            JPanel choices = new JPanel();
            choices.setLayout(new GridLayout(2, 2, 150, 20));
            choiceA = new JRadioButton(question.getChoices().get(0));
            choiceB = new JRadioButton(question.getChoices().get(1));
            choiceA.addActionListener(actionEvent -> {
                answers.set(number, "choice A");
                choiceB.setSelected(false);
            });
            choiceB.addActionListener(actionEvent -> {
                answers.set(number, "choice B");
                choiceA.setSelected(false);
            });
            choices.add(choiceA);
            choices.add(choiceB);
            res.add(choices);
        }
        if (number == 24) {
            JPanel endPanel = new JPanel();
            endPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton end = new JButton("交卷");
            end.addActionListener(actionEvent -> {
                new MessageWindow("确定交卷吗？", e -> {
                    mainWindow.vanish();
                    int score = 0;
                    for (int i = 0; i < 25; i++) {
                        System.out.println("question "+i+":");
                        System.out.println("your answer:" + answers.get(i));
                        System.out.println("expected answer:" + questions.get(i).getAnswer());
                        if (answers.get(i).equals(questions.get(i).getAnswer())) {
                            score += 4;
                        }
                    }
                    new MessageWindow("您的得分：" + score, e1 -> {
                        System.exit(1);
                    });
                });
            });
            endPanel.add(end);
            res.add(endPanel);
        }
        return res;
    }
}