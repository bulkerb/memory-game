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
    //https://emoji-api.com/emojis?access_key=f48301a44b0c8d06490563f08004880e0de02e51
    String output  = getUrlContents("https://emoji-api.com/emojis/grinning-squinting-face?access_key=f48301a44b0c8d06490563f08004880e0de02e51");  
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
      while ((line = bufferedReader.readLine()) != null)  
      {  
        content.append(line + "\n");  
      }  
      bufferedReader.close();  
    }  
    catch(Exception e)  
    {  
      e.printStackTrace();  
    }  
    return content.toString();  
  }  
}  