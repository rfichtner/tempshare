package software.xdev.tempshare;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MyFile
	extends File
{
	private final List<File> allFiles = new ArrayList();
	
	public MyFile(final String pathname)
	{
		super(pathname);
	}
	
	public MyFile(final File path)
	{
		super(path.getAbsolutePath());
	}
	
	public File[] listFilesRecursive()
	{
		this.collectFilesRecursive(this);
		return this.allFiles.toArray(new File[this.allFiles.size()]);
	}
	
	private void collectFilesRecursive(final File file)
	{
		final File[] subFiles = file.listFiles();
		
		for(int i = 0; i < subFiles.length; i++)
		{
			final File f = subFiles[i];
			
			if(f.isFile())
			{
				this.allFiles.add(f);
			}
			else if(f.isDirectory())
				this.collectFilesRecursive(f);
		}
	}
}
