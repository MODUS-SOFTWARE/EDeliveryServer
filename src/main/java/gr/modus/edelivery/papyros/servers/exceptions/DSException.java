package gr.modus.edelivery.papyros.servers.exceptions;

import JavaPapyrusR6ServerApi.DataTypes.WMTErrRetType;

public class DSException extends Exception{

	private WMTErrRetType error ;
	private String message = "";
	
	public WMTErrRetType getError() {
		return error;
	}

	public void setError(WMTErrRetType error) {
		this.error = error;
	}

	public DSException(WMTErrRetType error) {
        super();
		this.error = error;
    }
	
	public DSException(String message) {
        super();
		this.message = message;
    }
	
	public String getMessage(){
		if(error!=null && error.getErrorMessage()!=null){
			return error.getErrorMessage();
		}
		else if(this.message!=null){
			return message;
		}
		else{
			return "Null error";
		}
	}
}
