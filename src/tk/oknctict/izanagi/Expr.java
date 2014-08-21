package tk.oknctict.izanagi;

import tk.oknctict.izanagi.parser.*;
import tk.oknctict.izanagi.shell.*;
import java.io.*;

public class Expr 
{

	public static void main(String[] args) throws ParseException, IOException 
	{
		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(in);
		ShellInterface shellInterface = ShellInterface.getInstance();
		String line;
		//while ((line = reader.readLine()) != null){
			//ExprParser parser = new ExprParser(new StringReader(line));
			//File file = new File("Shell.txt");
			ExprParser parser = new ExprParser(new FileReader("Shell.iz"));
			ShellVisitor visitor = new ShellVisitor();
			ASTStart start = parser.Start();
			System.out.println(start.jjtAccept(visitor, null));

			shellInterface.callFunc("on_click");
		//}
	}


}
