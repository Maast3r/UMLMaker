package src;

public class InterfaceTitleDecorator extends AbstractTitleDecorator {
	public ClassPrototype c;
	
	public InterfaceTitleDecorator(ClassPrototype c) {
		super(c);
		this.c = c;
	}

	@Override
	public String decorateTitle() {
		if(c.isInterface) return c.preName += "\\<\\<Interface\\>\\>\\n";
		return c.preName;
	}

}
