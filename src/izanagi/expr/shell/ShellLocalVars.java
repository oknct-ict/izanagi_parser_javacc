package izanagi.expr.shell;

import java.util.Stack; 

public class ShellLocalVars
{
	private static ShellLocalVars instance = new ShellLocalVars();
	private final Stack<ShellVars> mVarsStack;

	private ShellLocalVars()
	{
		mVarsStack = new Stack<ShellVars>();
		push();
	}
	public static ShellLocalVars getInstance()
	{
		return (instance);
	}

	public void push()
	{
		ShellVars vars = new ShellVars();

		mVarsStack.push(vars);
	}
	public void pop()
	{
		mVarsStack.pop();
	}

	public ShellVar get(String name)
	{
		ShellVars vars = mVarsStack.peek();
		return (vars.get(name));
	}

	public void set(String name, ShellValue shellValue)
	{
		ShellVars vars = mVarsStack.peek();
		
		vars.set(name, shellValue);
	}

	public boolean usedName(String name)
	{
		ShellVars vars = mVarsStack.peek();

		return (vars.usedName(name));
	}

	public void dump()
	{
		ShellVars vars = mVarsStack.peek();

		vars.dump();
	}
}
