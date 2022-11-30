import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class records{


private static FileOutputStream w;

	static{
		try{
		w = new FileOutputStream("recordboard.txt",true);
		byte[] b = {'\r', '@', '@'};
		w.write(b);
		} catch(IOException ignored){}
	}

	public static void writeboard (int moves, int time) {
		try {
			w.write(moves);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//text.getBytes());
	}

/* 
	public static void readboard (int moves, int time) {
		try {
			W.write(new byte[]{'\n','#'});
		   } catch (IOException ignored) {
		   }
		   long seed = rand.nextLong();
		   if (args.length != 0) {
			try {
			 I = new Scanner(new File(args[0])).useDelimiter(",|\r\n|\\w*_|\\s+");
			} catch (FileNotFoundException e) {
			 I = new Scanner(Arrays.toString(args).replaceAll("[\\[\\]]|, |,|\\w*_", " "));
			}
			if (I.hasNextLong())
			 seed = Long.parseLong(getInput());
		   } else try {
			W.write(String.valueOf(seed).getBytes());
			W.write(' ');
		   } catch (IOException ignored) {
		   }
		   rand = new Random(seed);
		  }
	} */

}