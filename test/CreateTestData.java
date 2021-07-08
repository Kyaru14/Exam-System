package test;

import util.Question;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

//生成500道测试题目
public class CreateTestData {
    public static void main(String[] args) {
        File file = new File("test.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            String[] answers = {"choice A", "choice B", "choice C", "choice D"};
            for (int i = 1; i <= 500; i++) {
                Question temp = new Question((Math.abs(new Random().nextInt())) % 3 + 1);
                if (temp.getType() == Question.FILLING) {
                    temp.setQandA("question" + i, "this is a filling answer");
                } else {
                    temp.setQandA("question" + i, answers[(Math.abs(new Random().nextInt())) % 4]);
                }
                ArrayList<String> choices = new ArrayList<>();
                if (temp.getType() == Question.SELECTION) {
                    choices.add("choice A");
                    choices.add("choice B");
                    choices.add("choice C");
                    choices.add("choice D");
                } else if (temp.getType() == Question.JUDGE) {
                    choices.add("true");
                    choices.add("false");
                }
                temp.setChoices(choices);
                StringBuilder strToWrite = new StringBuilder();
                strToWrite.append(i).append(".").append(temp.getContent()).append("(").append(temp.getType()).append(")").append("\n");
                if (!temp.getChoices().isEmpty())
                    for (String choice : temp.getChoices()) {
                        strToWrite.append(choice).append("\n");
                    }
                strToWrite.append(temp.getAnswer()).append("\n");
                fileWriter.write(strToWrite.toString());
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("成功创建测试题库，路径："+file.getAbsolutePath());
    }
}
