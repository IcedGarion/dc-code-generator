package test;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.junit.Before;
import org.junit.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;

public class testParser
{
	private String fileName = "./resources/testParse";
	private String dummyFileName = "./resources/dummyParse";
	
	@Before
	public void writeFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		//scrive file per i test
		PrintWriter writer;

		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f b\ni a\na = 5\nb = a + 3.2\np b\n");
		writer.close();
		
		writer = new PrintWriter(dummyFileName, "UTF-8");
		writer.write("f f\ni f\nb = a + ");
		writer.close();
	}
	
	@Test
	public void testSynt() throws FileNotFoundException
	{
		Parser p = new Parser(new Scanner(fileName));
		
		try
		{
			assertTrue(p.parse());
		}
		catch(Exception e)
		{
			fail("No exception expected");
		}
	}
	
	public void testNoSynt() throws FileNotFoundException
	{
		Parser p = new Parser(new Scanner(fileName));
		
		try
		{
			p.parse();
			
			fail("Exception expected");
		}
		catch(SyntacticException e)
		{
			assertEquals("messaggio", e.getMessage());
		}
	}
}
