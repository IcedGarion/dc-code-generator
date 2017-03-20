package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser
{
	private String defaultGrammarPath = "./resources/grammar";
	private Scanner scanner;
	private Token currentToken;
	private ArrayList<String> derEmptyProductions;
	private ArrayList<String> derEmptyNT;
	private ArrayList<String> notTerminals;
	private ArrayList<String> grammar; 	//lista di stringhe che sono tutte le regole della grammatica
										//formato: un nonTerm seguito da uno o piu term/non term (solo spazi)
	private HashMap<String, Boolean> visitato;
	
	
	public Parser(Scanner s) throws IOException, SyntacticException
	{
		this.scanner = s;
		grammar = new ArrayList<String>();
		derEmptyProductions = new ArrayList<String>();
		derEmptyNT = new ArrayList<String>();
		notTerminals = new ArrayList<String>();
		grammarFill(defaultGrammarPath);
	}
	
	private void grammarFill(String Path) throws IOException, SyntacticException
	{
		BufferedReader t = new BufferedReader(new FileReader(Path));
		String line = t.readLine();
		String lhs;
		boolean contains;
		
		while(line != null)
		{
			grammar.add(line);						//importa la grammatica da file
			
			lhs = LHS(line);
			contains = notTerminals.contains(lhs);
			if(! contains)
				notTerminals.add(lhs);			//aggiunge ai non terminali il non term a sx (se non c'è già)
			if(derEmpty(line))
			{
				if(! contains)
					derEmptyNT.add(lhs);			//se la regola produce eps, aggiunge a derEmpty
				derEmptyProductions.add(line);
			}
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
		ArrayList<String> progProductions = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term (prog)
		//cioè le uniche regole da considerare
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				progProductions.add(grammar.get(i));
		}
		
		//prog ha una sola regola: prog->Dcls Stms eof
		//se prox token è un predict di prog, allora parsifica la regola prog->****
		if(predict(progProductions.get(0)).contains(nxt.getType()))
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
		ArrayList<String> dclsProductions = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term (prog)
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				dclsProductions.add(grammar.get(i));
		}
		
		//Dcls ha 2 regole: Dcls->Dcl Dcls
		if(predict(dclsProductions.get(0)).contains(nxt.getType()))
		{
			parseDcl();
			parseDcls();
		}
		//Dcls->eps
		else if(predict(dclsProductions.get(1)).contains(nxt.getType()))
		{
			//PARSE EPS
			return true;
		}
		else
			throw new SyntacticException();
		
		return true;
	}
	
	private boolean parseDcl() throws LexicalException, IOException, SyntacticException
	{
		String nonTerm = "Dcl";
		Token nxt = scanner.peekToken();
		ArrayList<String> dclProductions = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term (prog)
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				dclProductions.add(grammar.get(i));
		}
		
		// Dcl ha 2 regole: Dcl->floatdcl id
		if(predict(dclProductions.get(0)).contains(nxt.getType()))
		{
			match(TokenType.FLOATDCL);
			match(TokenType.ID);
		}
		//dcl->intdcl id
		else if(predict(dclProductions.get(1)).contains(nxt.getType()))
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
		ArrayList<String> stmsProductions = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				stmsProductions.add(grammar.get(i));
		}
		
		// Dcl ha 2 regole: stms->stm stms
		if(predict(stmsProductions.get(0)).contains(nxt.getType()))
		{
			parseStm();
			parseStms();
		}
		//stms->eps
		else if(predict(stmsProductions.get(1)).contains(nxt.getType()))
		{
			//EPS
			return true;
		}
		else
			throw new SyntacticException();
		
		return true;
	}
	private boolean parseStm() throws LexicalException, IOException, SyntacticException
	{
		String nonTerm = "Stm";
		Token nxt = scanner.peekToken();
		ArrayList<String> stmProductions = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				stmProductions.add(grammar.get(i));
		}
		
		// Dcl ha 2 regole: stm->id assign val expr
		if(predict(stmProductions.get(0)).contains(nxt.getType()))
		{
			match(TokenType.ID);
			match(TokenType.ASSIGN);
			parseVal();
			parseExpr();
		}
		//stm->print id
		else if(predict(stmProductions.get(1)).contains(nxt.getType()))
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
		ArrayList<String> exprProductions = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				exprProductions.add(grammar.get(i));
		}
		
		// Dcl ha 3 regole: Expr->plus Val Expr
		if(predict(exprProductions.get(0)).contains(nxt.getType()))
		{
			match(TokenType.PLUS);
			parseVal();
			parseExpr();
		}
		//Expr->minus Val Expr
		else if(predict(exprProductions.get(1)).contains(nxt.getType()))
		{
			match(TokenType.MINUS);
			parseVal();
			parseExpr();
		}
		else if(predict(exprProductions.get(2)).contains(nxt.getType()))
		{
			//EPS
			return true;
		}
		else
			throw new SyntacticException();
		
		return true;
	}
	
	private boolean parseVal() throws LexicalException, IOException, SyntacticException
	{
		String nonTerm = "Val";
		Token nxt = scanner.peekToken();
		ArrayList<String> valProductions = new ArrayList<String>();
		
		//agginge a una lista le regole che hanno come LHS questo non Term
		for(int i=0; i<grammar.size(); i++)
		{
			if(grammar.get(i).startsWith(nonTerm))
				valProductions.add(grammar.get(i));
		}
		
		// Dcl ha 3 regole: Val->inum
		if(predict(valProductions.get(0)).contains(nxt.getType()))
			match(TokenType.INUM);
	
		//Expr->minus Val Expr
		else if(predict(valProductions.get(1)).contains(nxt.getType()))
			match(TokenType.FNUM);

		else if(predict(valProductions.get(2)).contains(nxt.getType()))
			match(TokenType.ID);
		
		else
			throw new SyntacticException();
		
		return true;
	}
	
	// calcola se la regola può produrre eps
	private boolean derEmpty(String line) throws SyntacticException
	{
		return(RHS(line).matches("eps "));
	}
	
	private String scanProduction(String production, int index)
	{
		String tmp[] = production.split(" ");

		return tmp[index];
	}
	
	public String LHS(String production) throws SyntacticException
	{
		return splitter(production)[0];
	}
	
	public String RHS(String production) throws SyntacticException
	{
		if(production.contains("->"))
			return splitter(production)[1];
		else
			return production;
	}
	
	private String[] splitter(String production) throws SyntacticException
	{
		String[] a = production.split("->");
		
		if(a.length != 2)
			throw new SyntacticException("Wrong/no separator");
		
		return a;
	}
	
	public String first(String inProduction) throws Exception
	{
		visitato = new HashMap<String, Boolean>();
		
		for(int j=0; j<notTerminals.size(); j++)
			visitato.put(notTerminals.get(j), false);
		
		return firstRic(inProduction).trim();
	}
	
	public String firstRic(String inProduction) throws Exception
	{
		String ret = "", beta, firstBeta, secondBeta;
		
		beta = RHS(inProduction);
		firstBeta = scanProduction(beta, 0);
			
		if(firstBeta.equals("eps"))
			ret = "";
		//caso beta terminale
		else if(! notTerminals.contains(firstBeta))
			ret += " " + firstBeta;
		//caso beta non term, e non visitato
		else if(! visitato.get(firstBeta))
		{
			visitato.put(firstBeta, true);
			
			for(String p : grammar)
				if(LHS(p).equals(firstBeta))
					ret += " " + first(p);
			
			if(derEmpty(firstBeta))
			{
				secondBeta = scanProduction(beta, 1);
				ret += " " + first(secondBeta);
			}
		}
		
		return ret;
	}
	
	//ritorna la parola (RHS) seguente al non terminale input
	private ArrayList<String> segue(String notTerminal)
	{
		String[] lineSplitted;
		ArrayList<String> ret = new ArrayList<String>();
		boolean end = false;
		
		//scorre ogni regola
		for(String prod : grammar)
		{
			//divide la regola in parole
			lineSplitted = prod.split(" ");
			end = false;
			
			//scorre tutte le parole (dalla seconda, cioè inizio RHS)
			for(int i=1; i<lineSplitted.length && !end; i++)
			{
				if(lineSplitted[i].equals(notTerminal))
				{
					//se trova una parola uguale al non term
					//aggiunge a una lista la parola seguente
					if(i<lineSplitted.length - 1)
						ret.add(lineSplitted[i+1]);
					
					end = true;
				}
			}
		}
		
		return ret;
	}
	
	public String follow(String inNT) throws Exception
	{
		String ret = "";
		Set<String> noDup = new HashSet<>();	//set può eliminare i duplicati
		ArrayList<String> tmpList;				//appoggio per il set
		String[] tmpArray;
		Iterator<String> t;
		
		visitato = new HashMap<String, Boolean>();
		
		if(! notTerminals.contains(inNT))
			return "";
		
		for(int j=0; j<notTerminals.size(); j++)
			visitato.put(notTerminals.get(j), false);
		
		//da rivedere eliminazione duplicati
		//trasformo in array per passare in arraylist, poi aggiungo tutto a Set, che elimina duplicati
		ret = followRic(inNT).trim();
		tmpArray = ret.split(" ");
		tmpList = new ArrayList<String>(Arrays.asList(tmpArray));
		noDup.addAll(tmpList);
		
		ret = "";
		t = noDup.iterator();
			 
		while(t.hasNext())
			ret += " " + t.next();
		
		return ret.trim();
	}
	
	private String followRic(String inNT) throws Exception
	{
		String ret = "", notTerm;
		ArrayList<String> followers;
		
		if(! visitato.get(inNT))
		{
			visitato.put(inNT, true);
			followers = segue(inNT);			//cerca i term/nonTerm che seguono inNT
			
			for(String word : followers)		//le scorre e cerca a quale regola sono associate
				for(String prod : grammar)
				{
					/*scorre le parole che seguono inNT:
					 * scorre tutta la grammatica; se trova che una di queste parole è un LHS
					 * allora aggiunge il first di questa regola (perchè un non term)
					 */
					notTerm = LHS(prod);
					if(notTerm.equals(word))	//esiste una regola con LHS quel follower
					{
						ret += " " + first(prod);
						if(derEmptyNT.contains(notTerm))
							ret += " " + follow(LHS(prod));
					}
					else if(! notTerminals.contains(word))//altrimenti, se quella parola è un terminale lo aggiunge subito
						ret += " " + word;
				}
		}
		
		return ret;
	}
	
	private ArrayList<String> predict(String production)
	{
		return null;
	}
}