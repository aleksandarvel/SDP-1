package SDP;

/*
 * The stopping criteria numbers should be declared as constants in the programm 
 * The code is running Ok but needs little bit better documentation and review to be excelent
 * make the output with text !
*/
import Data.AbstactData;
import Data.IData;
import Matrix.*;
public class StochasticDP2 {
	

	private double [] storageD; //array of storage discretisation
	private int sD; //storage discretization number

	private int n; // number of iteration
	
	private int timestep;
	
	// next should be separate class flow diskretization and transition matrices
	//maybe basic class and stochasticDP1 to be inheritance 
	
	private double [] flowD;
	private int fD; //flow discretization number
	
	private double [][][] tVFDtable;
	
	double nInfiniteDouble = Double.NEGATIVE_INFINITY;
	
	private int [][][] OptimalReservoirPolicy;
	private double [][][] OptimalReleasePolicy;
	
	private double [][] UnconditionalFlowProbabilities;
	private double [][] UnconditionalStorageProbabilities;
	private double [][][] UnconditionalStorageFloWProbabilities;

	private double [][][] TM;
	
	double [][][] allocated = new double [sD][fD][sD];
	
	double discount_factor=1;
	
	public StochasticDP2(int numberItteration){	 this.n=numberItteration;	}
	
	//HERE NUMBER OF YEAR/timestep IS ENTERED
	private boolean print=true;
	
	
	public void setPrint(){ print=true;}
	public void unsetPrint(){print = false;}
	
	public void setDiscount(double disc){ discount_factor=disc;}
	public void unsetDiscount(){discount_factor = 1.0;}
	
	
	public void inicialize () throws Exception
	{
		
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
		
		
		double [] stDisk={0,3,6};
		this.storageD=stDisk;
		this.timestep=3;
		
		
		// Calculate the transition matrices
		
		transitionMatrices TM= new transitionMatrices();  
		TM.inicialize();
		
		TM.initialreadData();
		
		TM.calcuateTransitionMatrix();
		

	//	this.TM=TM.getTransMatrice();
		this.flowD=TM.getflowDiscretization();
		
		
		this.TM=TM.getTransMatrice();
		
		
	}

	
	
	
	
	
	
	public void StochasticDP() throws Exception
	{
		
		
		//inicialize  flow and storage discretization		
		int fD=flowD.length;
		int sD=storageD.length;
		this.fD=fD;
		this.sD=sD;
		
		int i,j ,k;
		
		// allocated and tCFDtable is only for one step e.g from           all states --> all possible inflows -->> all possible states
		// in one period of time e.g the entire table is 4 dimensional with the 4 dimensiont time (timesteps)
		// this solution saves computational resources
		double [][][] allocated = new double [sD][fD][sD];
		double [][][] tVFDtable= new double [sD][fD][sD]; // transition value flow table
	

		
		for (i=0;i<sD;i++)
		
		{	
		for (j=0;j<fD;j++)
			{
			
				for(k=0;k<sD;k++)
				{
				if(	(storageD[i]+flowD[j]-storageD[k])<0)
					allocated[i][j][k]=nInfiniteDouble;   // if final state is more tnat begining state + inflow it is infinite (not possible)
				else
					allocated[i][j][k]=storageD[i]+flowD[j]-storageD[k]; //this is water allocated between 2 states (storage volumes)
				
					
				}
			}
		}
		
		this.allocated=allocated;
		
		
		Matrix3DDouble pr1= new Matrix3DDouble(); //this matrix is used for printing and calculations 
											if (print){
												System.out.println("od tukaa");
												pr1.PrintMatrix3D();
												System.out.println("do tukaaa");
												}
		
		// this class calculates square error with several objectives 
		stochasticDP1OptimalAllocation t1=new stochasticDP1OptimalAllocation(allocated,sD,fD,storageD,flowD,timestep);
		if (print) t1.setPrint();
		else t1.unsetPrint();
		t1.initializeDataStorage();
		tVFDtable=t1.sumSquaredDeviation(timestep-1);// VERY IMPORTATNT SYM SQUARE DEVIATION IS CHANGING DUE TO DIFFERENT DEMANDS
	
		if (print){
		System.out.println("ovdeeee ");
		System.out.println(" ");
		pr1.PrintMatrix3D(tVFDtable);
		}
		
		
		
		
		// initialization of propagation matrix
		//first iteration
	
		double [][] errorT = new double  [sD][fD];
		int [][][] stateT = new int [timestep][sD][fD];
		double [][][] releaseT = new double [timestep][sD][fD];
		
		int  [][][] OptimalReservoirPolicy= new int [timestep] [fD][sD]; //final result1 !!! change 2 when more timestep ??
		

		/* The code down is the first itteration of SDP. This code is creating 2D matrix errorT
		 * the code is searcing for the final storage that has the least error 
		 * thats why k goes from 0 -- sD 
		 * all final storages are investigated and in which the value is the smallest from the tVFD table
		 * that is taken as optimal path !!
		 * 
		 */
		for (i=0;i<sD;i++)
		{
			for(j=0;j<fD;j++) 
			{			
				//take the first value [i][j][0] for starting !! Calculation of the last period
				errorT[i][j]=tVFDtable[i][j][0];
				stateT[timestep-1][i][j]=0;
				releaseT[timestep-1][i][j]=allocated[i][j][0];
				
				for(k=0;k<sD;k++)
				{
					if(errorT[i][j]>=tVFDtable[i][j][k])
					{// if values are smaller replace
						errorT[i][j]=tVFDtable[i][j][k];
						stateT[timestep-1][i][j]=k;
						releaseT[timestep-1][i][j]=allocated[i][j][k];
					}
				}
				
			}
			
		}
		
		MatrixDouble pr3= new MatrixDouble(errorT);
		
						if (print){
									System.out.println("errorr");
									pr3.PrintMatrix(errorT);
		
									System.out.println("___________________ ");
		
		
		
									System.out.println(" state ");
									pr3.PrintMatrix(stateT[timestep-1]);
		
									System.out.println("_____________");
		
		
		
		
									System.out.println("release ");
									pr3.PrintMatrix(releaseT[timestep-1]);
		
									System.out.println("_____________");
		
								}
		
		double [][][] stFinReq= new double [sD][fD][sD]; 
		
	
		
		
		
		
		
		double [][] tmat;   //transition matrices
		
		/*stop1 and stop2 count how many times the same matrix result will be repeated in SDP algorithm
		 * if they are repeated 2  consequtive  times then the calculation stops 
		 */

		int itterationCycle=0;

		int brojac=timestep*n-1; // this is because one iteration is done previously -1
		int itteration;
		int repeat_result=0; // stoping criteria
		
		
		
		while ((brojac--)>0)
		{
			
			itteration=brojac% timestep;
		
			if (itteration==0)itterationCycle++;
								if (print ) System.out.println("_________________________ITTERATION " + itteration);
			
			tmat=TM[itteration];	
			MatrixDouble g1= new MatrixDouble();
								if (print){
											
			
											System.out.println(" errorT matrix");
											g1.PrintMatrix(errorT);
											System.out.println("_______________________________________________" );
			
											System.out.println(" transition matrix");
											g1.PrintMatrix(tmat);
											System.out.println("_______________________________________________" );
											}
			double[][] errorTransponse = g1.MatrixTransponse(errorT);
			
			
								if (print){
											System.out.println(" transponed transition matrix");
											g1.PrintMatrix(errorTransponse);
											System.out.println("_______________________________________________" );
											}
								
								
			double[][] rezMat = g1.MatrixProduct(tmat, errorTransponse);
								if (print){
											System.out.println("Period error matrix multipled with transformation matrix" );
											g1.PrintMatrix(rezMat);
											System.out.println("_______________________________________________" );
										 }
			
			double[][] rezMatTran= g1.MatrixTransponse(rezMat);

			
								if (print){
											System.out.println("Period error matrix " );
											g1.PrintMatrix(rezMatTran);
											System.out.println("_______________________________________________" );
											}
			
			
			// on every itteration the sumSquareDeviation table is differente depending of the Demand and other constrains  that will create the total SumSquare Deviation
			tVFDtable=t1.sumSquaredDeviation(itteration);
			for (i=0;i<sD;i++)
			{
				for(j=0;j<fD;j++)
				{
					for(k=0;k<sD;k++)
					{
						if (discount_factor==1.0)
							stFinReq[i][j][k]=tVFDtable[i][j][k]+rezMatTran[i][j];  //discount factor can be applied here
						else
							stFinReq[i][j][k]=Math.pow(discount_factor,itterationCycle)*tVFDtable[i][j][k]+rezMatTran[i][j]; 
							
					}
					
				}
				
			}
			
			
	
			if (print) pr1.PrintMatrix3D(stFinReq);
			
			
			for (i=0;i<sD;i++)
			{
				for(j=0;j<fD;j++) 
				{		
					//take the first value [i][j][0] for starting
					errorT[i][j] =stFinReq[i][j][0];
					stateT[itteration][i][j]=0;
					releaseT[itteration][i][j]=allocated[i][j][0];         
					
					for(k=0;k<sD;k++)
					{
						if (stFinReq[i][j][k]<=errorT[i][j])
						{
							errorT[i][j]=stFinReq[i][j][k];
							stateT[itteration][i][j]=k;
							releaseT[itteration][i][j]=allocated[i][j][k];
							
						}
					}
					
					
				}
			}

		if (print){
					System.out.println("errorr1111");
					g1.PrintMatrix(errorT);
			
					System.out.println("___________________ ");
			
			
			
					System.out.println(" state 1111");
					g1.PrintMatrix(stateT[timestep-itteration-1]);
			
					System.out.println("_____________");
					
			
			
			

					System.out.println("release1111 ");
					g1.PrintMatrix(releaseT[timestep-itteration-1]);
			
					System.out.println("_____________");
			
					}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			/* stopping criteria 
			 * It will check if the optimal allocation is repeated twice to stop calculation
			 *  
			*/
			
		if (itterationCycle>2)// this is needed for warm up the matrices
			{	
		if (print)	System.out.println("ITTERATIOM CY    " + itterationCycle);
			
			pr1.PrintMatrix3D(stateT);
			
			if (pr1.MatrixEqual(OptimalReservoirPolicy,stateT)) { repeat_result++; System.out.println("EEEEEEJJJJ "+repeat_result);}
			if (repeat_result==2) 
			{
				System.out.println("The policy is repeating twice");
				pr1.PrintMatrix3D(OptimalReservoirPolicy);
				
				
				System.out.println("Bellow is the release policy");
				pr1.PrintMatrix3D(releaseT);
				
				
				
				System.out.println("NICE EXPLANATION");
				
				for (i=0;i<timestep;i++)
				{
					System.out.println("beggining period is  " + i); ;
					for(j=0;j<sD;j++)
					{
						System.out.println("                    state is  " + storageD[j]);;
						for(k=0;k<fD;k++)
						{
							System.out.print("              inflow is   " + flowD[k] );
							System.out.print(" optimal policy is   " + storageD[OptimalReservoirPolicy[i][j][k]]);
							
							System.out.println("   optimal release is  " +releaseT[i][j][k]);
						}
						
					}
					
				}
				
				
				
				
				break;
				
			}
			
			else
			{
			if (print)	System.out.println("EEEEEEJJJJ ");
				OptimalReservoirPolicy=pr1.MatrixCopy(stateT);
			}
			
			
			}
		
		}

		
			
			this.OptimalReservoirPolicy=OptimalReservoirPolicy;
			this.OptimalReleasePolicy=releaseT;
			
		
	}
	
	
	
	
	
	
	public void UnconditionalFlowProbabilities(double stopingPrecision )
	{

	// also this variables can be class variables (this.** 

	// transition matrices class
	int ts=timestep;
	//transitionMatrices TM= new transitionMatrices();
	//TM.inicialize();
	double [][] tmat;
	double [] calresult2= new double [fD];
	double [][] Finresult= new double [timestep][fD];
	double [][] CompareResult= new double [timestep][fD];
	int i,j;
	
	MatrixDouble pom = new MatrixDouble();
	
	//Initialization of unconditional flow probabilities matrix
	for (i=0;i<fD;i++){calresult2[i]=0;}  // all flow values should be 0
	calresult2[0]=1.0;		// only first will be 1 to start MARKOV process

	for (j=0;j<fD;j++){
	System.out.print("\t "+  calresult2[j]);
	}
		

	int	itteration=0;	
	int t=0;
	while (t==0)//DECLARE NUMBER OF ITTERATION
	{
	ts=itteration%timestep;
	tmat=TM[ts];
	MatrixDouble g1=new MatrixDouble();
	calresult2=g1.MatrixProductSingle(calresult2,tmat);

	//	Code for printing every step of the calculation
	
	 for (j=0;j<fD;j++){
	
		 System.out.print("\t "+  calresult2[j]);
		 Finresult[ts][j]=calresult2[j];
	}
	System.out.println(" ");
	
	

	//stopping criteria 
	//Similar to the previous (same code)

	if (itteration>10)// this is needed for warm up the matrices
	{	

	
		
		if (pom.Difference(Finresult, CompareResult)< stopingPrecision)
		{
			System.out.println("This is unconditional probability of flow in timestep in this number of steps    "+itteration);
			pom.PrintMatrix(CompareResult);
			t=1;
			break;
			
		}
		else
		{
			CompareResult=pom.MatrixCopy(Finresult);
		}
			
		

	}


	itteration++;

	}	

	
	this.UnconditionalFlowProbabilities=Finresult;
	}

	
	
	
	
	
	
	
	public void UnconditionalStorageProbabilities(int n ,double stopingPrecision)
	{

	int i,j,k,t;
	/* calResults is used to put all the results into one big matrix
	* first [fd] tells the number of timestep or transformation matrix  
	*/
	double [][][] calResult= new double [timestep][sD][sD];

	double [] calresult2 = new double [sD];
	double [][] Finresult = new double [timestep][sD];
	double [][] CompareResult = new double [timestep][sD];
	

	double [][] results=new double [sD][sD];
	//double [][] results1=new double [sD][sD];

	System.out.println("CALCULATION OF UNCONDITIONAL STORAGE PROBABILITIES");

	for (t=0;t<timestep;t++)
	{
	//int tperiod=(t+1)%timestep;

		for (i=0;i<sD;i++)
			{	
				for (j=0;j<sD;j++)
					{
					results[i][j]=0;	
		
						for(k=0;k<fD;k++)
						{
							if (OptimalReservoirPolicy[t][i][k]==j) 
							{
								results[i][j]+=UnconditionalFlowProbabilities[t][k];
		
							}
						}
		
					}
			}

	MatrixDouble y2=new MatrixDouble(results);
	
	calResult[t]=y2.MatrixCopy(results);

	}


	Matrix3DDouble y=new Matrix3DDouble(calResult);

	System.out.println("Transformation matrix from storage to storage ");
	y.PrintMatrix3D();






	for (i=0;i<sD;i++){calresult2[i]=0;}  // all flow values should be 0
	calresult2[0]=1.0;		// only first will be 1 to start MARKOV process
	System.out.println("this is first matrix for multiplying");
	for (j=0;j<sD;j++){
	System.out.print("\t "+  calresult2[j]);
	}
	System.out.println("_____________________________");
	
	
	
	int ts=0;
	int	itteration=0;	

	int t1=0;
	while (t1==0)//DECLARE NUMBER OF ITTERATION
	{
	ts=itteration%timestep;
	
	MatrixDouble y2 = new MatrixDouble();
	
	
	// this needs to be transponded to be reqursively calculated
	//
	//results1=y2.MatrixTransponse(calResult[ts]);
	//results1=y2.MatrixTransponse(calResult[ts]);

	calresult2=y2.MatrixProductSingle(calresult2,calResult[ts]);
	
	
	for (j=0;j<sD;j++){
		
		 System.out.print("\t "+  calresult2[j]);
		 Finresult[ts][j]=calresult2[j];
	}
	System.out.println("");
	
	//stopping criteria 
	//Similar to the previous (same code)
	
	

	if (itteration>10)// this is needed for warm up the matrices
	{	

	
		
		if (y2.Difference(Finresult, CompareResult)< stopingPrecision)
		{
			System.out.println("This is unconditional probability of flow in timestep in this number of steps    "+itteration);
			y2.PrintMatrix(CompareResult);
			t1=1;
			break;
			
		}
		else
		{
			CompareResult=y2.MatrixCopy(Finresult);
		}
			
		

	}


	itteration++;
	
	
	
	

		}

		

	




	this.UnconditionalStorageProbabilities=Finresult;
	
	

	}







	

	
	
	
	

	public void UnconditionalStorageFloWProbabilities()
	{
	int i,j,k;
	double [][][] result= new double[timestep][sD][fD];

	for (k=0;k<timestep;k++)
	{	
	for (i=0;i<sD;i++)
	{
		for (j=0;j<fD;j++)
		{
		
			result[k][i][j]=UnconditionalStorageProbabilities[k][i]*UnconditionalFlowProbabilities[k][j];
			
		}
	}
	}
	System.out.println("This is unconditional storage-flow probabilities ");
	Matrix3DDouble t1= new Matrix3DDouble(result);
	t1.PrintMatrix3D();
	}	
	
	

	
	
	public void Simulation(int startingState, double []flow) throws Exception
	{
		int state=startingState;
		
		double squared_cost=0;
		
		
		transitionMatrices TM= new transitionMatrices();  
		TM.inicialize();
		
		TM.initialreadData();
		
		TM.calcuateTransitionMatrix();
		
		
		
		
		stochasticDP1OptimalAllocation t1=new stochasticDP1OptimalAllocation(allocated,sD,fD,storageD,flowD,timestep);
		t1.initializeDataStorage();
		//tVFDtable=t1.sumSquaredDeviation(timestep-1);// VERY IMPORTATNT SYM SQUARE DEVIATION IS CHANGING DUE TO DIFFERENT DEMANDS
	
		
		
		
	
		int k,y;
		int t=0;
		for(t=0;t<timestep;t++)
		{
			k=TM.belongToInterval(flow[t]);
			y=OptimalReservoirPolicy[t][state][k];
			tVFDtable=t1.sumSquaredDeviation(t);
			
		
System.out.println( "timestep      volume           flow     flow_segment         release                            ending volume        squared_cost       " );	
			
System.out.println(t+ "            "+ storageD[state]+ "            "+ flow[t]+"             "+ "    "+k+ "   "	+"            " +allocated[state][k][y]+ 
		"                   "+ storageD[OptimalReservoirPolicy[t][state][k]] + "      "+ tVFDtable[state][k][y]);
			
			state=OptimalReservoirPolicy[t][state][k];
		squared_cost+=squared_cost+tVFDtable[state][k][y];
		}
		
		System.out.println(" This is the final solution" +squared_cost);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

		
	