package izanagi.expr.variable;

import java.util.List;
import java.util.ArrayList;

public class IzaArray implements IzaBasic
{
	public ArrayList<IzaBasic> mValue;
	public int mType;
	public ArrayList<Integer> mArraySizes;

	public IzaArray()
	{
		mType = TYPE_ARRAY;
	}
	public IzaArray(IzaArray izaArray)
	{
		mValue = new ArrayList<IzaBasic>(izaArray.mValue);
		mType = TYPE_ARRAY;
		mArraySizes = new ArrayList<Integer>(izaArray.mArraySizes);
	}
	public IzaArray(ArrayList<Integer> arraySizes, int type)
	{
		mType = TYPE_ARRAY;
		mArraySizes = arraySizes;
		int totalSize = 1;

		for (int i = 0; i < mArraySizes.size(); i++){
			totalSize *= mArraySizes.get(i);
		}

		mValue = new ArrayList<IzaBasic>(totalSize);
	}
	public IzaArray(String index, int type)
	{
		List<Integer> arraySizes = toArraySizes(index);
		mType = TYPE_ARRAY;
		int totalSize = 1;

		for (int i = 0; i < mArraySizes.size(); i++){
			totalSize *= mArraySizes.get(i);
		}

		mValue = new ArrayList<IzaBasic>(totalSize);
	}
	public void setArray(String index)
	{
		List<Integer> arraySizes = toArraySizes(index);
		int totalSize = 1;

		for (int i = 0; i < mArraySizes.size(); i++){
			totalSize *= mArraySizes.get(i);
		}

		mValue = new ArrayList<IzaBasic>(totalSize);
	}

	private ArrayList<Integer> toArraySizes(String index)
	{
		String[] sizes = index.split(",");
		ArrayList<Integer> arraySizes = new ArrayList<Integer>(sizes.length);

		for (int i = 0; i < sizes.length; i++){
			arraySizes.add(i, Integer.valueOf(sizes[i]));
		}

		return (arraySizes);
	}
	private int calcIndex(ArrayList<Integer> indexList)
	{
		int index = 0;

		if (mArraySizes.size() != indexList.size()){
			System.out.println("添字の数がおかしいです");
			return (-1);
		}

		for (int i = 0; i < indexList.size(); i++){
			index += indexList.get(i);
			index *= mArraySizes.get(i);
		}

		return (index);
	}

	public IzaBasic add(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic sub(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic mul(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic div(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic mod(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic pow(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic and(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic or(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic not(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}

	public IzaBoolean EQ(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean NEQ(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean GT(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean GE(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean LT(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean LE(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean BAnd(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean BOr(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}

	public IzaBasic minus()
	{
		System.out.println("許されない演算です");
		return (null);
	}

	public IzaBasic subscript(String index)
	{
		int num = calcIndex(toArraySizes(index));

		return (mValue.get(num));
	}

	public IzaBasic cast(int type)
	{
		System.out.println("キャスト出来ません");
		return (null);
	}
	public int getType()
	{
		return (mType);
	}
	public IzaBasic clone()
	{
		return (new IzaArray(this));
	}

	public String toString()
	{
		return ("" + mValue);
	}
}
