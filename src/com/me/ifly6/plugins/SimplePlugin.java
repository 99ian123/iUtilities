package com.me.ifly6.plugins;

public class SimplePlugin extends com.me.ifly6.ConInfClass {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void plugin(String[] args){
		if (operand[0].equals("/api")){
			if (operand[1].equals("SimplePlugin")){
				append("Hello World. SimplePlugin Reporting.");
				log("SimplePlugin Ran.");
				for (int x = 0; x<10; x++){
					append(x);
					try {
						Thread.sleep(60);
					} catch (InterruptedException e) { log("Plugin SimplePlugin Failed: InterruptedException"); }
				}
			}
		}
	}
}