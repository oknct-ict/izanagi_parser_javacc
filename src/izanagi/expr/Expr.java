package izanagi.expr;

import izanagi.expr.parser.*;
import izanagi.expr.shell.*;
import java.io.*;

public class Expr 
{

	public static void main(String[] args) throws ParseException, IOException 
	{
		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(in);
		String line;
		while ((line = reader.readLine()) != null){
			ExprParser parser = new ExprParser(new StringReader(line));
			//File file = new File("Shell.txt");
			ShellVisitor visitor = new ShellVisitor();
			ASTStart start = parser.Start();
			System.out.println(start.jjtAccept(visitor, null));
		}
	}


}
