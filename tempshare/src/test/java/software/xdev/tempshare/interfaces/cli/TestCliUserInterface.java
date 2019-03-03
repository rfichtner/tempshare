package software.xdev.tempshare.interfaces.cli;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;


class TestCliUserInterface
{
	
	@Rule
	TemporaryFolder tempFolder = new TemporaryFolder();
	
	@BeforeEach
	void setup() throws IOException
	{
		
		this.tempFolder.create();
		
		this.tempFolder.newFile("fileFromToday.txt");
		
		final File file10Days = this.tempFolder.newFile("10daysOld.txt");
		file10Days.setLastModified(LocalDateTime.now().minusDays(10).toInstant(ZoneOffset.UTC).toEpochMilli());
		
		final File file10DaysWithReadOnly = this.tempFolder.newFile("10daysOldWithReadOnly.txt");
		file10DaysWithReadOnly.setLastModified(
			LocalDateTime.now().minusDays(10).toInstant(ZoneOffset.UTC).toEpochMilli());
		file10DaysWithReadOnly.setReadOnly();
		
		final File file3Day = this.tempFolder.newFile("3daysOld.txt");
		file3Day.setLastModified(LocalDateTime.now().minusDays(3).toInstant(ZoneOffset.UTC).toEpochMilli());
		
		final File file3DayWithReadOnly = this.tempFolder.newFile("3daysOldWithReadOnly.txt");
		file3DayWithReadOnly.setLastModified(LocalDateTime.now().minusDays(3).toInstant(ZoneOffset.UTC).toEpochMilli());
		file3DayWithReadOnly.setReadOnly();
		
		final File file1Day = this.tempFolder.newFile("1dayOld.txt");
		file1Day.setLastModified(LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC).toEpochMilli());
		
		final File file1DayWithReadOnly = this.tempFolder.newFile("1dayOldWithReadOnly.txt");
		file1DayWithReadOnly.setLastModified(LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC).toEpochMilli());
		file1DayWithReadOnly.setReadOnly();
		
		
		final File dir10daysOld = this.tempFolder.newFolder("10daysOld");
		dir10daysOld.setLastModified(LocalDateTime.now().minusDays(10).toInstant(ZoneOffset.UTC).toEpochMilli());
		
		final File dir1dayOld = this.tempFolder.newFolder("1dayOld");
		dir1dayOld.setLastModified(LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC).toEpochMilli());
		
		
		final File dir10daysOldWithFile = this.tempFolder.newFolder("10daysOldWithFile");
		dir10daysOldWithFile.setLastModified(LocalDateTime.now().minusDays(10).toInstant(ZoneOffset.UTC).toEpochMilli());
		final File dir10daysChild = new File(dir10daysOldWithFile, "child.txt");
		dir10daysChild.createNewFile();
		
		
	}
	
	@AfterEach
	void tearDown()
	{
		this.tempFolder.delete();
	}
	
	@Test
	void testCompute()
	{
		
		final CliUserInterface cli = new CliUserInterface(
			new String[]{
				"--path",
				this.tempFolder.getRoot().toString(),
				"--age",
				"60",
				"--applicationMode",
				"ANALYZE",
				"--readOnlyStatus",
				"IGNORE"});
		
		cli.compute();
		
	}
	
}
