import java.net.*;
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        URL url = new URL("https://emoji-api.com/emojis?access_key=f48301a44b0c8d06490563f08004880e0de02e51");
        System.out.println("name: " + url.getFile());
    }
}
