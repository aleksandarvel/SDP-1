package Data;


public interface IData {
	
	/**  Storage 
	 * @throws Exception */

	
	public double[] getStorageDiscretisation() throws Exception;
	

	public double[] getInflow() throws Exception;
	
	public double[] getstorageTargetsFloods() throws Exception;
	
	public double[] getstorageTargetsRecreation() throws Exception;
	
	public double[] getreleaseTargets() throws Exception;
	
	public double[] getstorageTargetsFloodWeights() throws Exception;
	
	
	public double[] getstorageTargetsRecreationWeights() throws Exception;
	
	
	public double[] getreleaseTargetsWeights() throws Exception;
	
	public void  putStorageOptimal( double [] Optimal, int fD ) throws Exception;
}
