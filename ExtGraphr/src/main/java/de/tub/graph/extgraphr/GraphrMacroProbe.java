package de.tub.graph.extgraphr;

import java.util.ArrayList;
import java.util.List;

import net.sf.teeser.DimensionDescriptor;
import net.sf.teeser.Individual;
import net.sf.teeser.ProblemDescriptor;
import net.sf.teeser.analyzer.IMacroVarCallBack;
import net.sf.teeser.macroprobe.IModelSetup;
import net.sf.teeser.macroprobe.MacroValue;

/**
 * Macroscopic probe to provide "plug" for the Graphr simulator towards Teeser. This is the 
 * container class which contains all model parameters as wished by the Teeser 
 * (regardless the means how they were transported / acquired) and provides
 * methods to send the simulation results (macroscopic data) back to the Teeser
 * and to its analyzer(s).
 * @author Dalimir Orfanus
 *
 */
public class GraphrMacroProbe implements IModelSetup {
	static GraphrMacroProbe instance;
	Individual individual = null;
	IMacroVarCallBack analyzer = null;
	
	List<MacroValue> mvs;
	
	private GraphrMacroProbe() {
		// intentionally left empty
	};
	
	public static GraphrMacroProbe getInstance() {
		if(instance == null) 
			instance = new GraphrMacroProbe();
		
		return instance;
	}
		
	/**
	 * Returns current value of the model parameter of the given index
	 * @param dimIndex
	 * @return
	 */
	public double getParameter(int dimIndex) {
		if(individual == null) {
			throw new IllegalArgumentException("Cannot get parameter, individual has not been set yet");
		}

		if(dimIndex < 0 || dimIndex >= individual.getProblemSize()) {
			throw new IllegalArgumentException("Specified dimension index (" + 
						dimIndex + ") is out of range (0, " + individual.getProblemSize() +")");
		}
		
		return individual.getPosition(dimIndex);
	}
	
	/**
	 * Returns all parameters' values in an array
	 * @return
	 */
	public double[] getParametersAll() {
		if(individual == null) {
			throw new IllegalArgumentException("Individual has not been set yet");
		}

		return individual.getPosition();
	}
	
	/**
	 * Gets problem descriptor of the current session
	 * @return
	 */
	public ProblemDescriptor getProblemDescriptor() {
		if(individual == null) {
			throw new IllegalArgumentException("Cannot get problem descriptor, individual has not been set yet");
		}

		return individual.getProblemDescriptor();
	}
	
	/**
	 * Returns required dimension descriptor 
	 * @param dimIndex
	 * @return
	 */
	public DimensionDescriptor getDimensionDescriptor(int dimIndex) {
		if(individual == null) {
			throw new IllegalArgumentException("Cannot get dimension descriptor, individual has not been set yet");
		}
		
		if(dimIndex < 0 || dimIndex >= individual.getProblemSize()) {
			throw new IllegalArgumentException("Specified dimension index (" + 
						dimIndex + ") is out of range (0, " + individual.getProblemSize() +")");
		}
		
		return individual.getProblemDescriptor().getDescriptor(dimIndex);
	}

	/**
	 * Sends back to the Teeser observed macroscopic values which were previously
	 * collected by addResult method.
	 * @param macroId ID of this macro value, so can be matched with the macro variable
	 * @param time Simulation time when observed 
	 * @param value Values of the macroscopic variable
	 */
	public void sendResult() {
		analyzer.putMacroInstance(mvs, individual);
		mvs = null;
	}

	/**
	 * Collects observed macroscopic values which will be later (via sendResult method)
	 * sent back to the Teeser.
	 * @param macroId ID of this macro value, so can be matched with the macro variable
	 * @param time Simulation time when observed 
	 * @param value Values of the macroscopic variable
	 */
	public void addResult(int macroId, double time, double value) { 		
		if(individual == null) {
			throw new IllegalArgumentException("Cannot send results, analyzer has not been set yet");
		}
		
		MacroValue mv = new MacroValue(time, value, macroId);

		if(mvs == null) {
			mvs = new ArrayList<MacroValue>();			
		}
		mvs.add(mv);
	}
	
	//-------- GETTERS / SETTERS ----------------------
	
	public void setModelParameters(Individual individual) {
		this.individual = individual;
	}
	
	public Individual getModelParameters() {
		return this.individual;
	}

	public IMacroVarCallBack getAnalyzer() {
		return this.analyzer;
	}

	public void setAnalyzer(IMacroVarCallBack analyzer) {
		this.analyzer = analyzer;
	}

}
