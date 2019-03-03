package software.xdev.tempshare.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class FileSet
{
	private final List<Path> files = new ArrayList<>();
	
	private final ReadOnlyStatus readOnlyStatus;
	private final Duration maximumFileAge;
	private final Path root;
	
	public FileSet(final ReadOnlyStatus readOnlyStatus, final Duration maximumFileAge, final Path root)
	{
		super();
		this.readOnlyStatus = readOnlyStatus;
		this.maximumFileAge = maximumFileAge;
		this.root = root;
	}
	
	public List<Path> scan() throws IOException
	{
		
		try(Stream<Path> paths = Files.walk(this.root))
		{
			paths.filter(p -> p.toFile().isFile()).filter(this.before(this.maximumFileAge)).filter(
				this.readOnly(this.readOnlyStatus)).filter(p -> true).forEach(
				System.out::println);
		}
		
		return this.files;
	}
	
	private Predicate<Path> readOnly(final ReadOnlyStatus readOnlyStatus)
	{
		
		switch(readOnlyStatus)
		{
			
			case RESPECT:
				return path ->
				{
					try
					{
						final DosFileAttributeView attr = Files.getFileAttributeView(path, DosFileAttributeView.class);
						return !attr.readAttributes().isReadOnly();
					}
					catch(final IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				};
			case IGNORE:
				return p -> true;
			default:
				throw new EnumConstantNotPresentException(ReadOnlyStatus.class, readOnlyStatus.name());
				
		}
		
	}
	
	private Predicate<Path> before(final Duration maximumFileAge)
	{
		return path ->
		{
			try
			{
				final BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
				final LocalDateTime lastmodified =
					LocalDateTime.ofInstant(attr.lastModifiedTime().toInstant(), TimeZone.getDefault().toZoneId());
				return lastmodified.isBefore(LocalDateTime.now().minus(maximumFileAge));
			}
			catch(final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		};
	}
	
}
