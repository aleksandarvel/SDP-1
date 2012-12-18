package Matrix;

public class MatrixDouble {
	private int columms;
	private int rows;
	
	private int columms1;
	private int rows1;
	
	private int [][] matI;
	private double[][] mat;
	private double[][] mat1;
	
	//public Matrix(int c,int r){this.columms=c;this.rows=r;}
	public MatrixDouble(){
		
	}
	public MatrixDouble(double [][]t){
		this.mat=t;
		this.rows = t.length;        
		this.columms = t[0].length;  
	}
	public MatrixDouble(int [][]t){
		this.matI=t;
		this.rows = t.length;        
		this.columms = t[0].length;  
	}
	
	
	public MatrixDouble(double [][]t,double [][]t1){
		this.mat=t;
		this.rows = t.length;        
		this.columms = t[0].length;  
		this.mat1=t1;
		this.rows1 = t1.length;        
		this.columms1 = t1[0].length;
		
		
	}
	
	public double [][] MatrixProduct(double [][]t,double [][]t1){
		this.mat=t;
		this.rows = t.length;        
		this.columms = t[0].length;  
		this.mat1=t1;
		this.rows1 = t1.length;        
		this.columms1 = t1[0].length;
		double [] [] rez = new double [rows][columms1];
		
		if(columms!=rows1) System.out.println(" this matrix cannot be multiplied!!!!!!!!!!!!!!!!!!");
		else
		{
			
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columms1;j++)
			{
				rez[i][j]=0;
				for(int k=0;k<columms;k++)
				
				rez[i][j]+=mat[i][k]*mat1[k][j];
			}
		}
		
		
		}
		return rez;
	}
	
	public double [] MatrixProductSingle(double []t,double [][]t1){
		
		double [] rez=new double[t1[0].length];
		int a=t1[0].length;
		int b=t.length;
		
		if(t.length!=t1.length) System.out.println(" this matrix cannot be multiplied !!!!!!!!!!!!!");
		else
		{
			
		for(int i=0;i<a;i++)
		{
			rez[i]=0;
			for(int j=0;j<b;j++)
			{
				rez[i]+=t[j]*t1[j][i];
			}
		}
		
		
		}
		return rez;
	}
	
	public boolean MatrixEqual(double [][]t,double [][]t1)
	{
		this.mat=t;
		this.rows = t.length;        
		this.columms = t[0].length;  
		this.mat1=t1;
		this.rows1 = t1.length;        
		this.columms1 = t1[0].length;
		
		boolean com=true;
		
		if((columms!=columms1)||(rows!=rows1))
			{
			com=false;
			
			return com;
			}
		
		else
		{	
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columms1;j++)
			{
				if (t[i][j]!=t1[i][j]) { return false;}  // is break needed ???
						
			}
		}
		}
		return com;
		
	}
	
	
	public double [][] MatrixCopy(double [][]t)   //this maybe improved
	{
		double [][] t1=  new double[t.length][t[0].length];
		 
		for(int i=0;i<t.length;i++)
		{
			for(int j=0;j<t[0].length;j++)
			{
				t1[i][j]=t[i][j];
						
			}
		}
		return t1;
	
		
	}
	
	public double [][] MatrixTransponse(double [][]t)   //this maybe improved
	{
		double [][] t1=  new double[t[0].length][t.length];
		 
		for(int i=0;i<t.length;i++)
		{
			for(int j=0;j<t[0].length;j++)
			{
				t1[j][i]=t[i][j];
						
			}
		}
		return t1;
	
		
	}
	
	
	public void PrintMatrix(int [][] matI)
	{
		System.out.println(" ");
		for (int i=0;i<matI.length;i++)
		{
			for (int j=0;j<matI[0].length;j++)
			{
				System.out.print("\t "+  matI[i][j]);
				
			}
			System.out.println(" ");
		}
	}
	
	public void PrintMatrix(double [][] matI)
	{
		System.out.println(" ");
		for (int i=0;i<matI.length;i++)
		{
			for (int j=0;j<matI[0].length;j++)
			{
				System.out.print("\t "+  matI[i][j]);
				
			}
			System.out.println(" ");
		}
	}
	
	public double Difference(double [][]t,double [][]t1)
	{
		
		
		double sum=0;
		
		if((t.length!=t1.length)&&(t[0].length !=t1[0].length))
			{
			System.out.println("Array can not be compared");
			return 10000;
			}
		
		else
		{	
		for(int i=0;i<t.length;i++)
		{
			for(int j=0;j<t[0].length;j++)
			{
			sum+=Math.abs(t[i][j]-t1[i][j]);
			}
		}
		}
		return sum;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
