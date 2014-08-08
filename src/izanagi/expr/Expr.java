package izanagi.expr;

import izanagi.expr.parser.*;
import izanagi.expr.shell.*;
import java.io.*;

public class Expr implements ExprParserVisitor
{
	public static void main(String[] args) throws ParseException, IOException 
	{
		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(in);
		String line;
		while ((line = reader.readLine()) != null){
			ExprParser parser = new ExprParser(new StringReader(line));
			Expr visitor = new Expr();
			ASTStart start = parser.Start();
			System.out.println(start.jjtAccept(visitor, null));
		}
	}

	public Object visit(SimpleNode node, Object data)
	{
		return (null);
	}

	public Object visit(ASTStart node, Object data)
	{
		return (node.jjtGetChild(0).jjtAccept(this, null));
	}

	public Object visit(ASTEq node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.EQ(right);

		return (left);
	}

	public Object visit(ASTNEq node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.NEQ(right);

		return (left);
	}

	public Object visit(ASTGe node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);
		
		left.GE(right);

		return (left);
	}
	public Object visit(ASTGt node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.GT(right);

		return (left);
	}
	public Object visit(ASTLe node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.LE(right);

		return (left);
	}
	public Object visit(ASTLt node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.LT(right);

		return (left);
	}

	public Object visit(ASTAdd node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.add(right);

		return (left);
	}

	public Object visit(ASTSub node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.sub(right);

		return (left);
	}

	public Object visit(ASTMul node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.mul(right);

		return (left);
	}

	public Object visit(ASTDiv node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.div(right);

		return (left);
	}

	public Object visit(ASTMod node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.mod(right);

		return (left);
	}

	public Object visit(ASTPower node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.power(right);

		return (left);
	}

	public Object visit(ASTAnd node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.and(right);

		return (left);
	}

	public Object visit(ASTOr node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.or(right);

		return (left);
	}

	public Object visit(ASTNot node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.not(right);

		return (left);
	}

	public Object visit(ASTUniExpr node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		String value = node.nodeValue;

		if (value.equals("-")){
			left.minus();
		}

		return (left);
	}

	public Object visit(ASTInteger node, Object data)
	{
		return (new ShellValue(node.nodeValue, ShellValue.TYPE_INTEGER));
	}

	public Object visit(ASTFloat node, Object data)
	{
		return (new ShellValue(node.nodeValue, ShellValue.TYPE_FLOAT));
	}

	public Object visit(ASTString node, Object data)
	{
		return (new ShellValue(node.nodeValue, ShellValue.TYPE_STRING));
	}

}
