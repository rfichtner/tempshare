package software.xdev.tempshare.interfaces.cli;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.xdev.tempshare.application.ApplicationMode;
import software.xdev.tempshare.application.TempshareSpecification;
import software.xdev.tempshare.application.TempshareUseCase;
import software.xdev.tempshare.domain.ReadOnlyStatus;


public class CliUserInterface
{
	
	private static final Logger LOG = LoggerFactory.getLogger(CliUserInterface.class);
	
	private final String[] args;
	
	public CliUserInterface(final String[] args)
	{
		this.args = args;
		
	}
	
	public void compute()
	{
		
		final Options options = new Options();
		
		final Option optionPath = Option.builder().longOpt("path").hasArg().desc("root path").required().build();
		options.addOption(optionPath);
		
		final Option optionAge =
			Option.builder().longOpt("age").hasArg().desc("maximum age of files in minutes").required().build();
		options.addOption(optionAge);
		
		final Option optionApplicationMode =
			Option.builder().longOpt("applicationMode").hasArg().desc(
				"Application mode: ANALYZE | EXECUTE").required().build();
		options.addOption(optionApplicationMode);
		
		final Option optionReadOnlyStatus =
			Option.builder().longOpt("readOnlyStatus").hasArg().desc(
				"Should the read only flag be respected: IGNORE | RESPECT").required().build();
		options.addOption(optionReadOnlyStatus);
		
		// create the parser
		final CommandLineParser parser = new DefaultParser();
		try
		{
			// parse the command line arguments
			final CommandLine line = parser.parse(options, this.args, true);
			
			final Path root = Paths.get(line.getOptionValue(optionPath.getLongOpt()));
			final Duration maxAge = Duration.ofMinutes(Long.valueOf(line.getOptionValue(optionAge.getLongOpt())));
			final ApplicationMode applicationMode =
				ApplicationMode.valueOf(line.getOptionValue(optionApplicationMode.getLongOpt()));
			
			final ReadOnlyStatus readOnlyStatus =
				ReadOnlyStatus.valueOf(line.getOptionValue(optionReadOnlyStatus.getLongOpt()));
			
			final TempshareSpecification specification =
				new TempshareSpecification(root, maxAge, applicationMode, readOnlyStatus);
			
			final TempshareUseCase tempshareUseCase = new TempshareUseCase(specification);
			tempshareUseCase.run();
		}
		catch(final ParseException ex)
		{
			// oops, something went wrong
			
			LOG.error("Parsing failed. Reason: " + ex.getMessage(), ex);
			final HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("tempshare", options);
		}
		catch(final IOException e)
		{
			LOG.error("Could not perform all operations. See log.", e);
		}
	}
	
}
