package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import scanner.LexicalException;
import scanner.Scanner;

public class testScanner
{
	private String fileName = "./resources/input";
	private String dummyFileName = "./resources/dummy";
	
	@Before
	public void writeFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		//scrive i due file per i test
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f b\ni a\na = 5\nb = a + 3.2\np b\n");
		writer.close();
		
		writer = new PrintWriter(dummyFileName, "UTF-8");
		writer.write(";\nà\n123g\n1.5x\nfb\ni aa\npa\n");
		writer.close();
	}
	
	
	@Test
	public void testException()
	{
		try
		{
			new Scanner("wrongFile");
			fail("Exception expected");
		}
		catch(FileNotFoundException e)
		{}
		
		try
		{
			new Scanner(fileName);
		}
		catch(Exception e)
		{
			fail("No exception expected");
		}
	}


	@Test
	public void test()
	{
		Scanner s;
		
		try
		{
			s = new Scanner(fileName);
			
			assertEquals("FLOATDCL", s.nextToken().toString());
			assertEquals("ID,b", s.nextToken().toString());
			assertEquals("INTDCL", s.nextToken().toString());
			assertEquals("ID,a", s.nextToken().toString());
			assertEquals("ID,a", s.nextToken().toString());
			assertEquals("ASSIGN", s.nextToken().toString());
			assertEquals("INUM,5", s.nextToken().toString());
			assertEquals("ID,b", s.nextToken().toString());
			assertEquals("ASSIGN", s.nextToken().toString());
			assertEquals("ID,a", s.nextToken().toString());
			assertEquals("PLUS", s.nextToken().toString());
			assertEquals("FNUM,3.2", s.nextToken().toString());
			assertEquals("PRINT", s.nextToken().toString());
			assertEquals("ID,b", s.nextToken().toString());
			assertEquals("EOF", s.nextToken().toString());	
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			fail("No exception expected");
		}
	}
	
	
	@Test
	public void testTokenException() throws IOException
	{
		//prova tutte le possibili eccezioni in Scanner
		Scanner s = new Scanner(dummyFileName);
		
		//;
		try
		{
			s.nextToken();
			fail("Exception expected");
		}
		catch(LexicalException e)
		{
			assertEquals("Illegal character: ;", e.getMessage());
		}
		
		//à
		try
		{
			s.nextToken();
			fail("Exception expected");
		}
		catch(LexicalException e)
		{
			assertEquals("Illegal character: à", e.getMessage());
		}
		
		//123g
		try
		{
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();		
			fail("Exception expected");
		}
		catch(LexicalException e)
		{
			assertEquals("Illegal character: g", e.getMessage());
		}
		
		//1.5x
		try
		{
			s.nextToken();
			s.nextToken();
			s.nextToken();
			s.nextToken();		
			fail("Exception expected");
		}
		catch(LexicalException e)
		{
			assertEquals("Illegal character: x", e.getMessage());
		}
		
		//fb
		try
		{
			s.nextToken();
			s.nextToken();		
			fail("Exception expected");
		}
		catch(LexicalException e)
		{
			assertEquals("Illegal character: b, no blank", e.getMessage());
		}
		
		//i aa
		try
		{
			s.nextToken();
			s.nextToken();		
			fail("Exception expected");
		}
		catch(LexicalException e)
		{
			assertEquals("Illegal character: a, no blank", e.getMessage());
		}
		
		//pa
		try
		{
			s.nextToken();
			s.nextToken();		
			fail("Exception expected");
		}
		catch(LexicalException e)
		{
			assertEquals("Illegal character: a, no blank", e.getMessage());
		}
	}
	 
	 
	@Test
	public void testPeek()
	{
		Scanner s;
		
		try
		{
			s = new Scanner(fileName);
			
			assertEquals("FLOATDCL", s.peekToken().toString());
			assertEquals("FLOATDCL", s.nextToken().toString());
			assertEquals("ID,b", s.peekToken().toString());
			assertEquals("ID,b", s.peekToken().toString());
		}
		catch(Exception e)
		{
			fail("No exception expected");
		}
	}
	
}