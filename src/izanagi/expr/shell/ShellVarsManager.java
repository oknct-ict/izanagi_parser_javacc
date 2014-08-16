package izanagi.expr.shell;

public class ShellVarsManager
{
	private static ShellVarsManager instance = new ShellVarsManager();
	private final ShellGlobalVars mGVars;
	private final ShellLocalVars mLVars;
	private int countInFunc;
	
	private ShellVarsManager()
	{
		mGVars = ShellGlobalVars.getInstance();
		mLVars = ShellLocalVars.getInstance();
		countInFunc = 0;
	}
	public static ShellVarsManager getInstance()
	{
		return (instance);
	}

	public void intoFunc()
	{
		countInFunc++;
		mLVars.push();
	}
	public void outFunc()
	{
		countInFunc--;
		mLVars.pop();
	}

	public ShellVar get(String name)
	{
		if (mLVars.usedName(name)){
			return (mLVars.get(name));
		}

		return (mGVars.get(name));
	}
	public void set(String name, ShellValue shellValue)
	{
		if (countInFunc > 0){
			mLVars.set(name, shellValue);
		}
		else {
			mGVars.set(name, shellValue);
		}
	}

	public boolean usedName(String name)
	{
		if (mLVars.usedName(name)){
			return (true);
		}
		if (mGVars.usedName(name)){
			return (true);
		}

		return (false);
	}
}
