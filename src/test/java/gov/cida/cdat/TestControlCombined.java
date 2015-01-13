package gov.cida.cdat;


import gov.cida.cdat.control.Callback;
import gov.cida.cdat.control.Control;
import gov.cida.cdat.control.Controller;
import gov.cida.cdat.control.Message;
import gov.cida.cdat.io.stream.PipeStream;
import gov.cida.cdat.io.stream.SimpleStream;
import gov.cida.cdat.io.stream.UrlStream;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URL;


public class TestControlCombined {

	private static ByteArrayOutputStream target;
	private static String serviceName;
	private static Controller controller;
	
	
	public static void main(String[] args) throws Exception {
		controller = Controller.get();

		// consumer
		target = new ByteArrayOutputStream(1024*10);
		SimpleStream<OutputStream>     out = new SimpleStream<OutputStream>(target);
		
		// producer
		URL url = new URL("http://www.google.com");
		UrlStream google = new UrlStream(url);
		
		// pipe
		PipeStream pipe = new PipeStream(google, out);		
		
		serviceName = controller.addService("google", pipe);
		
		controller.send(serviceName, Message.create("Message", "Test"));
		controller.send(serviceName, Control.Start);
		controller.send(serviceName, Control.onComplete, new Callback(){
	        public void onComplete(Throwable t, Message repsonse){
	            report(repsonse);		
	        }
	    });
	}
	
	
	private static void report(final Message repsonse) {
        System.out.println("onComplete Response is " + repsonse);
		
		controller.send(serviceName, Control.Stop, new Callback() {
			public void onComplete(Throwable t, Message repsonse) throws Throwable {
				System.out.println("service shutdown");
				controller.shutdown();
			}
		});
		System.out.println("pipe results");
		System.out.println( "total bytes: " +target.size() );
		System.out.println( new String(target.toByteArray(), 0, 100) );
		
		String msg = "Google Not Found";
		if ( new String(target.toByteArray()).contains("Google") ) {
			msg = "Google Found";
		}
		System.out.println();
		System.out.println(msg);
	}

}
