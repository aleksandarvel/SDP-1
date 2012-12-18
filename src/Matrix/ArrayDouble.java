package Matrix;

import java.math.*;


public class ArrayDouble {

	
	

		private int elements;	
		private double[] mat;

		
		//public Matrix(int c,int r){this.columms=c;this.rows=r;}
		public ArrayDouble(){
			
		}
		public ArrayDouble(double []t){
			this.mat=t;
			this.elements = t.length;        
			
		}
		
		
		public double[] ArrayDoubleDifference(double []t,double []t1)
		{
			double [] result= new double [t.length];
			result[0]=100;
			
			
			
			
			if(t.length!=t1.length)
				{
				System.out.println("Array can not be compared");
				return result;
				}
			
			else
			{	
			for(int i=0;i<t.length;i++)
			{
				result[i]= Math.abs(t[i]-t1[i]);
			}
			}
			return result;
			
			
		}
		
		
		
		public double DoubleDifference(double []t,double []t1)
		{
			double  wrongResult=11;
			double  rightResult=0;
			
			
			
			if(t.length!=t1.length)
				{
				System.out.println("Array can not be compared");
				return wrongResult;
				}
			
			else
			{	
			for(int i=0;i<t.length;i++)
			{
				rightResult=rightResult+ Math.abs(t[i]-t1[i]);
			}
			}
			return rightResult;
			
			
		}
	

		public double[] ArrayDoubleCopy(double []t)
		{
			double [] result= new double [t.length];
		
			for(int i=0;i<t.length;i++)
			{
				result[i]= t[i];
			}
		
			return result;
			
		}
		public void PrintMatrix(double []t)
		{
			System.out.println(" ");
			for (int i=0;i<t.length;i++)
			{
				
					System.out.print("\t "+  t[i]);
					
			}
				System.out.println(" ");
			
		}
		
		
}
