package gov.cida.cdat.control;

import gov.cida.cdat.io.QuietClose;
import gov.cida.cdat.service.Service;


/**
 * This hides the AKKA library calls.
 * This delegate simple exists because eclipse will detect the underlying implementation.
 * 
 * @author duselmann
 *
 */
public class SCManager implements QuietClose {
	public final static String SESSION = Service.SESSION;

	private static final Service   scm;
	private static final SCManager instance;
	
	static {
		scm = Service.instance();
		instance = new SCManager();
	}
	
	
	public static SCManager open() {
		return open("");
	}
	public static SCManager open(String adminToken) {
	    Service.open(adminToken);
		return instance;
	}
	public static SCManager instance() {
		return open();
	}
	
	public int hashCode() {
		return scm.hashCode();
	}

	public void close() {
		scm.close();
	}

	public void close(boolean force) {
		scm.close(force);
	}

	public boolean equals(Object obj) {
		return scm.equals(obj);
	}

	public String addWorker(String workerLabel, Worker worker) {
		return scm.addWorker(workerLabel, worker);
	}

	public String toString() {
		return scm.toString();
	}

	public void addWorker(String workerLabel, Worker worker, Callback onComplete) {
		scm.addWorker(workerLabel, worker, onComplete);
	}

	public void send(String workerName, Message message) {
		scm.send(workerName, message);
	}

	public void send(String workerName, Control ctrl) {
		scm.send(workerName, ctrl);
	}

	public Message request(String workerName, Message msg) {
		return scm.request(workerName, msg);
	}

	public Message request(String workerName, Message msg, Time maxWait) {
		return scm.request(workerName, msg, maxWait);
	}
	
	public Message request(String workerName, Message msg, Time maxWait, Callback callback) {
		return scm.request(workerName, msg, maxWait, callback);
	}

	public void send(String workerName, Control ctrl, Callback callback) {
		scm.send(workerName, ctrl, callback);
	}
	public void send(String workerName, Control ctrl, Time waitTime, Callback callback) {
		scm.send(workerName, ctrl, waitTime, callback);
	}

	public Message send(String workerName, Status status) {
		return scm.send(workerName, status);
	}

	public void send(String workerName, Status status, Callback callback) {
		scm.send(workerName, status, callback);
	}

	public void send(String workerName, Message msg, Time waitTime, Callback callback) {
		scm.send(workerName, msg, waitTime, callback);
	}

	public SCManager setAutoStart(boolean value) {
		scm.setAutoStart(value);
		return this;
	}

	public long waitForComplete(String workerName, long waitTime) {
		return scm.waitForComplete(workerName, waitTime);
	}

	public long waitForComplete(String workerName, long initialWait, long subsequentWait) {
		return scm.waitForComplete(workerName, initialWait, subsequentWait);
	}

	public Message fetchSessionInfo() {
		return scm.fetchSessionInfo();
	}
}
