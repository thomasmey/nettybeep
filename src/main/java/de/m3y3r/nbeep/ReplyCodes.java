package de.m3y3r.nbeep;

public enum ReplyCodes {
	R200(200, "success"),
	R421(421, "service not available"),
	R450(450, "requested action not taken"), // e.g., lock already in use
	;
	//	   451     requested action aborted
	//	           (e.g., local error in processing)
	//	   454     temporary authentication failure
	//	   500     general syntax error
	//	           (e.g., poorly-formed XML)
	//	   501     syntax error in parameters
	//	           (e.g., non-valid XML)
	//	   504     parameter not implemented
	//	   530     authentication required
	//	   534     authentication mechanism insufficient
	//	           (e.g., too weak, sequence exhausted, etc.)
	//	   535     authentication failure
	//	   537     action not authorized for user
	//	   538     authentication mechanism requires encryption
	//	   550     requested action not taken
	//	           (e.g., no requested profiles are acceptable)
	//	   553     parameter invalid
	//	   554     transaction failed
	//	           (e.g., policy violation)

	ReplyCodes(int code, String meaning) {
	}
}
