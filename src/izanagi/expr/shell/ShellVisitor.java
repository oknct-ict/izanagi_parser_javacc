package izanagi.expr.shell;

import izanagi.expr.parser.*;
import izanagi.expr.shell.*;
import java.io.*;

public class ShellVisitor implements ExprParserVisitor
{
	private final ShellVars mVars;

	public ShellVisitor()
	{
		//mVars = new ShellVars();
		mVars = ShellVars.getInstance();
	}

	public Object visit(SimpleNode node, Object data)
	{
		return (null);
	}

	public Object visit(ASTStart node, Object data)
	{
		return (node.jjtGetChild(0).jjtAccept(this, null));
	}

	public Object visit(ASTDimStmt node, Object data)
	{
		String type = node.nodeValue;
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue value = new ShellValue("", ShellValue.TYPE_NONE);

		value.setType(type);
		mVars.set(name, value);

		System.out.println("mVars dump---------");
		mVars.dump();
		System.out.println("mVars dump---------");

		return (value);
	}

	public Object visit(ASTAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue value = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		mVars.set(name, value);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTAddAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.add(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTSubAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.sub(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTMulAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.mul(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTDivAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.div(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTModAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.mod(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTPowAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.power(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
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

	public Object visit(ASTVar node, Object data)
	{
		String name = node.nodeValue;
		
		return (name);
	}
	public Object visit(ASTRefVar node, Object data)
	{
		String name = node.nodeValue;
		
		if (mVars.usedName(name)){
			System.out.println("exist var");
			return (mVars.get(name).getValue());
		}
		else {
			System.out.println("not exist var");
			ShellValue shellValue = new ShellValue("", ShellValue.TYPE_NONE);
			return (shellValue);
		}
	}
}
