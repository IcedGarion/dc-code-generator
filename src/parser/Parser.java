package parser;

import java.io.IOException;
import java.util.ArrayList;
import ast.LangType;
import ast.NodeAST;
import ast.NodeDecl;
import ast.NodeProgram;
import ast.NodeStm;
import scanner.LexicalException;
import scanner.Scanner;
import symTable.SymTable;
import token.Token;
import token.TokenType;

public class Parser
{
	private SymTable symbolTable;
	private Scanner scanner;
	private Token currentToken;
	
	
	public Parser(Scanner s) throws IOException, SyntacticException
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
	
	public NodeProgram parse() throws Exception
	{
		return parseProg();
	}
	
	private NodeProgram parseProg() throws Exception
	{
		Token nxt = scanner.peekToken();
		ArrayList<NodeDecl> decls = new ArrayList<NodeDecl>();
		ArrayList<NodeStm> stms = new ArrayList<NodeStm>();
		
		switch(nxt.getType())
		{
			case INTDCL:
			case FLOATDCL:
			case ID:
			case PRINT:
			case EOF:
			{
				//prog->dcls stms eof
				decls.addAll(parseDcls());
				stms.addAll(parseStms());
				match(TokenType.EOF);
				break;
			}
			default:
				throw new SyntacticException();
		}
		
		return new NodeProgram(decls, stms);
	}		
	
	private ArrayList<NodeDecl> parseDcls() throws Exception
	{
		Token nxt = scanner.peekToken();
		ArrayList<NodeDecl> ret = new ArrayList<NodeDecl>();

		switch(nxt.getType())
		{
			case INTDCL:
			case FLOATDCL:
			{
				//Dcls->Dcl Dcls
				ret.add(parseDcl());
				ret.addAll(parseDcls());
				break;
			}
			case ID:
			case PRINT:
			case EOF:
			{
				//Dcls->eps: non fa niente
				break;
			}
			default:
				throw new SyntacticException();
		}

		return ret;
	}
	
	private NodeDecl parseDcl() throws Exception
	{
		Token nxt = scanner.peekToken();
		NodeDecl ret = null;

		switch(nxt.getType())
		{
			case FLOATDCL:
			{
				//Dcl->floatdcl id
				match(TokenType.FLOATDCL);
				match(TokenType.ID);
				String id1 = nxt.getValue();
				String id = scanner.peekToken().getValue();
				ret = new NodeDecl(scanner.peekToken().getValue(), LangType.FLOAT);
				break;
			}
			case INTDCL:
			{
				//dcl->intdcl id
				match(TokenType.INTDCL);
				match(TokenType.ID);
				String id = nxt.getValue();
				ret = new NodeDecl(scanner.peekToken().getValue(), LangType.INT);
				break;
			}
			default:
				throw new SyntacticException();
		}

		return ret;
	}
	
	private ArrayList<NodeStm> parseStms() throws Exception
	{
		Token nxt = scanner.peekToken();
		ArrayList<NodeStm> ret = new ArrayList<NodeStm>();
		
		switch(nxt.getType())
		{
			case ID:
			case PRINT:
			{
				//stms->stm stms
				ret.add(parseStm());
				ret.addAll(parseStms());
				break;
			}
			case EOF:
				//stms->eps
				break;
			default:
				throw new SyntacticException();			
		}
		
		return ret;
	}
	
	//ritorna sempre null da qua in poi
	private NodeStm parseStm() throws Exception
	{
		Token nxt = scanner.peekToken();
			
		switch(nxt.getType())
		{
			case ID:
			{
				//stm->id assign val expr
				match(TokenType.ID);
				match(TokenType.ASSIGN);
				parseVal();
				parseExpr();
				break;
			}
			case PRINT:
			{
				//stm->print id
				match(TokenType.PRINT);
				match(TokenType.ID);
				break;
			}
			default: 
				throw new SyntacticException();
		}
		
		return null;
	}
	
	private NodeAST parseExpr() throws Exception
	{
		Token nxt = scanner.peekToken();
		
		switch(nxt.getType())
		{
			case PLUS:
			{
				//Expr->plus Val Expr
				match(TokenType.PLUS);
				parseVal();
				parseExpr();
				break;
			}
			case MINUS:
			{
				//Expr->minus Val Expr
				match(TokenType.MINUS);
				parseVal();
				parseExpr();
				break;				
			}
			case ID:
			case PRINT:
			case EOF:
			{
				//EPS
				break;
			}
			default:
				throw new SyntacticException();
		}
		
		return null;
	}
	
	private NodeAST parseVal() throws Exception
	{
		Token nxt = scanner.peekToken();
	
		switch(nxt.getType())
		{
			case INUM:
			{
				//Val->inum
				match(TokenType.INUM);
				break;
			}
			case FNUM:
			{
				//Val->fnum
				match(TokenType.FNUM);
				break;
			}
			case ID:
			{
				//Val->id
				match(TokenType.ID);
				break;
			}
			default:
				throw new SyntacticException();
		}
			
		return null;
	}
}