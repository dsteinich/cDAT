package gov.cida.cdat.control;


/**
 * The callback for the message return onComplete. The motivation for this is to change
 * the AKKA Object response message to the CDAT Message class
 * 
 * This has to be an abstract class because the super class is not an interface
 * 
 * @author duselman
 *
 */
public abstract class Callback {

	/**
	 * Called after a worker is known to be completed.
	 * Casts the AKKA OnComplete from Object to Message response
	 * 
	 * TODO Implement the Delegate and Worker actors if they can be used for a better OnComplete determination
	 * TODO Implement other callback method types. AKKA has others like OnError and OnSuccess (I think)
	 */
	abstract public void onComplete(Throwable t, Message response);

}
