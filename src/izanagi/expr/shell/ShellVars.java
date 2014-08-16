package izanagi.expr.shell;

import java.util.HashMap;
import java.util.Map;

public class ShellVars
{
	private final HashMap<String, ShellVar> mVar;

	public ShellVars()
	{
		mVar = new HashMap<String, ShellVar>();
	}

	public ShellVar get(String name)
	{
		return (mVar.get(name));
	}

	public void set(String name, ShellValue shellValue)
	{
		if (mVar.containsKey(name)){
			ShellVar var = mVar.get(name);
			var.setValue(shellValue);
		}
		else {
			mVar.put(name, new ShellVar(name, shellValue));
		}
	}

	public boolean usedName(String name)
	{
		return (mVar.containsKey(name));
	}

	public void dump()
	{
		for (Map.Entry<String, ShellVar> e : mVar.entrySet()){
			e.getValue().dump();
		}
	}
}

