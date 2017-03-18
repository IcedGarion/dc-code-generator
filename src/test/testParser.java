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
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;

public class testParser
{
	private String inputFileName = "./resources/testParse";
	private String dummyFileName = "./resources/dummyParse";
	private String grammarFileName = "./resources/grammar";
	
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
		
		writer = new PrintWriter(grammarFileName, "UTF-8");
		writer.write("Prog->Dcls Stms eof\nDcls->Dcl Dcls\nDcls->eps\nDcl->floatdcl id\nDcl->intdcl id\n"
				+ "Stms->Stm Stms\nStms->eps\nStm->id assign Val Expr\nStm->print id\nExpr->plus Val Expr\n"
				+ "Expr->minus Val Expr\nExpr->eps\nVal->inum\nVal->fnum\nVal->id\n");
		writer.close();
	}
	
	@Test
	public void testHS() throws FileNotFoundException, IOException, SyntacticException
	{
		Parser p = new Parser(new Scanner(inputFileName));
		
		assertEquals("Prog", p.LHS("Prog->Dcls Stms eof"));
		assertEquals("Dcls Stms eof", p.RHS("Prog->Dcls Stms eof"));
		assertEquals("Stms", p.LHS("Stms->eps"));
		assertEquals("eps", p.RHS("Stms->eps"));
		assertEquals("Stm", p.LHS("Stm->id assign Val Expr"));
		assertEquals("id assign Val Expr", p.RHS("Stm->id assign Val Expr"));
		assertEquals("Dcl", p.LHS("Dcl->intdcl id"));
		assertEquals("intdcl id", p.RHS("Dcl->intdcl id"));
	}
	
	
	@Test
	public void testGrammar() throws FileNotFoundException, IOException, SyntacticException
	{
		Parser p = new Parser(new Scanner(inputFileName));
		ArrayList<String> grammar = p.getGrammar();
		int i = 0;
		
		assertEquals("Prog->Dcls Stms eof", grammar.get(i++));
		assertEquals("Dcls->Dcl Dcls", grammar.get(i++));
		assertEquals("Dcls->eps", grammar.get(i++));
		assertEquals("Dcl->floatdcl id", grammar.get(i++));
		assertEquals("Dcl->intdcl id", grammar.get(i++));
		assertEquals("Stms->Stm Stms", grammar.get(i++));
		assertEquals("Stms->eps", grammar.get(i++));
		assertEquals("Stm->id assign Val Expr", grammar.get(i++));
		assertEquals("Stm->print id", grammar.get(i++));
		assertEquals("Expr->plus Val Expr", grammar.get(i++));
		assertEquals("Expr->minus Val Expr", grammar.get(i++));
		assertEquals("Expr->eps", grammar.get(i++));
		assertEquals("Val->inum", grammar.get(i++));
		assertEquals("Val->fnum", grammar.get(i++));
		assertEquals("Val->id", grammar.get(i++));
	}
	
	/*
	@Test
	public void testSynt() throws IOException
	{
		Parser p = new Parser(new Scanner(inputFileName));
		
		//file di input con un programma sintatticamente corretto
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
		Parser p = new Parser(new Scanner(inputFileName));
		
		//programma sintatticamente non corretto
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
	
	*/
	
	@Test
	public void testNotTermDerEmpty()
	{
		/*testa le variabili nuove:
		 * private ArrayList<String> derEmptyProductions;
		   private ArrayList<String> derEmptyNT;
		   private ArrayList<String> notTerminals;
		   
		   in grammarFill
		 */
	}
}