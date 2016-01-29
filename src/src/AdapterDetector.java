package src;

public class AdapterDetector extends AbstractDetector{
	public NoahsArk ark;
	
	public AdapterDetector(NoahsArk ark){
		super(ark);
		this.ark = ark;
	}
	
	@Override
	public String getType(String cName) {
		for(ClassPrototype cl : this.ark.getBoat().values()){
			String className = cl.getName();
			String[] interfaces = cl.getInterfaces();
			if(ark.pairs.get(className) != null){
				for (String target : ark.pairs.get(className)) {
					String targetName = target.substring(1);
					if(target.charAt(0) == '#' ){
						for(String intfc : interfaces){
							if(intfc.contains("/")) intfc = intfc.split("/")[1];
							for(FieldPrototype f : cl.fields.values()){
								if(f.type.equals(targetName)){
									cl.type = "adapter";
									cl.arrowDesc = ",label=\"\\<\\<Adapts\\>\\>\"";
									// target
									if(ark.getBoat().containsKey(intfc))ark.getBoat().get(intfc).type = "target";
									// adaptee
									ark.getBoat().get(targetName).type = "adaptee";
								}
							}
						}
					}
				}
			}
		}
		ClassPrototype c = this.ark.getBoat().get(cName);
		return c.type;
	}
}
