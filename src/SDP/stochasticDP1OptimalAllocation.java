package SDP;

import Data.AbstactData;
import Data.IData;

public class stochasticDP1OptimalAllocation {
	
	private double [][][] allocated;

	int sD,fD;
	int periods;
	
	private double [] storageTargetsFloods;
	private double [] storageTargetsRecreation;
	private double [] releaseTargets;
	
	private double [] storageTargetsFloodWeights;
	private double [] storageTargetsRecreationWeights;
	private double [] releaseTargetsWeights;
	
	private boolean print=true;
	
	
	public void setPrint(){ print=true;}
	public void unsetPrint(){print = false;}
	
	double [] stDisk;
	double [] flow;
	//double [] releaseTargets={2,3,1}; //release targents or other objectives should be listed here the matrix output is total square deviation
	
	
	
	
	
	public stochasticDP1OptimalAllocation(double [][][] allocated,int sD,int fD, double [] storageD,double [] flow, int periods)
	{ this.allocated=allocated;  this.sD=sD;  this.stDisk=storageD; this.fD=fD; this.flow=flow; this.periods=periods;}
	
	
	
	public void initializeDataStorage() throws Exception
	{
		
		System.out.println("This inicialization will put values in storage and release Targets for each period");
		/*
		 * And also put the weights on each of the component
		 * data will be put manual but this should be upgraded to put over a php form (internet)
		*/
		
		
		/* This is the part to connect to the database "Bregalnica" from Postgress
		 */
			// the default values for storagetargets,storagerecreation and release targets and their weights is 0
		double[] storageTargetsFloodsN=new double [periods];
		double[] storageTargetsRecreationN=new double [periods];
		double[] releaseTargetsN=new double [periods];
		double[] storageTargetsFloodsNWeights=new double [periods];
		double[]  storageTargetsRecreationNWeights=new double [periods];
		double[] releaseTargetsNWeights=new double [periods];
		int i;
	
		
		
		
		AbstactData test = new AbstactData();
		int a;
		a=test.testDatabaseConnection();
		
	IData test1 = new AbstactData();
		
		
		storageTargetsFloodsN=test1.getstorageTargetsFloods();
		storageTargetsRecreationN=test1.getstorageTargetsRecreation();
		releaseTargetsN=test1.getreleaseTargets();
		storageTargetsFloodsNWeights=test1.getstorageTargetsFloodWeights();
		storageTargetsRecreationNWeights=test1.getstorageTargetsRecreationWeights();
		releaseTargetsNWeights=test1.getreleaseTargetsWeights();
	
		
		if (print){
			for (i=0;i<periods;i++)
		{
				System.out.println(" ");
			System.out.println("storageTargetsFloodsN  [] ------" +i+ "  "+ storageTargetsFloodsN[i]);
			System.out.println("storageTargetsFloodsNWeights [] ------" +i+ "  "+  storageTargetsFloodsNWeights[i]);
			
			
		}	
		
			for (i=0;i<periods;i++)
			{
				System.out.println("storageTargetsRecreationN  [] ------" +i+ "  "+  storageTargetsRecreationN[i]);
				System.out.println("storageTargetsRecreationNWeights [] ------" +i+ "  "+  storageTargetsRecreationNWeights[i]);
			}
		
			for (i=0;i<periods;i++)
			{
				System.out.println("getreleaseTargets [] ------" +i+ "  "+ releaseTargetsN[i]);
				System.out.println("releaseTargetsNWeights [] ------" +i+ "  "+ releaseTargetsNWeights[i]);
			}
		
		}
	
		
		this.storageTargetsFloods=storageTargetsFloodsN;
		this.storageTargetsRecreation=storageTargetsRecreationN;
		this.releaseTargets=releaseTargetsN;
		
		
		// here are defined the weight !!!!
	
		
		
		
		
		
		this.storageTargetsFloodWeights=storageTargetsFloodsNWeights;
		this.storageTargetsRecreationWeights=storageTargetsRecreationNWeights;
		this.releaseTargetsWeights=releaseTargetsNWeights;
		
		
		
		
		
		
	}
	
	
	
	public double[][][] sumSquaredDeviation(int timestep)
	{
		
	
		double [][][] tVFDtable=new double [sD][fD][sD];
		
		int i,j,k;
		double recreation,flood,target;
	
		
		
		
		for(i=0;i<sD;i++)
		{
			for (j=0;j<fD;j++)
			{
				
					for(k=0;k<sD;k++)
					{
						
					
						recreation=0;
						flood=0;
						target=0;
						tVFDtable[i][j][k]=0;
						
						// first part if for Recreation target
				if 	(storageTargetsRecreationWeights[timestep]>0)
				{
					
			//if((storageTargetsRecreation[i]-stDisk[j])>0)
			recreation=	storageTargetsRecreationWeights[timestep]*( Math.pow((storageTargetsRecreation[timestep]-stDisk[i]), 2));
								
			//if((storageTargetsRecreation[i]-stDisk[k])>0)		
			recreation+=storageTargetsRecreationWeights[timestep]*(Math.pow((storageTargetsRecreation[timestep]-stDisk[k]), 2));
				}
		
				
				if 	(storageTargetsFloodWeights[timestep]>0)
				{
				
			if((storageTargetsFloods[timestep]-stDisk[i])<0)
			flood=storageTargetsFloodWeights[timestep]*( Math.pow((storageTargetsFloods[timestep]-stDisk[i]), 2));
								
			if((storageTargetsFloods[timestep]-stDisk[k])<0)		
			flood+=storageTargetsFloodWeights[timestep]*(Math.pow((storageTargetsFloods[i]-stDisk[k]), 2));
				}
			
				if 	(releaseTargetsWeights[timestep]>0)
				{
			if((releaseTargets[timestep]-allocated[i][j][k])>=0)
			target=releaseTargetsWeights[timestep]*( Math.pow((releaseTargets[timestep]-allocated[i][j][k]), 2));
								
			
		
				}
			
				//tVFDtable[i][j][k]=recreation+flood+target;	
				tVFDtable[i][j][k]=target;
	
			
			
								
			
		
	if (print) System.out.println(" ova e "+ i +"  "+ j + "  "+k+" "+releaseTargets[periods]+"    "+ allocated[i][j][k]+ "  ova e izlezot "+tVFDtable[i][j][k]);
				}
			
					
				
				
					}
				}
				
	
	
		
		return tVFDtable;		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	

}
