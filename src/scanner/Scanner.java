package scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

import scanner.LexicalException;
import token.Token;
import token.TokenType;

public class Scanner
{
	private PushbackReader buffer;
	private final String ID_PATTERN = "[a-e]|[g-h]|[j-o]|[q-z]";
	private final String[] KEYWORDS = {"f", "i", "p", "=", "+", "-"};
	private final TokenType[] ASSOCIATED_TOKENS = {TokenType.FLOATDCL, TokenType.INTDCL, TokenType.PRINT,
			TokenType.ASSIGN, TokenType.PLUS, TokenType.MINUS};
	private Token currentToken;
	
	public Scanner(String fileName) throws FileNotFoundException
	{
		buffer = new PushbackReader(new FileReader(fileName));
	}
	
	public Token peekToken() throws LexicalException, IOException
	{
		if(currentToken == null)
			currentToken = nextToken();

		return currentToken;
	}
	
	public Token nextToken() throws LexicalException, IOException
	{
		int next;
		char cNext;
		String sNext;
		Token retToken = null;
		
		if(currentToken != null)
		{
			Token t = currentToken;
			currentToken = null;
			return t;
		}
		
		do
		{
			next = buffer.read();
			
			//EOF?
			if(next == -1)
				retToken = new Token(TokenType.EOF, null);
			
			cNext = (char) next;
		}
		while(cNext == ' ' || cNext == '\n' || cNext == '\r' || cNext == '\t');
		
		if(retToken == null)
		{
			// A questo punto ho un carattere non blank: se è una cifra,
			// controllo int/float
			sNext = "" + cNext;
			if(sNext.matches("[0-9]"))
				retToken = scanNumber(sNext);
			else
			{
				// se non è una cifra, controlla se è una keyword e restituisce
				// n il token associato
				for(int i = 0; i < KEYWORDS.length; i++)
				{
					if(KEYWORDS[i].equals(sNext))
					{
						next = buffer.read();
						cNext = (char) next;

						// se ha trovato una keyword ma dopo non c'è un blank o
						// EOF (ma c'è un altro char) lancia excp
						if(next == -1)
							retToken = new Token(ASSOCIATED_TOKENS[i], null);
						else if(cNext == ' ' || cNext == '\n' || cNext == '\r' || cNext == '\t')
							retToken = new Token(ASSOCIATED_TOKENS[i], null);
						else
							throw new LexicalException("Illegal character: " + cNext + ", no blank");
					}
				}

				if(retToken == null)
				{
					sNext = "" + cNext;
					if(sNext.matches(ID_PATTERN))
					{
						next = buffer.read();
						cNext = (char) next;

						if(next == -1)
							retToken = new Token(TokenType.ID, sNext);
						else if(cNext == ' ' || cNext == '\n' || cNext == '\r' || cNext == '\t')
							retToken = new Token(TokenType.ID, sNext);
						else
							throw new LexicalException("Illegal character: " + cNext + ", no blank");
					}
					else
						throw new LexicalException("Illegal character: " + cNext);
				}
			}
		}
		
		return retToken;
	}
	
	private Token scanNumber(String firstDigit) throws LexicalException, IOException
	{
		String wholeNumber = firstDigit;
		int next = buffer.read();
		char cNext;
		String sNext;
		
		if(next == -1)						//se capita un EOF dopo una cifra la ritorna
			return new Token(TokenType.INUM, wholeNumber);
		
		cNext = (char) next;										//legge fino al '.' o fino a un blank
		sNext = "" + cNext;
		while(cNext != ' ' && cNext != '.' && cNext != '\t' && cNext != '\n' && cNext != '\r')
		{
			if(! sNext.matches("[0-9]"))
				throw new LexicalException("Illegal character: " + sNext);
			
			wholeNumber += cNext;
			next = buffer.read();
			cNext = (char) next;
			sNext = "" + cNext;
		}
		
		//se capita un EOF dopo una cifra la ritorna
		if(next == -1)							
			return new Token(TokenType.INUM, wholeNumber);
		
		//se l'ultimo letto era un blank, ho in INT
		if(cNext == ' ' || cNext == '\t' || cNext == '\n' || cNext == '\r')
			return new Token(TokenType.INUM, wholeNumber);
		
		//altrimenti è un FLOAT e quindi devo continuare con la lettura
		else
		{
			wholeNumber += '.';
			next = buffer.read();
			cNext = (char) next;										
			sNext = "" + cNext;
			while(cNext != ' ' && cNext != '\t' && cNext != '\n' && cNext != '\r')
			{
				if(! sNext.matches("[0-9]"))
					throw new LexicalException("Illegal character: " + cNext);
				
				wholeNumber += cNext;
				next = buffer.read();
				cNext = (char) next;
				sNext = "" + cNext;
			}
			
			return new Token(TokenType.FNUM, wholeNumber);
		}
	}
}