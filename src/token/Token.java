package token;

public class Token
{
	private TokenType type;
	private String value;
	
	public Token(TokenType tipo, String valore)
	{
			this.type = tipo;
			this.value = valore;
	}
	
	public TokenType getType()
	{
		return type;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return (value == null) ? type.toString() : type + "," + value;
	}
}
