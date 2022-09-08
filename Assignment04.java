import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;


public class Assignment04 
{
	static Pair goal;
	static Pair start;
	static ArrayList<Pair> shortest = new ArrayList<Pair>();
	static PriorityQueue<Pair> frontier = new PriorityQueue<Pair>();
	static ArrayList<Pair> visited = new ArrayList<Pair>();
	static char[][] board;
	
	
	public static void main(String[] args) 
	{
		boolean done = false;
		while(!done){
			shortest = new ArrayList<Pair>(); 
			frontier = new PriorityQueue<Pair>();
			visited = new ArrayList<Pair>();
			board = new char[15][15];
			for(int i=0; i<15; i++)
			{
				for(int j=0; j<15; j++)
				{
					board[i][j]='-';
				}
			}
			Random random = new Random();
			for(int x = 1; x<=22; x++)
			{
				int r1 = random.nextInt(15 - 1);
				int r2 = random.nextInt(15 - 1);
				//System.out.println(r1 +"," + r2);
				if(board[r1][r2] == 'o')
				{
					while(board[r1][r2]!='o')
					{
						r1 = random.nextInt(15 - 1) + 1;
						r2 = random.nextInt(15 - 1) + 1;
					}				
				}
				board[r1][r2]=(char)111;

			}
			
//			board[0][5] = 'o';
//			board[1][5] = 'o';
//			board[2][5] = 'o';
//			
//			board[0][9] = 'o';
//			board[1][9] = 'o';
			
			print(board);
			
			
			
			boolean good = false;
			int row1 =0,col1 =0;
			while(!good)
			{
				Scanner scan = new Scanner(System.in);
				System.out.println("Enter start row (1-15)");
				row1 = scan.nextInt();
				System.out.println("Enter start col (1-15)");
				col1 = scan.nextInt();
				
				if(row1>15 || col1>15|| row1<1 || col1<1 || board[row1-1][col1-1] == 'o')
				{
					System.out.println("Invalid co-ordinates");
				}
				else {
					board[row1-1][col1-1] = 'S';
					good = true;
				}
			}
			good = false;
			int row2 =0,col2 =0;
			Scanner scan = new Scanner(System.in);

			while(!good)
			{
				System.out.println("Enter goal row (1-15)");
				row2 = scan.nextInt();
				System.out.println("Enter goal col (1-15)");
				col2 = scan.nextInt();
				
				if(row2>15 || col2>15 || row2<1 || col2<1 || board[row2-1][col2-1] == 'o'|| board[row2-1][col2-1] == 'S')
				{
					System.out.println("Invalid co-ordinates");
				}
				else {
					board[row2-1][col2-1] = 'G';
					good = true;
				}
			}
			
			print(board);


			start = new Pair(row1-1, col1-1, 0);
			goal = new Pair(row2-1, col2-1, 0);
			//System.out.println("x"+goal.x + ' ' + goal.y);
			
			frontier.add(start);

			Boolean result =aStarLoop();
			
			//------
			//------
			
			Pair curr = goal;
			
			while(curr.parent!=null)
			{
				shortest.add(curr.parent);
				curr = curr.parent;
			}
			
			
			if(!result)
			{
				System.out.println("FAIL, PATH NOT FOUND");
			}
			
			
			for(int i = shortest.size()-1; i>=0; i--)
			{
				if(board[shortest.get(i).x][shortest.get(i).y]=='-')
				{
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					board[shortest.get(i).x][shortest.get(i).y]= '=';
					print(board);
				}
				
			}
			
			
			//System.out.println(visited.equals(shortest));
			System.out.println("would you like to play again? (y/n)");
			String x = scan.next();
			if(x.toLowerCase().equals("n"))
			{
				done=true;
			}
		}
		
		
		
		//if(board[row1][col1])
		
	}
	
	public static void print(char[][] board)
	{
		System.out.println(" '=' is the shortest path");
		for(int i=0; i<15; i++)
		{
			for(int j=0; j<15; j++)
			{
				System.out.print(" " + board[i][j]);
			}
			System.out.println();
		}
	}
	
	public static int manhat(Pair one, Pair two)
	{
		return Math.abs(one.x-two.x) + Math.abs(one.y-two.y);
	}
	
	public static ArrayList<Pair> aStar(char[][] board, Pair start, Pair goal) //my first attempt at aStar in recursive, did not work so I created aStarLoop which is in another method
	{
		if(start.equals(goal))
		{
			return new ArrayList<Pair>();
		}
		else
		{
			visited.add(start);
			frontier.remove(start);
			shortest.add(start);
			Pair left = new Pair(start.x-1, start.y,0);
			Pair right = new Pair(start.x+1, start.y,0);
			Pair up = new Pair(start.x, start.y-1,0);
			Pair down = new Pair(start.x, start.y+1,0);
			if(start.x-1>=0 && board[start.x-1][start.y]!= 'o' && !frontier.contains(left) && !visited.contains(left))
			{
				//System.out.println("here1");
				frontier.add(left);
			}
			if(start.x+1<15 && board[start.x+1][start.y]!= 'o' && !frontier.contains(right) && !visited.contains(right))
			{
				//System.out.println("here2");
				frontier.add(right);
			}
			if(start.y-1>=0 && board[start.x][start.y-1]!= 'o' && !frontier.contains(up) && !visited.contains(up))
			{
				//System.out.println("here3");
				frontier.add(up);
			}
			if(start.y+1<15 && board[start.x][start.y+1]!= 'o' && !frontier.contains(down) && !visited.contains(down))
			{
				//System.out.println("here4");
				frontier.add(down);
			}
			if(frontier.isEmpty())
			{
				shortest.remove(start);
				//System.out.println("here");
				return null;
			}
			else
			{
				System.out.println("here");
				ArrayList <Pair> x = aStar(board, frontier.peek(), goal);
				x.add(start);
				return x;
			}
						
		}
			
			
			
		//start goal
		
	}
	
	public static void action(Pair todo, Pair parent)
	{
		for(Pair cood: visited)
		{
			if(cood.equals(todo))
			{
				return;
			}
		}
		todo.g = parent.g+1;
		todo.setF(todo.g+manhat(todo,goal));
		todo.setP(parent);

		//System.out.println("SIZE" + frontier.size());
		for(Pair infront: frontier)
		{
			if(infront.equals(todo))
			{
				if(todo.g>infront.g)
				{
					//System.out.println("EEHRHEHREHRE");
					return;
				}
			}
		}
		
		frontier.add(todo);
		return;
	}

	public static Boolean aStarLoop()
	{
		
		 Pair current = start;
		 while(!frontier.isEmpty())
		 {
			current = frontier.poll();
			visited.add(current);
			if(board[current.x][current.y] == 'G')
			{
				goal.setP(visited.get(visited.size()-1));
				return true;
			}
			//System.out.println(current.x + "," + current.y + "f" + current.f);
			 
			Pair left = new Pair(current.x-1, current.y, 0);
			Pair right = new Pair(current.x+1, current.y, 0);
			Pair up = new Pair(current.x, current.y-1, 0);
			Pair down = new Pair(current.x, current.y+1, 0);
			if(left.x>=0 && board[left.x][left.y]!= 'o' && board[left.x][left.y] != 'S')
			{
				action(left, current);
			}
			
			if(right.x<15 && board[right.x][right.y]!= 'o' && board[right.x][right.y] != 'S')
			{
				action(right, current);
			}
			if(down.y<15 && board[down.x][down.y]!= 'o' && board[down.x][down.y] != 'S')
			{
				action(down, current);
			}
			if(up.y>=0 && board[up.x][up.y]!= 'o' && board[up.x][up.y] != 'S')
			{
				action(up, current);
			}
			
//			System.out.print('{');
//			for(Pair x: frontier) {
//				System.out.print("(" + x.x + "," + x.y + "g" + x.g +")");
//			}
//			System.out.println('}');
			
			

		 }
		 
		 return false;

	}

}



