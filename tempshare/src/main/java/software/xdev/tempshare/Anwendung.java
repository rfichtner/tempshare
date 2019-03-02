package software.xdev.tempshare;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Anwendung
{
	static String hilfeOderPfad;
	static String schreibgeschuetzt;
	static String alter;
	static String simulieren;
	static String kurzeausgabe = "0";
	
	public Anwendung()
	{
	}
	
	public static void main(final String[] args)
	{
		if(args.length < 1)
		{
			System.out.println("Falsche Zahl an Parametern (Die Hilfe ist ueber /? erreichbar)");
			System.exit(0);
		}
		
		hilfeOderPfad = args[0];
		
		if(hilfeOderPfad.equals("/?"))
		{
			Hilfe();
			System.exit(0);
		}
		
		if(args.length < 8)
		{
			System.out.println("Falsche Zahl an Parametern (Die Hilfe ist ueber /? erreichbar)");
			System.exit(0);
		}
		
		for(int i = 0; i < args.length; i++)
		{
			final String var = args[i];
			i++;
			final String wert = args[i];
			
			if(var.equalsIgnoreCase("/path"))
			{
				hilfeOderPfad = wert;
			}
			else if(var.equalsIgnoreCase("/age"))
			{
				alter = wert;
			}
			else if(var.equalsIgnoreCase("/simulate"))
			{
				simulieren = wert;
			}
			else if(var.equalsIgnoreCase("/state"))
			{
				schreibgeschuetzt = wert;
			}
			else if(var.equalsIgnoreCase("/output"))
			{
				kurzeausgabe = wert;
			}
		}
		
		if(Pruefen())
		{
			
			Loeschen();
			
			LeereOrdnerLoeschen(new File(hilfeOderPfad));
		}
		else
		{
			System.out.println("Falsche Zahl an Parametern (Die Hilfe ist ueber /? erreichbar)");
		}
		
		if(System.out != null)
		{
			System.out.close();
		}
	}
	
	public static void Hilfe()
	{
		String ausgabe = "Hilfe\n\n";
		
		ausgabe = ausgabe + "Parameter werden mit \"/parametername wert\" uebergeben.\n";
		ausgabe = ausgabe + "Parameterliste: (Alle Parameter muessen uebergeben werden!)\n";
		
		ausgabe = ausgabe + "/path: Pfad\n";
		
		ausgabe = ausgabe + "/state: 0 - normale Dateien; 1 - nur schreibgeschuetzte Dateien\n";
		
		ausgabe = ausgabe + "/age: Alter der Dateien in Tagen\n";
		
		ausgabe = ausgabe + "/simulate: Loeschen simulieren; 0 - Loeschen simulieren; 1 - Loeschen sofort ausführen";
		
		ausgabe =
			ausgabe + "/output: Ist eine kurze oder lange Ausgabe erwuenscht? 0 - lange Ausgabe; 1 - kurze Ausgabe";
		
		System.out.println(ausgabe);
	}
	
	public static void Loeschen()
	{
		final MyFile path = new MyFile(hilfeOderPfad);
		
		final File[] loeschListe = path.listFilesRecursive();
		final Calendar aktuellesDatum = Calendar.getInstance();
		final SimpleDateFormat formatter = new SimpleDateFormat("E dd.MM.yyyy 'um' HH:mm");
		
		if(kurzeausgabe.equals("0"))
		{
			
			System.out.println("-----------------------------------------------------------------");
			final String simStr = simulieren.equals("0") ? "Simulation" : "Durchlauf";
			System.out.println(simStr + " vom " + formatter.format(aktuellesDatum.getTime()));
			System.out.println("Loeschen in Ordner: " + hilfeOderPfad);
			final String substr = schreibgeschuetzt.equals("0") ? "" : "schreibgeschuetzten ";
			System.out.println("alle " + substr + "Dateien die aelter sind als " + alter + " Tage");
			System.out.println("-----------------------------------------------------------------");
		}
		else
		{
			System.out.println("-----------------------------------------------------------------");
			final String simStr = simulieren.equals("0") ? "Simulation" : "Durchlauf";
			System.out.println(simStr + " vom " + formatter.format(aktuellesDatum.getTime()));
			System.out.println("-----------------------------------------------------------------");
		}
		
		for(int i = 0; i < loeschListe.length; i++)
		{
			
			final File loeschFile = loeschListe[i];
			
			final Calendar working = (Calendar)aktuellesDatum.clone();
			
			working.add(6, -Integer.parseInt(alter));
			
			final Calendar lastModified = Calendar.getInstance();
			
			lastModified.setTimeInMillis(loeschFile.lastModified());
			
			if(lastModified.before(working)
				&& (loeschFile.canWrite() && schreibgeschuetzt.equals("0")
					|| !loeschFile.canWrite() && schreibgeschuetzt.equals("1")))
			{
				if(kurzeausgabe.equals("0"))
				{
					
					System.out.println("-> Folgende Datei soll geloescht werden: \"" + loeschFile.getPath() + "\"");
					System.out.println("   Aenderungsdatum: " + formatter.format(lastModified.getTime()));
				}
				else if(simulieren.equals("0"))
				{
					System.out.println("Datei soll geloescht werden: \"" + loeschFile.getPath() + "\"");
				}
				
				if(simulieren.equals("1"))
				{
					
					final boolean geloescht = loeschFile.delete();
					
					if(geloescht)
					{
						if(kurzeausgabe.equals("0"))
						{
							
							System.out.println("   Loeschen war erfolgreich");
						}
						else
						{
							System.out.println("Datei \"" + loeschFile.getPath() + "\" wurde geloescht!");
						}
						
					}
					else if(kurzeausgabe.equals("0"))
					{
						
						System.out.println("   Loeschen fehlgeschlagen");
					}
				}
			}
		}
	}
	
	public static boolean Pruefen()
	{
		if(hilfeOderPfad.isEmpty())
		{
			return false;
		}
		final File f = new File(hilfeOderPfad);
		if(!f.isDirectory())
		{
			return false;
		}
		if(schreibgeschuetzt.isEmpty())
			return false;
		if(alter.isEmpty())
		{
			return false;
		}
		
		if(!schreibgeschuetzt.equals("0") && !schreibgeschuetzt.equals("1"))
			return false;
		if(!simulieren.equals("1") && !simulieren.equals("0"))
			return false;
		if(!kurzeausgabe.equals("0") && !kurzeausgabe.equals("1"))
		{
			return false;
		}
		try
		{
			Integer.parseInt(alter);
		}
		catch(final Exception e)
		{
			return false;
		}
		
		return true;
	}
	
	public static void LeereOrdnerLoeschen(final File path)
	{
		final File[] files = path.listFiles();
		
		if(simulieren.equals("1"))
		{
			if(files.length < 1)
			{
				return;
			}
			
			for(int nIndex = files.length - 1; nIndex >= 0; nIndex--)
			{
				
				if(files[nIndex].isDirectory())
				{
					
					LeereOrdnerLoeschen(files[nIndex]);
					
					final boolean geloescht = files[nIndex].delete();
					
					if(geloescht)
					{
						System.out.println(
							"Der Ordner \"" + files[nIndex].getPath() + "\" war leer und wurde geloescht!");
					}
				}
			}
		}
	}
}
