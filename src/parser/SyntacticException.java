package parser;

public class SyntacticException extends Exception
{
	public SyntacticException() {};
	
	public SyntacticException(String msg)
	{
		super(msg);
	}
}
