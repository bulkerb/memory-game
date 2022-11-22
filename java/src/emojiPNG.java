import java.net.*;
import java.nio.file.*;
import java.io.*;  

public class emojiPNG {
    static void getPng(String unicode) {
        String protocol = "http";
        String host = "emojiapi.dev";
        String file = "/api/v1/"+unicode+"/512.png";
        String file_name = unicode+".png";
        try{
            URL url = new URL(protocol, host, file);
            System.out.println(url);
            InputStream in = url.openStream();
            Files.copy(in, Paths.get(file_name), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        String emojiCode = "1f92a";
        getPng(emojiCode);
        
    }
}

