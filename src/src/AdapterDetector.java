package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AdapterDetector extends AbstractDetector{
	public NoahsArk ark;
	
	public AdapterDetector(NoahsArk ark){
		super(ark);
		this.ark = ark;
	}
	
	@Override
	public HashSet<String> getType(String cName) {
		ArrayList<String> classNames = new ArrayList<String>();
		ArrayList<String> targetNames = new ArrayList<String>();
		
		for(ClassPrototype cl : this.ark.getBoat().values()){
			String className = cl.getName();
			String superName = cl.getSuperName();
			String pkg = cl.pkg;
			String[] interfaces = cl.getInterfaces();
			if(ark.pairs.get(className) != null){
				for (String target : ark.pairs.get(className)) {
					String targetName = target.substring(1);
					if(target.charAt(0) == '$' ){
						
						
						if (!superName.equals("") && superName != null) {
							if(ark.getBoat().containsKey(superName)){
								if(!ark.getBoat().get(superName).pkg.equals("java.lang.")){
									ClassPrototype sup = ark.getBoat().get(superName);
									for(FieldPrototype f : sup.fields.values()){
										if(!cl.fields.containsKey(f.name)){
											cl.addField(f.name, f);
										}
									}
									for (FieldPrototype f : cl.fields.values()) {
										if (f.type.equals(targetName) && !f.type.equals(superName) && !superName.equals(targetName) && !ark.getBoat().get(targetName).isInterface) {
											cl.type.add("adapter");
											
											// target
											if(ark.getBoat().containsKey(superName))ark.getBoat().get(superName).type.add("target");
											// adaptee
											ark.getBoat().get(targetName).type.add("adaptee");
											classNames.add(className);
											targetNames.add(targetName);
											break;
										}
									}
									
								}
							}
						}
						
						for(String intfc : interfaces){
							if(intfc.contains("/")) intfc = intfc.split("/")[intfc.split("/").length-1];
							for(FieldPrototype f : cl.fields.values()){
								if(f.type.equals(targetName) && checkMethods(cl, ark.getBoat().get(intfc)) && ark.getBoat().get(targetName).isInterface){
									cl.type.add("adapter");
									// target
									if(ark.getBoat().containsKey(intfc))ark.getBoat().get(intfc).type.add("target");
									// adaptee
									ark.getBoat().get(targetName).type.add("adaptee");
									classNames.add(className);
									targetNames.add(targetName);
									break;
								}
							}
						}
					}
				}
			}
		}
		
		for(int i=0; i<classNames.size(); i++){
			ark.pairs.get(classNames.get(i)).remove("$" + targetNames.get(i));
			ark.pairs.get(classNames.get(i)).add("$" + targetNames.get(i) + ";");
		}
		
		ClassPrototype c = this.ark.getBoat().get(cName);
		return c.type;
	}
	
	public boolean checkMethods(ClassPrototype c, ClassPrototype intfc){
		if((c == null) || (intfc == null)) return false;
		Set<String> intfcMethods = intfc.getMethods().keySet();
		ArrayList<Boolean> hasMethod = new ArrayList<Boolean>();
		for(String iMethods : intfcMethods){
			for(String methods : c.getMethods().keySet()){
				if(methods.equals(iMethods)) hasMethod.add(true);
			}
		}
		return hasMethod.size() == intfcMethods.size();
		
	}
}
