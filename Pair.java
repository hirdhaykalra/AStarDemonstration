
public class Pair implements Comparable<Pair> {

	public int x;
	public int y;
	public Pair parent;
	public int g;
	public int f;
	
	public Pair()
	{
		x=0;
		y=0;
		parent = null;
		g=0;
		
	}
	
	public Pair(int x, int y, int g)
	{
		this.x = x;
		this.y = y;
		parent = null;
		this.g=g;
	}
	
	public Boolean equals(Pair other)
	{
		if(this.x == other.x && this.y == other.y) {
			return true;
		}
		else return false;
	}
	
	public void setP(Pair x)
	{
		parent = x;
	}
	
	public void setF(int x)
	{
		f=x;
	}
	
	@Override
	public int compareTo(Pair other)
	{
		Assignment04 as = new Assignment04();
		int h1 = this.g + as.manhat(this, as.goal);
		int h2 = this.g + as.manhat(other, as.goal);
		return h1-h2;
		
//		if(h1==h2)
//		{
//			return 0;
//		}
//		if(h1<h2)
//		{
//			return 1;
//		}
//		else
//		{
//			return -1;
//		}
	}
}
