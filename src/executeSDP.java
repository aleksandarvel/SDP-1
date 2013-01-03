


import Data.AbstactData;
import Data.IData;
import SDP.StochasticDP2;
import Data.AbstactData;
import Data.IData;

public class executeSDP {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//nov promena !!123123

		
		
		
		
	
		
	double [] flow = {4.0,2.0,1.0};
	
	double discount_factor=0.99;
	
	
	
//	for (int i=0;i<inflow.length;i++)
	//	System.out.println("Inflow" +i+"   " + inflow[i]);
	//System.out.println("Inflow.lenght" + inflow.length);
		// storage discretization, flow discretization, periods, number of itteration
		StochasticDP2 prob=new StochasticDP2(40);
		prob.setDiscount(0.99);
		//prob.unsetPrint();
		prob.inicialize();
		prob.StochasticDP();
		prob.UnconditionalFlowProbabilities( 0.01);
		prob.UnconditionalStorageProbabilities(10,0.01);
		prob.UnconditionalStorageFloWProbabilities();
		prob.Simulation(0,flow);
	
	

	}
}
