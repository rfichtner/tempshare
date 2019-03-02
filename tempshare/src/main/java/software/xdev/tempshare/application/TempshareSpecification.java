package software.xdev.tempshare.application;

import java.nio.file.Path;
import java.time.Duration;


public class TempshareSpecification
{
	final Path root;
	final Duration maximumFileAge;
	final ApplicationMode applicationMode;
	final ReadOnlyStatus readOnlyStatus;
	
	public TempshareSpecification(
		final Path root,
		final Duration maximumFileAge,
		final ApplicationMode applicationMode,
		final ReadOnlyStatus readOnlyStatus)
	{
		super();
		this.root = root;
		this.maximumFileAge = maximumFileAge;
		this.applicationMode = applicationMode;
		this.readOnlyStatus = readOnlyStatus;
	}
	
	public Path getRoot()
	{
		return this.root;
	}
	
	public Duration getMaximumFileAge()
	{
		return this.maximumFileAge;
	}
	
	public ApplicationMode getApplicationMode()
	{
		return this.applicationMode;
	}
	
	public ReadOnlyStatus getReadOnlyStatus()
	{
		return this.readOnlyStatus;
	}
	
}
