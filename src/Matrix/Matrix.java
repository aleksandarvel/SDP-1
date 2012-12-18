package Matrix;

public class Matrix {

	
	private int rows;
	private int columms;
	private int[][] mat;
	
	//public Matrix(int c,int r){this.columms=c;this.rows=r;}
	
	public Matrix(int [][]t){
		this.mat=t;
		this.rows = t.length;        
		this.columms = t[0].length;  
	}
	
	public void PrintMatrix()
	{
		for (int i=0;i<rows;i++)
		{
			for (int j=0;j<columms;j++)
			{
				System.out.print("\t "+  mat[i][j]);
			}
			System.out.println(" ");
		}
	}
	
	
	
	
}
