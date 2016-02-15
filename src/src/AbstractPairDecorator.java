package src;

public abstract class AbstractPairDecorator {
	public String pair;
	
	public AbstractPairDecorator(String pair){
		this.pair = pair;
	}
	
	public abstract String decorate(String s);
}
