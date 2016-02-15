package src;

public abstract class AbstractTitleDecorator {
	public ClassPrototype c;
	
	public AbstractTitleDecorator(ClassPrototype c){
		this.c = c;
	}
	
	public abstract String decorateTitle();
}
