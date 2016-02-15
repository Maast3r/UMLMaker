package src;

public class AbstrTitleDecorator extends AbstractTitleDecorator {
	ClassPrototype c;
	
	public AbstrTitleDecorator(ClassPrototype c){
		super(c);
		this.c = c;
	}
	
	@Override
	public String decorateTitle() {
		if(c.isAbstract) return c.preName += "\\<\\<Abstract\\>\\>\\n";
		else return c.preName;
	}

}
