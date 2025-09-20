import controller.MainController;
import java.nio.file.Paths;
public class App {
    public static void main(String[] args) {
        var dataDir = Paths.get("data");
        var c = new MainController(dataDir);
        c.start();
    }
}