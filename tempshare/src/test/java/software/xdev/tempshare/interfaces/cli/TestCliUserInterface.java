package software.xdev.tempshare.interfaces.cli;

import org.junit.jupiter.api.Test;

import software.xdev.tempshare.interfaces.cli.CliUserInterface;


class TestCliUserInterface
{
	
	@Test
	void testCompute()
	{
		
		final CliUserInterface cli = new CliUserInterface(
			new String[]{
				"--path",
				"C:\\temp",
				"--age",
				"60",
				"--applicationMode",
				"ANALYZE",
				"--readOnlyStatus",
				"IGNORE"});
		
		cli.compute();
		
	}
	
}