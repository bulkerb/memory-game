java.net.URLConnection;
java.net.URLEncoder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.io.InputStream;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

URLConnection = new URL("<https://emoji-api.com/emojis?access_key=f48301a44b0c8d06490563f08004880e0de02e51
>/</https:>").openConnection();
unirest.get ("https://emoji-api.com/emojis?access_key=f48301a44b0c8d06490563f08004880e0de02e51");
.end(function (result))
console.log()

public class getemojis
{
    public static void main(String[] args) throws Execption
    {
        // Host url
      String host = "https://emoji-api.com/emojis?access_key=";
      String charset = "UTF-8";
      // Headers for a request
      String host = "https://emoji-api.com/emojis?access_key=";
      String key = <f48301a44b0c8d06490563f08004880e0de02e51>;
      // Params
      String s = "grinning-face";
  // Format query for preventing encoding problems
      String query = String.format("s=%s",
       URLEncoder.encode(s, charset));
       
    HttpResponse <JsonNode> response = Unirest.get(host + "?" + query)
      .header("xh", host)
      .header("xy", key)
      .asJson();
        System.out.println(response.getStatus());
        System.out.println(response.getHeaders().get("Content-Type"));

        InputStream is = httpResponse.getRawBody();
        BufferedImage inputStreamImage = ImageIO.read(is);
        File image = new File("image.jpg");
        ImageIO.write(inputStreamImage, "jpg", image);
        System.out.println( httpResponse.getHeaders()
      .get("Content-Type"));
    }
}