package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser
{
	private Scanner scanner;
	private Token currentToken;
	private String grammarPath = "./resources/grammar";

	//lista di stringhe che sono tutte le regole della grammatica
	private ArrayList<String> prod;
	
	public Parser(Scanner s) throws IOException
	{
		this.scanner = s;
		prodFill(grammarPath);
	}
	
	private void prodFill(String Path) throws IOException
	{
		BufferedReader t = new BufferedReader(new FileReader(Path));
		String line = t.readLine();
		
		while(line != null)
		{
			prod.add(line);
			line = t.readLine();
		}
		
	}
	
	public ArrayList<String> grammar()
	{
		return prod;
	}
	
	private void match(TokenType type) throws SyntacticException, LexicalException, IOException
	{
		if(type.equals(scanner.peekToken().getType()))
			currentToken = scanner.nextToken();
		else
			throw new SyntacticException();
	}
	
	public boolean parse() throws SyntacticException, LexicalException, IOException
	{
		return parseProg();
	}
	
	private boolean parseProg() throws LexicalException, IOException, SyntacticException
	{
		String nonTerm = "Prog";
		Token nxt = scanner.peekToken();
		ArrayList<String> startsWithNT = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term (prog)
		for(int i=0; i<prod.size(); i++)
		{
			if(prod.get(i).startsWith(nonTerm))
				startsWithNT.add(prod.get(i));
		}
		
		//prog ha una sola regola: prog->Dcls Stms eof
		//se prox token è un predict di prog, allora parsifica la regola prog->****
		if(predict(nonTerm, startsWithNT.get(0)).contains(nxt.getType()))
		{
			parseDcls();
			parseStms();
			match(TokenType.EOF);
		}
		else
			throw new SyntacticException();
		
		return true;
	}
	
	private boolean parseDcls() throws LexicalException, IOException, SyntacticException
	{
		String nonTerm = "Dcls";
		Token nxt = scanner.peekToken();
		ArrayList<String> startsWithNT = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term (prog)
		for(int i=0; i<prod.size(); i++)
		{
			if(prod.get(i).startsWith(nonTerm))
				startsWithNT.add(prod.get(i));
		}
		
		//Dcls ha 2 regole: Dcls->Dcl Dcls
		if(predict(nonTerm, startsWithNT.get(0)).contains(nxt.getType()))
		{
			parseDcl();
			parseDcls();
		}
		//Dcls->eps
		else if(predict(nonTerm, startsWithNT.get(1)).contains(nxt.getType()))
		{
			//PARSE EPS? MATCH CON EPS?
		}
		else
			throw new SyntacticException();
		
		return true;
	}
	private boolean parseDcl() throws LexicalException, IOException, SyntacticException
	{
		String nonTerm = "Dcl";
		Token nxt = scanner.peekToken();
		ArrayList<String> startsWithNT = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term (prog)
		for(int i=0; i<prod.size(); i++)
		{
			if(prod.get(i).startsWith(nonTerm))
				startsWithNT.add(prod.get(i));
		}
		
		// Dcl ha 2 regole: Dcl->floatdcl id
		if(predict(nonTerm, startsWithNT.get(0)).contains(nxt.getType()))
		{
			match(TokenType.FLOATDCL);
			match(TokenType.ID);
		}
		//dcl->intdcl id
		else if(predict(nonTerm, startsWithNT.get(1)).contains(nxt.getType()))
		{
			match(TokenType.INTDCL);
			match(TokenType.ID);
		}
		else
			throw new SyntacticException();
		
		return true;
	}
	private boolean parseStms()
	{
		return false;
	}
	private boolean parseStm()
	{
		return false;
	}
	private boolean parseExpr()
	{
		return false;
	}
	private boolean parseVal()
	{
		return false;
	}
	
	private ArrayList<String> predict(String nT, String production)
	{
		return null;
	}
	
	private String LHS(String production)
	{
		return null;
	}
	
	private String RHS(String production)
	{
		return null;
	}
}
