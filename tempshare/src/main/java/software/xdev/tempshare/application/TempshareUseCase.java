package software.xdev.tempshare.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.xdev.tempshare.domain.FileSet;


public class TempshareUseCase
{
	final TempshareSpecification tempshareSpecification;
	
	private static final Logger LOG = LoggerFactory.getLogger(TempshareUseCase.class);
	
	public TempshareUseCase(final TempshareSpecification tempshareSpecification)
	{
		super();
		this.tempshareSpecification = tempshareSpecification;
	}
	
	public void run() throws IOException
	{
		final FileSet fileSet = new FileSet(
			this.tempshareSpecification.getReadOnlyStatus(),
			this.tempshareSpecification.getMaximumFileAge(),
			this.tempshareSpecification.getRoot());
		
		final List<Path> files = fileSet.scan();
		
		LOG.info("Files that reached the best before date {}", files);
		
		switch(this.tempshareSpecification.getApplicationMode())
		{
			
			case ANALYZE:
				// do nothing :)
				LOG.info("This is a dry run - NO DELETIONS ARE MADE");
				break;
			
			case EXECUTE:
				LOG.info("Deleting files");
				this.delete(files);
				LOG.info("Files were successfully deleted");
				break;
			
			default:
				throw new EnumConstantNotPresentException(
					ApplicationMode.class,
					this.tempshareSpecification.getApplicationMode().name());
				
		}
		
	}
	
	private void delete(final List<Path> files) throws IOException
	{
		
		for(final Path path : files)
		{
			Files.delete(path);
		}
	}
	
}
