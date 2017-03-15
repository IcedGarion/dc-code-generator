package parser;

import java.io.BufferedReader;
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
	private String defaultGrammarPath = "./resources/grammar";
	private ArrayList<String> grammar; 	//lista di stringhe che sono tutte le regole della grammatica
										//formato: un nonTerm seguito da uno o piu term/non term (solo spazi)
	
	public Parser(Scanner s) throws IOException
	{
		this.scanner = s;
		grammar = new ArrayList<String>();
		grammarFill(defaultGrammarPath);
	}
	
	private void grammarFill(String Path) throws IOException
	{
		BufferedReader t = new BufferedReader(new FileReader(Path));
		String line = t.readLine();
		
		while(line != null)
		{
			grammar.add(line);
			line = t.readLine();
		}
		
		t.close();
	}
	
	public ArrayList<String> getGrammar()
	{
		return grammar;
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
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				startsWithNT.add(grammar.get(i));
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
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				startsWithNT.add(grammar.get(i));
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
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				startsWithNT.add(grammar.get(i));
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
	private boolean parseStms() throws LexicalException, IOException, SyntacticException
	{
		String nonTerm = "Stms";
		Token nxt = scanner.peekToken();
		ArrayList<String> startsWithNT = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				startsWithNT.add(grammar.get(i));
		}
		
		// Dcl ha 2 regole: stms->stm stms
		if(predict(nonTerm, startsWithNT.get(0)).contains(nxt.getType()))
		{
			parseStm();
			parseStms();
		}
		//stms->eps
		else if(predict(nonTerm, startsWithNT.get(1)).contains(nxt.getType()))
		{
			//EPS
		}
		else
			throw new SyntacticException();
		
		return true;
	}
	private boolean parseStm() throws LexicalException, IOException, SyntacticException
	{
		String nonTerm = "Stm";
		Token nxt = scanner.peekToken();
		ArrayList<String> startsWithNT = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				startsWithNT.add(grammar.get(i));
		}
		
		// Dcl ha 2 regole: stm->id assign val expr
		if(predict(nonTerm, startsWithNT.get(0)).contains(nxt.getType()))
		{
			match(TokenType.ID);
			match(TokenType.ASSIGN);
			parseVal();
			parseExpr();
		}
		//stm->print id
		else if(predict(nonTerm, startsWithNT.get(1)).contains(nxt.getType()))
		{
			match(TokenType.PRINT);
			match(TokenType.ID);
		}
		else
			throw new SyntacticException();
		
		return true;
	}
	private boolean parseExpr() throws LexicalException, IOException, SyntacticException
	{
		String nonTerm = "Expr";
		Token nxt = scanner.peekToken();
		ArrayList<String> startsWithNT = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				startsWithNT.add(grammar.get(i));
		}
		
		// Dcl ha 3 regole: Expr->plus Val Expr
		if(predict(nonTerm, startsWithNT.get(0)).contains(nxt.getType()))
		{
			match(TokenType.PLUS);
			parseVal();
			parseExpr();
		}
		//Expr->minus Val Expr
		else if(predict(nonTerm, startsWithNT.get(1)).contains(nxt.getType()))
		{
			match(TokenType.MINUS);
			parseVal();
			parseExpr();
		}
		else if(predict(nonTerm, startsWithNT.get(2)).contains(nxt.getType()))
		{
			//EPS
		}
		else
			throw new SyntacticException();
		
		return true;
	}
	
	private boolean parseVal() throws LexicalException, IOException, SyntacticException
	{
		String nonTerm = "Val";
		Token nxt = scanner.peekToken();
		ArrayList<String> startsWithNT = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				startsWithNT.add(grammar.get(i));
		}
		
		// Dcl ha 3 regole: Val->inum
		if(predict(nonTerm, startsWithNT.get(0)).contains(nxt.getType()))
			match(TokenType.INUM);
	
		//Expr->minus Val Expr
		else if(predict(nonTerm, startsWithNT.get(1)).contains(nxt.getType()))
			match(TokenType.FNUM);

		else if(predict(nonTerm, startsWithNT.get(2)).contains(nxt.getType()))
			match(TokenType.ID);
		
		else
			throw new SyntacticException();
		
		return true;
	}
	
	private ArrayList<String> predict(String nT, String production)
	{
		return null;
	}
	
	public String LHS(String production)
	{
		String ret = "";
		int i = 0;
		char c = production.charAt(i);
		
		while(c != ' ')
		{
			ret += c;
			c = production.charAt(++i);
		}
		
		return ret;
	}
	
	public String RHS(String production)
	{
		String splitted[];
		String ret = "";
		
		splitted = production.split(" ");
		
		for(int i=1; i<splitted.length; i++)
		{
			if(i != splitted.length -1)
				ret += splitted[i] + " ";
			else
				ret += splitted[i];
		}
			
		return ret;
	}
}