package parser;

import java.io.IOException;

import java.util.ArrayList;

import ast.LangOper;
import ast.LangType;
import ast.NodeAST;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeCost;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import ast.NodeStm;
import scanner.LexicalException;
import scanner.Scanner;
import symTable.SymTable;
import token.Token;
import token.TokenType;

public class Parser
{
	private Scanner scanner;
	private Token currentToken;
	
	
	public Parser(Scanner s) throws IOException, SyntacticException
	{
		this.scanner = s;
		SymTable.init();
	}
	
	private void match(TokenType type) throws SyntacticException, LexicalException, IOException
	{
		if(type.equals(scanner.peekToken().getType()))
			currentToken = scanner.nextToken();
		else
			throw new SyntacticException("Expected "+type+" but was "+scanner.peekToken().getType());
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
		ArrayList<NodeDecl> ret = new ArrayList<NodeDecl>();

		parseDeclRic(ret);

		return ret;
	}

	private void parseDeclRic(ArrayList<NodeDecl> ret) throws Exception, SyntacticException
	{
		Token nxt = scanner.peekToken();
		
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
				ret = new NodeDecl(new NodeId(currentToken.getValue()), LangType.FLOAT);		
				break;
			}
			case INTDCL:
			{
				//dcl->intdcl id
				match(TokenType.INTDCL);
				match(TokenType.ID);
				ret = new NodeDecl(new NodeId(currentToken.getValue()), LangType.INT);
				break;
			}
			default:
				throw new SyntacticException();
		}

		return ret;
	}
	
	private ArrayList<NodeStm> parseStms() throws Exception
	{
		ArrayList<NodeStm> ret = new ArrayList<NodeStm>();
		
		parseStmsRic(ret);
		
		return ret;
	}

	private void parseStmsRic(ArrayList<NodeStm> ret) throws Exception, SyntacticException
	{
		Token nxt = scanner.peekToken();
		
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
	}
	
	private NodeStm parseStm() throws Exception
	{
		Token nxt = scanner.peekToken();
		NodeStm ret;
		String id;
			
		switch(nxt.getType())
		{
			case ID:
			{
				//stm->id assign val expr
				match(TokenType.ID);
				id = currentToken.getValue();
				match(TokenType.ASSIGN);
				NodeExpr val = parseVal();
				NodeExpr expr = parseExpr(val);
				ret = new NodeAssign(new NodeId(id), expr);
				break;
			}
			case PRINT:
			{
				//stm->print id
				match(TokenType.PRINT);
				match(TokenType.ID);
				ret = new NodePrint(new NodeId(currentToken.getValue()));
				break;
			}
			default: 
				throw new SyntacticException();
		}
		
		return ret;
	}
	
	private NodeExpr parseExpr(NodeExpr val) throws Exception
	{
		Token nxt = scanner.peekToken();
		NodeExpr ret = null;
		NodeExpr right, left;
		
		switch(nxt.getType())
		{
			case PLUS:
			{
				//Expr->plus Val Expr
				match(TokenType.PLUS);
				left = parseVal();
				right = parseExpr(left);
				ret = new NodeBinOp(LangOper.PLUS, val, right);
				break;
			}
			case MINUS:
			{
				//Expr->minus Val Expr
				match(TokenType.MINUS);
				left = parseVal();
				right = parseExpr(left);
				ret = new NodeBinOp(LangOper.MINUS, val, right);
				break;				
			}
			case ID:
			case PRINT:
			case EOF:
			{
				//EPS
				ret = val;						//se non c'è expr dopo val, allora ritorno val
				break;							//perchè parseExpr dovrebbe provare a concatenargli dietro un'altra expr,
			}									//ma se non c'è niente torna val
			default:
				throw new SyntacticException();
		}
		
		return ret;
	}
	
	private NodeExpr parseVal() throws Exception
	{
		Token nxt = scanner.peekToken();
		NodeExpr ret;
	
		switch(nxt.getType())
		{
			case INUM:
			{
				//Val->inum
				match(TokenType.INUM);
				ret = new NodeCost(currentToken.getValue());
				break;
			}
			case FNUM:
			{
				//Val->fnum
				match(TokenType.FNUM);
				ret = new NodeCost(currentToken.getValue());
				break;
			}
			case ID:
			{
				//Val->id
				match(TokenType.ID);
				ret = new NodeDeref(new NodeId(currentToken.getValue()));
				break;
			}
			default:
				throw new SyntacticException();
		}
			
		return ret;
	}
}