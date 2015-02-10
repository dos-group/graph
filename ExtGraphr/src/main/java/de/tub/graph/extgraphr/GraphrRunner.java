package de.tub.graph.extgraphr;

import java.util.List;

import net.sf.teeser.Individual;
import net.sf.teeser.TeeserException;
import net.sf.teeser.TeeserUnsupported;
import net.sf.teeser.simulator.Simulator;

import org.apache.log4j.Logger;

/**
 * Plugin for the Graphr simulator to be run in the Teeser as a Simulator component.
 * This plugin performs single simulation, i.e. does not support "batch" mode.
 * simulatorConfig property contains list of arguments separated by semicolumn. For exmaple "-s;scenario.xml;-w;world.xml" 
 * @author Dalimir Orfanus
 *
 */
public class GraphrRunner extends Simulator {
	private static final long serialVersionUID = 6458424608702777490L;
	
	private static final Logger log = Logger.getLogger(GraphrRunner.class);
	public static final String CONFIG_ARG_DELIMITER = ";";
		
	//-----------------------	

	/**
	 * Main function used only for test and debug purposes. 
	 * @param args
	 * @throws TeeserException 
	 */
	public static void main(String args[]) throws TeeserException {
		GraphrRunner sr = new GraphrRunner();
		String config = "-s" + CONFIG_ARG_DELIMITER + "scenario.xml" + CONFIG_ARG_DELIMITER
				+ "-w" + CONFIG_ARG_DELIMITER + "world.xml"; 
		sr.setSimulatorConfig(config);
		
		sr.initSimulator();
		
		sr.run();
	}
	
	/**
	 * Empty constructor, intentionally left empty 
	 */
	public GraphrRunner() { 
		super();
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void initSimulator() throws TeeserException {
		GraphrMacroProbe.getInstance().setAnalyzer(analyzer);
		log.trace("Simulator initialized");
	}

	/** {@inheritDoc} */
	@Override
	public void initModel(Individual individual) throws TeeserException {
		GraphrMacroProbe.getInstance().setModelParameters(individual);
		log.trace("Model initialized");
	}

	/** {@inheritDoc} */
	@Override
	public void initModel(List<Individual> individuals) throws TeeserException {
		log.error("Init of models with the batch of individuals is unsupported");
		throw new TeeserUnsupported("Unsupported model setup with this component!");
	}

	/** {@inheritDoc} */
	@Override
	public void run() throws TeeserException {
		String[] args = null;		
		if(simulatorConfig != null) {
			String configFile = (String)simulatorConfig;
			// split configuration string into array of parameters			
			args = configFile.split(CONFIG_ARG_DELIMITER);
		}
		
		// launch simulator
		// TODO: launch simulator Simbeeotic.main(args);
		
	}

	// ---- GETTERS / SETTERS -----------
	

}
