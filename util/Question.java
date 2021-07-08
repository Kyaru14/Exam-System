package util;

import java.util.ArrayList;

public class Question {

    /**
     * 选择题
     */
    public static final int SELECTION = 1;
    /**
     * 判断题
     */
    public static final int JUDGE = 2;
    /**
     * 填空题
     */
    public static final int FILLING = 3;

    private String content;
    private final int type;
    private String answer;
    private ArrayList<String> choices;

    /**
     * 题目类
     * @param type 题目类型
     * @throws IllegalTypeException 类型有误时抛出此异常
     */
    public Question(int type){
        if(type>=1&&type<=3)
            this.type = type;
        else
            throw new IllegalTypeException();
    }
    public void setQandA(String content,String answer){
        this.content = content;
        this.answer = answer;
    }
    public void setContent(String content){
        this.content = content;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getAnswer() {
        return answer;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }
}
class IllegalTypeException extends RuntimeException{
    public IllegalTypeException(){
        super("题目类型有误");
    }
}