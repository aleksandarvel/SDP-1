package Matrix;

public class Matrix3DDouble {
	
	private int rows;
	private int columms;
	private int deep; 
	private double[][][] mat;
	private int[][][] matI;
	//public Matrix(int c,int r){this.columms=c;this.rows=r;}
	
	public Matrix3DDouble(){}
	
	public Matrix3DDouble(double [][][]t){
		this.mat=t;
		this.rows = t.length;        
		this.columms = t[0].length;  
		this.deep = t[0][0].length;
	}
	
	
	public Matrix3DDouble(int [][][]t){
		this.matI=t;
		this.rows = t.length;        
		this.columms = t[0].length;  
		this.deep = t[0][0].length;
	}
	
	public void PrintMatrix3D()
	{
		int j,i,k;
	//	System.out.println("This is matrix3d row "+rows +" and colums " + columms + "and deep" + deep);
		
			for (i=0;i<rows;i++)
			{
				for (j=0;j<columms;j++)	
				{
				
					for (k=0;k<deep;k++)
					{
						System.out.print("\t "+  mat[i][j][k]);
					}
					System.out.println(" ");
					
			}
			System.out.println("");
			
		}
	}
	
	
	
	
	
	public void PrintMatrix3D(double [][][]t)
	{
		int j,i,k;
	//	System.out.println("This is matrix3d row "+rows +" and colums " + columms + "and deep" + deep);
		
			for (i=0;i<t.length;i++)
			{
				for (j=0;j<t[0].length;j++)	
				{
				
					for (k=0;k<t[0][0].length;k++)
					{
						System.out.print("\t "+  t[i][j][k]);
					}
					System.out.println(" ");
					
			}
			System.out.println("");
			
		}
	}
	
	
	
	public void PrintMatrix3D(int [][][]t)
	{
		int j,i,k;
	//	System.out.println("This is matrix3d row "+rows +" and colums " + columms + "and deep" + deep);
		
			for (i=0;i<t.length;i++)
			{
				for (j=0;j<t[0].length;j++)	
				{
				
					for (k=0;k<t[0][0].length;k++)
					{
						System.out.print("\t "+  t[i][j][k]);
					}
					System.out.println(" ");
					
			}
			System.out.println("");
			
		}
	}
	
	

	
	public double [][][] MatrixCopy(double [][][]t)   //this maybe improved
	{
		double [][][] t1=  new double[t.length][t[0].length][t[0][0].length];
		 
		for(int i=0;i<t.length;i++)
		{
			for(int j=0;j<t[0].length;j++)
			{
				for(int k=0;k<t[0][0].length;k++)
			
			{
				t1[i][j][k]=t[i][j][k];
			}
			}
		}
		return t1;
	
		
	}
	
	
	public int[][][] MatrixCopy(int [][][]t)   //this maybe improved
	{
		int [][][] t1=  new int[t.length][t[0].length][t[0][0].length];
		 
		for(int i=0;i<t.length;i++)
		{
			for(int j=0;j<t[0].length;j++)
			{
				for(int k=0;k<t[0][0].length;k++)
			
			{
				t1[i][j][k]=t[i][j][k];
			}
			}
		}
		return t1;
	
		
	}
	
	
	
	public boolean MatrixEqual(double [][][]t1,double [][][]t2)
	{
		int rows1=t1.length;
		int colu1=t1[0].length;
		int deep1=t1[0][0].length;
		
		int rows2=t2.length;
		int colu2=t2[0].length;
		int deep2=t2[0][0].length;
		
		boolean com=true;
		
		if((colu1!=colu2)||(rows1!=rows2)||(deep1!=deep2) )
			{
			com=false;
			
			return com;
			}
		
		else
		{	
		for(int i=0;i<rows1;i++)
		{
			for(int j=0;j<colu1;j++)
			{
				for(int k=0;k<deep1;k++)
				{
				if (t1[i][j][k]!=t2[i][j][k]) { return false;}  // is break needed ???
				}	
			}
		}
		}
		return com;
		
	}
	
	
	
	
	
	
	
	public boolean MatrixEqual(int [][][]t1,int [][][]t2)
	{
		int rows1=t1.length;
		int colu1=t1[0].length;
		int deep1=t1[0][0].length;
		
		int rows2=t2.length;
		int colu2=t2[0].length;
		int deep2=t2[0][0].length;
		
		boolean com=true;
		
		if((colu1!=colu2)||(rows1!=rows2)||(deep1!=deep2) )
			{
			com=false;
			
			return com;
			}
		
		else
		{	
		for(int i=0;i<rows1;i++)
		{
			for(int j=0;j<colu1;j++)
			{
				for(int k=0;k<deep1;k++)
				{
				if (t1[i][j][k]!=t2[i][j][k]) { return false;}  // is break needed ???
				}	
			}
		}
		}
		return com;
		
	}
	
	
	
}
