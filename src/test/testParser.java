package test;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
	public void testGrammar() throws FileNotFoundException, IOException
	{
		Parser p = new Parser(new Scanner(fileName));
		ArrayList<String> grammar = p.grammar();
		
		assertEquals("Prog Dcls Stms eof", grammar.get(0));
		fail("da finire");
		//ecc...
	}
	
	@Test
	public void testSynt() throws IOException
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
	
	public void testNoSynt() throws IOException
	{
		Parser p = new Parser(new Scanner(fileName));
		
		try
		{
			p.parse();
			
			fail("Exception expected");
		}
		catch(Exception e)
		{
			assertEquals("messaggio", e.getMessage());
		}
	}
}
