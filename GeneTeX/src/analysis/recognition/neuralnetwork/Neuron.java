package analysis.recognition.neuralnetwork;

public class Neuron {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */

	private float value;
	
	private float synapticWeights[];
	
	private float nonLinearParameters[];
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
	public Neuron(Layer previousLayer){
		this.value = 0;
		this.synapticWeights = new float[previousLayer.size()];
	}

	
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	public void computeValue(Layer previousLayer){
		// TODO Auto-generated method stub
	}

	public void resetValue(){
		this.value = 0;
	}
	
	
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

	public float getValue() {
		return value;
	}

}
