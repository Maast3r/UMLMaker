package src;

public class DecoratorDetector extends AbstractDetector {
	public NoahsArk ark;

	public DecoratorDetector(NoahsArk ark) {
		super(ark);
		this.ark = ark;
	}

	@Override
	public String getType(String cName) {
		for(ClassPrototype cl : this.ark.getBoat().values()){
			String className = cl.getName();
			String superName = cl.getSuperName();
			String[] interfaces = cl.getInterfaces();
			if (ark.pairs.get(className) != null) {
				for (String target : ark.pairs.get(className)) {
					String targetName = target.substring(1);
					// Detect Decorator Pattern -------------------------
					// check for association arrow
					if (target.charAt(0) == '$') {
						// check if the target is a super
						// check if it has a subclass
						if (targetName.equals(superName)) {
							for (FieldPrototype f : cl.fields.values()) {
								if (f.type.equals(superName)) {
									cl.type = "decorator";
									cl.arrowDesc = ",label=\"\\<\\<Decorates\\>\\>\"";
									ark.getBoat().get(targetName).type = "component";
									for (ClassPrototype cp : ark.getBoat().values()) {
										if(cp.superName != null){	
											if (cp.superName.equals(className))
												cp.type = "decorator";
										}
									}
								}
							}
						}
					}
				}
			}
		}
		ClassPrototype c = this.ark.getBoat().get(cName);
		if (!c.type.equals("")){
			return c.type;
		}
		return "";
	}
}
