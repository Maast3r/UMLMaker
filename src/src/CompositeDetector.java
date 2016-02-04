package src;

import java.util.HashSet;

public class CompositeDetector extends AbstractDetector{

	public CompositeDetector(NoahsArk ark) {
		super(ark);
	}

	@Override
	public HashSet<String> getType(String cName) {
		ClassPrototype c = super.ark.getBoat().get(cName);
		// look at super, check assos arrow
		// if this has list of components, super is the component, this is the composite
		// all other classes that extend the component is a leaf (if it has no subclasses)
		return null;
	}

}
