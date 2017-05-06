package token;

/**
 * Class Token rappresenta gli elementi più piccoli e specifici del linguaggio
 * 
 * @author Garion Musetta
 */
public class Token
{
	private TokenType type;
	private String value;
	
	/**
	 * Costruttore: inizializza il Token con TipoToken (e valore)
	 * 
	 * @param tipo			Che tipo di Token è
	 * @param valore		Quale informazione contiene
	 */
	public Token(TokenType tipo, String valore)
	{
			this.type = tipo;
			this.value = valore;
	}
	
	/**
	 * Restituisce il Tipo di Token
	 * 
	 * @return 		Il tipo del Token
	 */
	public TokenType getType()
	{
		return type;
	}
	
	/**
	 * Restituisce l'informazione associata al Token
	 * 
	 * @return		Il valore contenuto nel token
	 */
	public String getValue()
	{
		return value;
	}
	
	/**
	 * Rappresentazione in stringa del Token
	 * 
	 * @return 		una stringa che rappresenta il token
	 */
	public String toString()
	{
		return (value == null) ? type.toString() : type + "," + value;
	}
}
