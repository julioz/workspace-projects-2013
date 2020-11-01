package br.com.zynger.libertadores.asynctasks;

public interface AsyncTaskListener {
	
	public void preExecution();
	public void onComplete(Object result);
	public void onFail(String message);

}
