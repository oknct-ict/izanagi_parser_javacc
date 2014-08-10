package izanagi.expr.shell;

public class ShellVar
{
	private String mName;
	private ShellValue mValue;

	public ShellVar(String name, ShellValue shellValue)
	{
		set(name, shellValue);
	}

	public void set(String name, ShellValue shellValue)
	{
		setName(name);
		setValue(shellValue);
	}

	public String getName()
	{
		return (mName);
	}
	public void setName(String name)
	{
		mName = name;
	}

	public ShellValue getValue()
	{
		return (mValue);
	}
	public void setValue(ShellValue shellValue)
	{
		mValue = shellValue;
	}

	public void dump()
	{
		System.out.println(mName + " = (" + mValue.toString() + ")");
	}
}
