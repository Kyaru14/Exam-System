import util.AccountManager;
import java.io.File;

//启动
public class Launcher {
    public static AccountManager accountManager = new AccountManager();
    public static QuestionBank questionBank = new QuestionBank();

    public static void main(String[] args) {
        accountManager.put("2020010910022","12345678");
        accountManager.put("admin","admin");
        questionBank.load(new File("test.txt"));
        GUI.show();
    }
}
