package software.xdev.tempshare;

import java.io.IOException;

import software.xdev.tempshare.interfaces.cli.CliUserInterface;


public final class Application
{
	
	private Application()
	{
		// no instances plz
	}
	
	public static void main(final String[] args) throws IOException
	{
		final CliUserInterface cliUserInterface = new CliUserInterface(args);
		cliUserInterface.compute();
	}
}
