import sys;
import os;

def _run():
	os.chdir("build/classes/");
	os.system("cat StandardFunc.iz Main.iz > Shell.iz");
	os.system("java tk.oknctict.izanagi.Expr");

def _compile():
	os.system("ant cc");
	os.system("ant compile");

if __name__ == "__main__":
	args = sys.argv;
	argc = len(args);

	if (argc >= 2):
		if (args[1] == "run"):
			_run();
		elif (args[1] == "compile"):
			_compile();
	elif (argc == 1):
		_compile();
		_run();

