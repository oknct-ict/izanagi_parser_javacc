package tk.oknctict.izanagi.shell;

import java.util.ArrayList;
import tk.oknctict.izanagi.variable.*;
import tk.oknctict.izanagi.shell.*;
import tk.oknctict.izanagi.parser.*;

public class ShellInterface
{
	private static final ShellInterface instance = new ShellInterface();

	private final String FUNC_GET_X 	 = "stdGetX";
	private final String FUNC_SET_X 	 = "stdSetX";
	private final String FUNC_GET_Y 	 = "stdGetY";
	private final String FUNC_SET_Y 	 = "stdSetY";
	private final String FUNC_GET_WIDTH  = "stdGetWidth";
	private final String FUNC_SET_WIDTH  = "stdSetWidth";
	private final String FUNC_GET_HEIGHT = "stdGetHeight";
	private final String FUNC_SET_HEIGHT = "stdSetHeight";
	private final String FUNC_GET_TEXT	 = "stdGetText";
	private final String FUNC_SET_TEXT	 = "stdSetText";

	private ShellVarsManager mVarsMng;
	private ShellFuncs mFuncs;

	private ShellVisitor mVisitor;

	private ShellInterface()
	{
		mVarsMng = ShellVarsManager.getInstance();
		mFuncs = ShellFuncs.getInstance();
		mVisitor = new ShellVisitor();
	}
	public static ShellInterface getInstance()
	{
		return (instance);
	}

	public void setEvent(String name, IzaEvent event)
	{
		setEvent(name, event.getFuncName(), event.getBlock(), event.getEventType());
	}
	public void setEvent(String name, String funcName, ASTFuncBlock block, String eventType)
	{
		int event = toEventType(eventType);

		setEvent(name, funcName, block, event);
	}
	public void setEvent(String name, String funcName, ASTFuncBlock block, int eventType)
	{
		IzaBasic view = new IzaNone();

		if (mVarsMng.usedName(name) == false){
			System.out.println("not find view");
			return;
		}

		view = mVarsMng.get(name).getValue();
		if ((view instanceof IzaView) == false){
			System.out.println("type is not view");
			return;
		}

		IzaEvent event = new IzaEvent(funcName, block, eventType);
		((IzaView)view).setEvent(event);
		ShellArgs args = new ShellArgs();
		ShellFunc func = new ShellFunc(name, args, view.getType(), block);

		mFuncs.set(funcName, func);
	}

	public void callEvent(String funcName)
	{
		ShellFunc func = mFuncs.get(funcName);
		mVisitor.visit(func.getBlock(), null);
	}

	public IzaBasic callStdFunc(String funcName, ArrayList<IzaBasic> valueList)
	{
		IzaBasic returnValue= new IzaNone();
		IzaBasic view = new IzaNone();
		IzaBasic value = valueList.get(0);
		String name;

		if ((value instanceof IzaString) == false){
			return (returnValue);
		}
		name = ((IzaString)value).mValue;

		if (mVarsMng.usedName(name) == false){
			return (returnValue);
		}

		view = mVarsMng.get(name).getValue();
		if ((view instanceof IzaView) == false){
			return (returnValue);
		}

		if (funcName.equals(FUNC_GET_X)){
			float x = ((IzaView)view).getX();
			returnValue = new IzaFloat(x);
		}
		else if (funcName.equals(FUNC_SET_X)){
			float x = ((IzaFloat)valueList.get(1)).mValue;
			((IzaView)view).setX(x);
			returnValue = new IzaFloat();
		}
		else if (funcName.equals(FUNC_GET_Y)){
			float y = ((IzaView)view).getY();
			returnValue = new IzaFloat(y);
		}
		else if (funcName.equals(FUNC_SET_Y)){
			float y = ((IzaFloat)valueList.get(1)).mValue;
			((IzaView)view).setY(y);
			returnValue = new IzaFloat();
		}
		else if (funcName.equals(FUNC_GET_WIDTH)){
			float width = ((IzaView)view).getWidth();
			returnValue = new IzaFloat(width);
		}
		else if (funcName.equals(FUNC_SET_WIDTH)){
			float width = ((IzaFloat)valueList.get(1)).mValue;
			((IzaView)view).setWidth(width);
			returnValue = new IzaFloat();
		}
		else if (funcName.equals(FUNC_GET_HEIGHT)){
			float height = ((IzaView)view).getHeight();
			returnValue = new IzaFloat(height);
		}
		else if (funcName.equals(FUNC_SET_HEIGHT)){
			float height = ((IzaFloat)valueList.get(1)).mValue;
			((IzaView)view).setHeight(height);
			returnValue = new IzaFloat();
		}
		else if (funcName.equals(FUNC_GET_TEXT)){
			String text = ((IzaView)view).getText();
			returnValue = new IzaString(text);
		}
		else if (funcName.equals(FUNC_SET_TEXT)){
			String text = ((IzaString)valueList.get(1)).mValue;
			((IzaView)view).setText(text);
			returnValue = new IzaString();
		}

		return (returnValue);
	}

	private int toEventType(String str)
	{
		if (str.equals(IzaEvent.EVENT_CLICK_NAME)){
			return (IzaEvent.EVENT_CLICK);
		}

		return (IzaEvent.EVENT_NON);
	}
}
