package izanagi.expr.shell;

import java.util.HashMatp;
import java.util.Map;

public class ShellVars
{
	private final HashMap<String, ShellVar> mVar;

	public ShellVars()
	{
		mVar = new HashMap<ShellVar>();
	}

	public ShellVar get(String name)
	{
		return (mVar.get(name));
	}

	public void set(String name, ShellValue shellValue)
	{
		if (mVar.containsKey(name)){
			mVar.get(name).setValue(shellValue);
		}
		else {
			mVar.push(name, new ShellVar(name, shellValue));
		}
	}

	public void dump()
	{
		for (Map.Entry<String, ShellVar> e : mVar.entrySet()){
			e.getValue().dump();
		}
	}
}

