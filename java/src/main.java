import java.util.*;
import java.lang.*;
import java.io.*;
 
class Ideone
{
	public static void main (String[] args) throws java.lang.Exception
	{
        List<String> uni = new ArrayList<>();
        uni.add("\uD83D\uDE00");
        String[] s = new String[] {uni.get(0)};
        System.out.println(s.toString());
        var arraypic = new String[]{s[0],"\uD83D\uDE00"};
        System.out.println(Arrays.toString(arraypic));
        List<String> strList = Arrays.asList(arraypic);
        arraypic = strList.toArray(new String[0]);	
	}
}