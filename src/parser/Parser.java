package parser;

import java.io.IOException;
import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser
{
	private Scanner scanner;
	private Token currentToken;
	
	public Parser(Scanner s)
	{
		this.scanner = s;
	}
	
	private void match(TokenType type) throws SyntacticException, LexicalException, IOException
	{
		if(type.equals(scanner.peekToken().getType()))
			currentToken = scanner.nextToken();
		else
			throw new SyntacticException();
	}
	
	public boolean parse() throws SyntacticException
	{
		throw new SyntacticException();
		//return parseProg();
	}
	
	private boolean parseProg()
	{
		return false;
	}
	
	private boolean parseDcls()
	{
		return false;
	}
	private boolean parseDcl()
	{
		return false;
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
	
}
