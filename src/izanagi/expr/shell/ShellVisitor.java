package izanagi.expr.shell;

import izanagi.expr.parser.*;
import izanagi.expr.shell.*;
import java.io.*;
import java.util.ArrayList;

public class ShellVisitor implements ExprParserVisitor
{
	private final ShellVars mVars;
	private final ShellFuncs mFuncs;
	private boolean mBreak;
	private boolean mContinue;

	public ShellVisitor()
	{
		//mVars = new ShellVars();
		mVars = ShellVars.getInstance();
		mFuncs = ShellFuncs.getInstance();
		mBreak = false;
		mContinue = false;
	}

	public Object visit(SimpleNode node, Object data)
	{
		return (null);
	}

	public Object visit(ASTStart node, Object data)
	{
		return (node.jjtGetChild(0).jjtAccept(this, null));
	}

	public Object visit(ASTStmts node, Object data)
	{
		int size = node.jjtGetNumChildren();
		for (int i = 0; i < size; i++){
			node.jjtGetChild(i).jjtAccept(this, null);
		}

		ShellValue returnValue = new ShellValue("" + size, ShellValue.TYPE_INTEGER);
		return (returnValue);
	}

	public Object visit(ASTStmt node, Object data)
	{
		int size = node.jjtGetNumChildren();
		for (int i = 0; i < size; i++){
			node.jjtGetChild(i).jjtAccept(this, null);
		}

		ShellValue returnValue = new ShellValue("" + size, ShellValue.TYPE_INTEGER);
		return (returnValue);
	}

	public Object visit(ASTBlock node, Object data)
	{
		int size = node.jjtGetNumChildren();
		for (int i = 0; i < size; i++){
			node.jjtGetChild(i).jjtAccept(this, null);
		}
		
		ShellValue returnValue = new ShellValue("" + size, ShellValue.TYPE_INTEGER);
		return (returnValue);
	}
	public Object visit(ASTWhileBlock node, Object data)
	{
		int size = node.jjtGetNumChildren();
		for (int i = 0; i < size; i++){
			if (mContinue == true){
				mContinue = false;
				break;
			}
			if (mBreak == true){
				break;
			}
			node.jjtGetChild(i).jjtAccept(this, null);
		}
		
		ShellValue returnValue = new ShellValue("" + size, ShellValue.TYPE_INTEGER);
		return (returnValue);
	}
	public Object visit(ASTFuncBlock node, Object data)
	{
		int size = node.jjtGetNumChildren();
		for (int i = 0; i < size; i++){
			node.jjtGetChild(i).jjtAccept(this, null);
		}
		
		ShellValue returnValue = new ShellValue("" + size, ShellValue.TYPE_INTEGER);
		return (returnValue);
	}

	public Object visit(ASTDefFunc node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellArgs args = (ShellArgs)node.jjtGetChild(1).jjtAccept(this, null);
		String type = node.nodeValue;
		ASTFuncBlock block = (ASTFuncBlock)node.jjtGetChild(2);

		ShellFunc func = new ShellFunc(name, args, type, block);
		mFuncs.set(name, func);

		return (null);
	}
	public Object visit(ASTCallFunc node, Object data)
	{
		int size = node.jjtGetNumChildren();
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellFunc shellFunc = mFuncs.get(name);
		ArrayList<ShellArg> argList = shellFunc.getArgs().getArgList();
		ArrayList<ShellValue> valueList = new ArrayList<ShellValue>();

		if (shellFunc == null){
			System.out.println("定義されていない関数です");
			return (null);
		}

		for (int i = 1; i < size; i++){
			ShellValue value = (ShellValue)node.jjtGetChild(i).jjtAccept(this, null);
			valueList.add(value);
		}

		if (shellFunc.checkArgs(valueList) == false){
			return (null);
		}
		
		for (int i = 0; i < valueList.size(); i++){
			mVars.set(argList.get(i).getName(), valueList.get(i));
		}

		visit(shellFunc.getBlock(), data);
		return (null);
	}

	public Object visit(ASTIfStmt node, Object data)
	{
		//First child points if condition, second points if body,
		//third points elseif condition, fourth points elseif body,
		//and the last child points else body

		int size;
		size = node.jjtGetNumChildren();
		for (int i = 0; i < size - 1; i += 2){
			ShellValue shellValue = (ShellValue)node.jjtGetChild(i).jjtAccept(this, data);
			String value = shellValue.getValue();

			if (value.equals("" + true)){
				node.jjtGetChild(i + 1).jjtAccept(this, null);
				return (shellValue);
			}
		}

		//Check which else condition is exisits
		if (size % 2 != 0){
			node.jjtGetChild(size - 1).jjtAccept(this, null);
		}

		return (null);
	}

	public Object visit(ASTWhileStmt node, Object data)
	{
		//First child points while condition, second points while body

		while (true){
			if (mBreak == true){
				mBreak = false;
				break;
			}

			ShellValue shellValue = (ShellValue)node.jjtGetChild(0).jjtAccept(this, data);
			if (shellValue.getValue().equals("" + false)){
				break;
			}

			node.jjtGetChild(1).jjtAccept(this, null);
		}

		return (null);
	}

	public Object visit(ASTSelectStmt node, Object data)
	{
		//First child points select condition, second points case condition
		//third points seca body, and last case else body

		int size;
		size = node.jjtGetNumChildren();

		ShellValue oriValue = (ShellValue)node.jjtGetChild(0).jjtAccept(this, data);
		for (int i = 1; i < size - 1; i += 2){
			ShellValue shellValue = (ShellValue)node.jjtGetChild(i).jjtAccept(this, data);
			String value = shellValue.getValue();

			if (oriValue.EQ(shellValue)){
				node.jjtGetChild(i + 1).jjtAccept(this, null);
				return (shellValue);
			}
		}

		if (size % 2 == 0){
			node.jjtGetChild(size - 1).jjtAccept(this, null);
		}

		return (null);
	}

	public Object visit(ASTDimStmt node, Object data)
	{
		String type = node.nodeValue;
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue value = new ShellValue("", ShellValue.TYPE_NONE);

		value.setType(type);
		mVars.set(name, value);

		return (value);
	}

	public Object visit(ASTBreakStmt node, Object data)
	{
		mBreak = true;

		return (null);
	}

	public Object visit(ASTContinueStmt node, Object data)
	{
		mContinue = true;

		return (null);
	}

	public Object visit(ASTPrintStmt node, Object data)
	{
		ShellValue value = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);

		System.out.println(value.getValue());

		return (value);
	}

	public Object visit(ASTAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue value = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		int type = mVars.get(name).getType();
		value.setType(type);

		mVars.set(name, value);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTAddAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		int type = mVars.get(name).getType();
		right.setType(type);

		left.add(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTSubAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		int type = mVars.get(name).getType();
		right.setType(type);

		left.sub(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTMulAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		int type = mVars.get(name).getType();
		right.setType(type);

		left.mul(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTDivAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		int type = mVars.get(name).getType();
		right.setType(type);

		left.div(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTModAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		int type = mVars.get(name).getType();
		right.setType(type);

		left.mod(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
	}
	public Object visit(ASTPowAssign node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue left = mVars.get(name).getValue();
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		int type = mVars.get(name).getType();
		right.setType(type);

		left.power(right);

		mVars.set(name, left);

		return (mVars.get(name).getValue());
	}

	public Object visit(ASTBAnd node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.BAnd(right);

		return (left);
	}
	public Object visit(ASTBOr node, Object data)
	{
		ShellValue left = (ShellValue)node.jjtGetChild(0).jjtAccept(this, null);
		ShellValue right = (ShellValue)node.jjtGetChild(1).jjtAccept(this, null);

		left.BOr(right);

		return (left);
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
			ShellValue shellValue = new ShellValue(mVars.get(name).getValue());
			return (shellValue);
		}
		else {
			ShellValue shellValue = new ShellValue("", ShellValue.TYPE_NONE);
			return (shellValue);
		}
	}

	public Object visit(ASTArguments node, Object data)
	{
		ShellArgs args = new ShellArgs();
		int size = node.jjtGetNumChildren();

		for (int i = 0; i < size; i++){
			ShellArg arg = (ShellArg)node.jjtGetChild(i).jjtAccept(this, null);
			args.add(arg);
		}

		return (args);
	}
	public Object visit(ASTArgument node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		String type = node.nodeValue;
		ShellArg arg = new ShellArg(name, type);

		return (arg);
	}

	public Object visit(ASTFuncName node, Object data)
	{
		String name = node.nodeValue;

		return (name);
	}
}
