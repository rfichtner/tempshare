package software.xdev.tempshare;

import org.junit.jupiter.api.Test;

import software.xdev.tempshare.interfaces.cli.CliUserInterface;

class TestCliUserInterface
{
	

	@Test
	void testCompute()
	{
		
		final CliUserInterface cli = new CliUserInterface(new String[] {"--path", "C:\\temp", "--age", "60"});
		
		cli.compute();
		
		
		
	}
	
}
