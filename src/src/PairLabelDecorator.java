package src;

public class PairLabelDecorator extends AbstractPairDecorator {
	public String pair;
	
	public PairLabelDecorator(String pair) {
		super(pair);
		this.pair = pair;
	}

	@Override
	public String decorate(String s) {
		if(s.contains(";")){
			System.out.println("HELLOE!@?!?");
			s += ",label=\"\\<\\<Adapts\\>\\>\"";
			s = s.replace(";", " ");
		}
		if(s.contains("+")){
			s += ",label=\"\\<\\<Decorates\\>\\>\"";
			s = s.replace("+", " ");
		}	
		return s;
	}
}
