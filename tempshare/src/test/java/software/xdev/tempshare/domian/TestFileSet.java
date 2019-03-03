package software.xdev.tempshare.domian;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;

import org.junit.jupiter.api.Test;

import software.xdev.tempshare.domain.FileSet;
import software.xdev.tempshare.domain.ReadOnlyStatus;


class TestFileSet
{
	
	@Test
	void testScan() throws IOException
	{
		final FileSet fileSet = new FileSet(ReadOnlyStatus.RESPECT, Duration.ofDays(1), Paths.get("C:\\temp"));
		fileSet.scan();
		
	}
	
}
