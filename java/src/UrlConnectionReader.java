import java.net.*;
import java.io.*;
import java.util.*;

public class UrlConnectionReader
{  
  /**
 * @param args
 */
public static void main(String[] args)
  {
    //references
    String host = "https://emoji-api.com/";
    String key = "?access_key=f48301a44b0c8d06490563f08004880e0de02e51";

    //all api info https://emoji-api.com/emojis?access_key=f48301a44b0c8d06490563f08004880e0de02e51
    String output = getUrlContents(host + "emojis" + key);
    //System.out.println(output);

    //categories: https://emoji-api.com/categories?access_key=f48301a44b0c8d06490563f08004880e0de02e51
    output = getUrlContents(host + "categories" + key);
    System.out.println(output);

    //emoji in category
    output = getUrlContents(host + "categories/smileys-emotion" + key);
    List<String> cat = Arrays.asList(output.split("\\s*,\\s*"));
    //System.out.println(output);

    //get code for multiple emoji
    Object unit = cat.get(3);
    for(int i = 3; i<cat.size(); i+=6){
      unit = cat.get(i);
      unit = strip((String) unit);
      System.out.println(unit);
    }

    //curl getting image
    String command = "curl -X GET https://emojiapi.dev/api/v1/" + unit + "/512.png --output thisemoji.png";
    ProcessBuilder im = new ProcessBuilder(command.split(" "));
    im.directory(new File("memory-game/java/"));
    Process process;
    try {
      process = im.start();
      InputStream inputstream = process.getInputStream();
      int exitCode=process.exitValue();
      process.destroy();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //var question = getUrlContents("https://emojiapi.dev/api/v1/slightly_smiling_face/512.png");
    //System.out.println(question);


    //one emoji
    output = getUrlContents(host + "emojis/grinning-squinting-face" + key);
    System.out.println(output);

    //unicode
    List<String> select = Arrays.asList(output.split("\\s*,\\s*"));
    var em = select.get(1);
    
    //codepoint
    var cp =select.get(3);

    //print info
    em = em.replace("character\":","");
    em = em.replace("\"","");
    cp = cp.replace("codePoint","");
    cp = cp.replace("\"","");
    cp = cp.replace(":","");//0x
    System.out.println(em);
    System.out.println(cp);

    //utc-8
    int cc = Integer.parseInt(cp,16);
    System.out.println(cc);
    System.out.println("&#"+cc);//Character.toChars(
    
    //direct print emoji to terminal
    PrintWriter uc = new PrintWriter(System.out,true);
    String two = "&#" + cc;
    uc.println(two);
    uc.close();

    //get args info
    if(args.length != 0){
      output = getUrlContents(host + args[0] + key);
      System.out.println("here");
    }
  }

  //strip
  private static String strip(String cp)
  {
    cp = cp.replace("codePoint","");
    cp = cp.replace("\"","");
    cp = cp.replace(":","");//0x
    return(cp);
  }

  private static String getUrlContents(String theUrl)
  {
    StringBuilder content = new StringBuilder();
  // Use try and catch to avoid the exceptions
    try
    {
      URL url = new URL(theUrl); // creating a url object
      URLConnection urlConnection = url.openConnection(); // creating a urlconnection object
      // wrapping the urlconnection in a bufferedreader  
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      String line;
      // reading from the urlconnection using the bufferedreader
      while ((line = bufferedReader.readLine()) != null){content.append(line + "\n");}
      bufferedReader.close();
    }
    catch(Exception e){e.printStackTrace();}
    return content.toString();
  }
}