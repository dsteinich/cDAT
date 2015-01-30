package gov.cida.cdat.service;

import gov.cida.cdat.exception.CdatException;
import gov.cida.cdat.io.stream.DataPipe;

public class PipeWorker extends Worker {

	private final DataPipe pipe;
	
	public PipeWorker(DataPipe pipe) {
		this.pipe = pipe;
	}
	
	@Override
	public void begin() throws CdatException {
		super.begin();
		pipe.open();
	}
	
	@Override
	public boolean process() throws CdatException {
		super.process();
		boolean isMore = pipe.process(500); // asdf TODO this need to be a real amount of time
		return isMore;
	}
	
	@Override
	public void end() {
		try {
			pipe.close();
		} finally {
			super.end();
		}
	}
	
}
