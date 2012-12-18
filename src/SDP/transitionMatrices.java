package SDP;

import Data.AbstactData;
import Data.IData;
import Matrix.*;


public class transitionMatrices {

	
	private int episodes; //number of transition Matrices
	private int timestep;
	private int sections;
	private double [][][] TM;
	double[] inlowRaw=new double [timestep*episodes];
	double [] flowDisk=new double[sections];
	double [] intervals= new double [sections+1];
	
	
	
	public transitionMatrices(){}


	public double [][] getTransMatricesbyTimestep(int timestep){	return TM[timestep];}
	
	public double [][][] getTransMatrice(){	return TM;}
	public double [] getflowDiscretization(){	return flowDisk;}
	
	
	public void inicialize ()
	{
		
		double [][] tmat3={{0.6,0.4},{0.3,0.7}};
		double [][] tmat2={{0.3,0.7},{0.5,0.5}};
		double [][] tmat1={{0.5,0.5},{0.5,0.5}};
		double [][][]tm=new double[3][tmat1.length][tmat1[0].length];
		tm[0]=tmat1;
		tm[1]=tmat2;
		tm[2]=tmat3;
		//tm[3]=tmat1;
		this.TM=tm;
		
		MatrixDouble g=new MatrixDouble(TM[0]);
		MatrixDouble g1=new MatrixDouble(TM[1]);
		MatrixDouble g2=new MatrixDouble(TM[2]);
		System.out.println("first transition matrix ");
		g.PrintMatrix(TM[0]);
		System.out.println("");
		System.out.println("Second transition matrix ");
		g1.PrintMatrix(TM[1]);
		System.out.println("");
		System.out.println("Third transition matrix ");
		g2.PrintMatrix(TM[2]);
		System.out.println("");
	}
	
	public void inicialize1 ()
	{
		
		double [][] tmat3={{0.2,0.2,0.3,0.3},{0.2,0.2,0.3,0.3},{0.2,0.2,0.3,0.3},{0.2,0.2,0.3,0.3}};
		double [][] tmat2={{0.2,0.2,0.3,0.3},{0.2,0.2,0.3,0.3},{0.2,0.2,0.3,0.3},{0.2,0.2,0.3,0.3}};
		double [][] tmat1={{0.2,0.2,0.3,0.3},{0.2,0.2,0.3,0.3},{0.2,0.2,0.3,0.3},{0.2,0.2,0.3,0.3}};
		double [][][]tm=new double[timestep][tmat1.length][tmat1[0].length];
		tm[0]=tmat1;
		tm[1]=tmat2;
		tm[2]=tmat3;
		//tm[3]=tmat1;
		this.TM=tm;
		
		MatrixDouble g=new MatrixDouble(TM[0]);
		MatrixDouble g1=new MatrixDouble(TM[1]);
		MatrixDouble g2=new MatrixDouble(TM[2]);
		System.out.println("first transition matrix ");
		g.PrintMatrix(TM[0]);
		System.out.println("");
		System.out.println("Second transition matrix ");
		g1.PrintMatrix(TM[1]);
		System.out.println("");
		System.out.println("Third transition matrix ");
		g2.PrintMatrix(TM[2]);
		System.out.println("");
	}
	
	
	
	public void initialreadData() throws Exception
	{
		
		System.out.println("Read timeserie data from database and calcuate transition matrices");
		/*
		 
		 
			// the default values for storagetargets,storagerecreation and release targets and their weights is 0
		
		  //this should be improved
	
		
		int i;
			
		AbstactData test = new AbstactData();
		int a;
		a=test.testDatabaseConnection();
		
	IData test1 = new AbstactData();
		
		
	inlowRaw=test1.getInflowRawData();
		numberofepisodes=test1.getNumberofepisodes();
		sections=test1.getsections();
		
			for (i=0;i<numberofepisodes*episodes;i++)
		{
				System.out.println(" ");
			System.out.println("Inflow Raw data  [] ------" +i+ "  "+ inlowRaw[i]);
			
			
			
		}	
		
		
		THE INFORMATION BELOW WILL BE READED FROM THE DATABASE
		*/

	double [] inlowRaw={1,2,3,4,5,2,3,4,2,1,2,3,2,4,5,3,6,4,2,2};
	int episodes = 6;
	int sections=2;
	int timesteps = 3;
	this.timestep=timesteps;
	this.inlowRaw=inlowRaw;
	this.episodes=episodes;
	this.sections=sections;
}

	
	
	
	
	
	public void calcuateTransitionMatrix() throws Exception
	{
		/* define how many discretization level will be in inflow
		 * 
		 * calculation of some coeficients 
		 */
	
		
		// expectancy
		
		int i,j,k;
		
		
		
		
		System.out.println(" average value is " + average(inlowRaw));
	
		System.out.println(" variation is value  " + variation(inlowRaw));
	
		System.out.println(" One step correlation  " + one_step_correlation(inlowRaw));
		
		double maximum = maximumElement(inlowRaw);
		System.out.println(" maximum element  " +maximum );
		
		System.out.println(" sections  " +sections);
		
		
		double [] intervals1 = new double [sections+1];
		
		//double [][] intervals_with_value= new double [sections][episodes*timestep];
		
		double [] flowDisc=new double [sections];
		
		
		for (i=0;i<=sections;i++)
		{
			intervals1[i]=i*maximum/sections;            // regular intervals -- can be with different interval not only regular
			System.out.print(" intervals  " +i*maximum/sections);
		}
		
		this.intervals=intervals1;
		
		int [][][] transMatrix = new int [timestep] [sections][sections];
		double [][][] transferMatrixStochastic = new double [timestep] [sections][sections];
		double [][] inputDataMatrix = new double [episodes][timestep];
		
		int t=0;
		for (i=0;i<episodes;i++)
		{
			for (j=0;j<timestep;j++)
			{
				inputDataMatrix[i][j]=inlowRaw[t];
				
				System.out.print(" -- "+t);
				t++;
			}
		}
		
		System.out.println(" episodes " +episodes);
		System.out.println(" Timesteps" +timestep);
		MatrixDouble pr3= new MatrixDouble(inputDataMatrix);
		System.out.println("inputDataMatrix");
		pr3.PrintMatrix(inputDataMatrix);
		
		System.out.println("___________________ ");
		
		
		
		double []intervalSum = new double [sections];
		double []intervalnumber = new double [sections];
		double [] averageInterval = new double [sections];
		int in1,in2,m;
		for (i=0;i<timestep-1;i++)
		{
			for (j=0;j<episodes;j++)
			{
				
				in1=belongToInterval(inputDataMatrix[j][i]);
				
				in2=belongToInterval(inputDataMatrix[j][i+1]);
				System.out.println(" timestep " +i + " episode  "+ j+ " data " + inputDataMatrix[j][i]+"  belogs to interval1   " + in1+
						" data2 " + inputDataMatrix[j][i+1]+"  belogs to interval2  " + in2);
				
				transMatrix[i+1][in1][in2]++;//  THE TRANSITION MATRIX START FROM 1 AND IT IS BEWEEN (O AND 1)
				
				intervalnumber[in1]++;
				intervalSum[in1]+=inputDataMatrix[j][i];
				
				
				System.out.println(" transMatrix " +(i+1) + " in1  "+ in1+  " in2  "+ in2+"  increase" + transMatrix[i][in1][in2]);
				System.out.println(" intervalnumber[in1]++ " +intervalnumber[in1] + " intervalSum[in1]  "+ intervalSum[in1]);                                                                                                         
			}
		}
		// One more calculation is needed to make trasfer matrix from the last and the 0-or first step
		for (j=0;j<episodes;j++)
		{
			
			in1=belongToInterval(inputDataMatrix[j][timestep-1]);
			
			in2=belongToInterval(inputDataMatrix[j][0]);
			System.out.println(" timestep " +0 + " episode  "+ j+ " data " + inputDataMatrix[j][timestep-1]+"  belogs to interval1   " + in1+
					" data2 " + inputDataMatrix[j][0]+"  belogs to interval2  " + in2);
			
			transMatrix[0][in1][in2]++; //	THIS IS THE TRANSITION MATRIX 0 THAT MEANS TRANSITION FROM FIRST-TO LAST OR LAST-TO FIRST !!!!
			
			intervalnumber[in1]++;
			intervalSum[in1]+=inputDataMatrix[j][0];
			
			
			System.out.println(" transMatrix " +0 + " in1  "+ in1+  " in2  "+ in2+"  increase" + transMatrix[timestep-1][in1][in2]);
			System.out.println(" intervalnumber[in1]++ " +intervalnumber[in1] + " intervalSum[in1]  "+ intervalSum[in1]);                                                                                                          
		}
		
		
		
		Matrix3DDouble pr2 = new Matrix3DDouble(transMatrix);
		pr2.PrintMatrix3D(transMatrix);
		
		System.out.println("___________________ ");
		
		
		
		
		double t1=0;
		double [][] sumRow= new double[timestep][sections];
		for (i=0;i<timestep;i++)
		{
			for (j=0;j<sections;j++)
			{	
			
				//sumRow[i][j]=0.0000000001;
				sumRow[i][j]=0;
				for (k=0;k<sections;k++)
				{
					sumRow[i][j]+=transMatrix[i][j][k];
				}
			}
		}
		
		
		
		for (i=0;i<timestep;i++)
		{
			for (j=0;j<sections;j++)
			{	
			
				for (k=0;k<sections;k++)
				{
					if(sumRow[i][j]==0)transferMatrixStochastic[i][j][k]=0;
					else
					{transferMatrixStochastic[i][j][k]=(transMatrix[i][j][k]/1.0)/sumRow[i][j];}
				}
			}
		}
		System.out.println("TRANSITION MATRICES  ");
		pr2.PrintMatrix3D(transferMatrixStochastic);
		
		System.out.println("___________________ ");
		
		
		this.TM=pr2.MatrixCopy(transferMatrixStochastic);
		
		
		for (i=0;i<sections;i++)
		{
			averageInterval[i]=intervalSum[i]/intervalnumber[i];
		}
		
		this.flowDisk=averageInterval;
		
		for (i=0;i<sections;i++)
		{
			System.out.println("flow discretization   "+flowDisk[i]);
		}
		
		
	}
	
	

	
	public int  belongToInterval(double value)
	{
	
	int i=0;
	
	for (i=0;i<intervals.length-1;i++)
	{
		if ((value>intervals[i])&& (value<=intervals[i+1]))
		{
			return i;
		
		}
	}
	System.out.println("this line should not print !!!!!");
	return i;
	}
	
	
	
	public double  maximumElement(double [] data)
	{
	
	double maximum=0;
	for (int i=0;i<data.length;i++)
	{
		if (maximum<inlowRaw[i]) maximum=inlowRaw[i];
	}
	
	//System.out.println(" Maximum inflow is  " + maximum);
	
	
	return maximum;
	}
	
	
	
	public double average(double [] data)
	{
		double sum=0;
		int i;
		for (i=0;i<data.length;i++)
		{
			sum+=data[i];
		}
		
		double average=sum/data.length;
		//System.out.println(" average value is " + average);
		return average;
		
	}
	
	
	
	
	public double variation(double [] data)
	{
		
		int i;
		double variation=0;
		double averageV=average(data);
		for (i=0;i<data.length;i++)
		{
			variation+=((averageV-inlowRaw[i])*(averageV-inlowRaw[i]));
			
		}
		
		variation=variation/data.length;
	
	//	System.out.println(" variation is value  " + variation);
		
		return variation;
		
	}
	
	
	public double one_step_correlation(double [] data)
	{
	
	double h1=0;
	double h=0;
	int i;
	double one_step_correlation=0;
	double averageV=average(data);
	for (i=0;i<data.length-1;i++)
	{
		h+=((averageV-inlowRaw[i])*(averageV-inlowRaw[i+1]));
		h1+=((averageV-inlowRaw[i])*(averageV-inlowRaw[i]));
	}
	
	one_step_correlation=h/h1;
	return(one_step_correlation);
	
	
	}
	
	
	
	
	
	
	
	
	
}
