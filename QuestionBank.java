import util.Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//读取问题，并生成题库
public class QuestionBank {
    private final List<Question> questionList = new LinkedList<>();
    public void load(File path){
        if(!path.exists()){
            System.err.println("未找到题库");
        }
        if(!path.getPath().endsWith(".txt")){
            System.out.println("文件格式错误");
            return;
        }
        ArrayList<String> content = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            for(Object obj:bufferedReader.lines().toArray()){
                content.add((String) obj);
            }
            for(int i = 0;i<content.size();){
                String line = content.get(i);
                int type = Integer.parseInt(String.valueOf(line.charAt(line.length()-2)));
                if(Character.isDigit(line.charAt(0))){
                    Question question = new Question(type);
                    for(int j=0;j<line.length();j++){
                        if(line.charAt(j)=='.'){
                            line = line.substring(j+1);
                            break;
                        }
                    }
                    question.setContent(line);
                    ArrayList<String> choices = new ArrayList();
                    if(type==Question.SELECTION){
                        choices.add(content.get(i+1));
                        choices.add(content.get(i+2));
                        choices.add(content.get(i+3));
                        choices.add(content.get(i+4));
                        question.setChoices(choices);
                        question.setAnswer(content.get(i+5));
                        i+=6;
                    }else if(type==Question.JUDGE){
                        choices.add(content.get(i+1));
                        choices.add(content.get(i+2));
                        question.setChoices(choices);
                        question.setAnswer(content.get(i+3));
                        i+=4;
                    }else{
                        question.setAnswer(content.get(i+1));
                        i+=2;
                    }
                    questionList.add(question);
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    /**
     * 得到一个新的题库
     * @param number 生成题目的数量
     * @return 生成的题库
     */
    public List<Question> getRandomQuestions(int number){
        List<Question> questions;
        Collections.shuffle(questionList);
        questions = questionList.subList(0,number);
        return questions;
    }
}
