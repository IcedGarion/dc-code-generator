package ast;

public class NodeId extends NodeStm
{
	private String name;
	
	public NodeId(String id)
	{
		this.name = id;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
