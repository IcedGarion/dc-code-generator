package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.junit.Before;
import org.junit.Test;
import ast.NodeProgram;
import parser.Parser;
import scanner.Scanner;
import visitor.TypeChecker;
import typecheck.TypeException;

public class testTypeCheck
{
	private String excFileName = "./resources/symParse";
	
	@Before
	public void writeFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer;
		
		writer = new PrintWriter(excFileName, "UTF-8");
		writer.write("i a\nf b\na = b + 3.2 ");
		writer.close();
	}
	
	@Test
	public void testExc() throws Exception
	{
		Parser p = new Parser(new Scanner(excFileName));
		NodeProgram np;
		TypeChecker visitor = new TypeChecker();
		
		np = p.parse();
		
		try
		{
			np.accept(visitor);
		}
		catch(TypeException e)
		{
			assertEquals("Type mismatch in a = b PLUS 3.2: cannot convert from FLOAT to INT", e.getMessage());
		}
	}
}
