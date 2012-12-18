/* this code is unused at the moment 
 * the code is implemented directly into Stochastic dP1
 */

package Matrix;

public class MatrixProcess {

	

		private int columms;
		private int rows;
		private int deep; 
		private double [][][] mat;
		private double [][] result1;
		private double [][] result2;
		
		//public Matrix(int c,int r){this.columms=c;this.rows=r;}
		
		public MatrixProcess(double [][][]t){
			this.mat=t;
			this.rows = t.length;        
			this.columms = t[0].length;  
			this.deep = t[0][0].length;
		}
		
		
		
public void  MatrixP1(double[][][]t1)
{
	int rows = t1.length;        
	int columms = t1[0].length;  
	int deep = t1[0][0].length;
	
	double [][] rez1=new double[columms][rows];
	double [][] rez2=new double[columms][rows];
	int j,i,k;
	
	
	//System.out.println("This is matrix3d row "+rows +" and colums " + columms + "and deep" + deep);
	for (i=0;i<rows;i++)	
	{
		for (j=0;j<columms;j++)
		{
			double inf=10000000.0; // declare this positive infinity
				for (k=0;k<deep;k++)
				{
				if (t1[i][j][k]<inf)
					{
					rez1[j][i]=t1[i][j][k];
					rez2[j][i]=k;
					inf=t1[i][j][k];
					}
				}
		}
		
		
	}
	this.result1=rez1;
	
	this.result2=rez2;
}
public double [][] ReturnRez1()
{
	return result1;
}	
public double [][] ReturnRez2()
{
	return result2;
}
		
		public void PrintMatrix3D()
		{
			int j,i,k;
			System.out.println("This is matrix3d row "+rows +" and colums " + columms + "and deep" + deep);
			for (j=0;j<columms;j++)	
			{
				for (i=0;i<rows;i++)
				{
					
						for (k=0;k<deep;k++)
						{
							System.out.print("\t "+  mat[j][i][k]);
						}
						System.out.println(" ");
						
				}
				System.out.println("This is matrix3d row "+i +" and colums " + j);
				
			}
		}

		
	}
	
