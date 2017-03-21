package test;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ast.NodeProgram;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import token.TokenType;

public class testParser
{
	private String inputFileName = "./resources/testParse";
	private String dummyFileName = "./resources/dummyParse";
	
	@Before
	public void writeFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		//scrive file per i test
		PrintWriter writer;

		writer = new PrintWriter(inputFileName, "UTF-8");
		writer.write("f b\ni a\na = 5\nb = a + 3.2\np b\n");
		writer.close();
		
		writer = new PrintWriter(dummyFileName, "UTF-8");
		writer.write("f f\ni f\nb = a + ");
		writer.close();
	}
	
	@Test
	public void testParseOk() throws IOException, SyntacticException
	{
		Parser p = new Parser(new Scanner(inputFileName));
		NodeProgram np;
		
		//file di input con un programma sintatticamente corretto
		try
		{
			np = p.parse();
			
			//ne salta uno!
			
			System.out.println(np);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
			fail();
		}
	}
	
	/*
	@Test
	public void testParseNo() throws IOException, SyntacticException
	{
		Parser p = new Parser(new Scanner(dummyFileName));
		
		//programma sintatticamente non corretto
		try
		{
			p.parse();
			
			fail("Exception expected");
		}
		catch(Exception e)
		{
			e.printStackTrace(System.out);
			System.out.println(e.getMessage());
			fail();
		}
	}
	*/
}