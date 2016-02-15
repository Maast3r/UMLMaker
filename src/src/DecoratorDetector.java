package src;

import java.util.ArrayList;
import java.util.HashSet;

public class DecoratorDetector extends AbstractDetector {
	public NoahsArk ark;

	public DecoratorDetector(NoahsArk ark) {
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
			String[] interfaces = cl.getInterfaces();
			if (ark.pairs.get(className) != null) {
				for (String target : ark.pairs.get(className)) {
					String targetName = target.substring(1);
					// Detect Decorator Pattern -------------------------
					// check for association arrow
					if (target.charAt(0) == '$') {
						if(targetName.equals(className)){
							if(cl.isAbstract){
								for (FieldPrototype f : cl.fields.values()) {
									if (f.type.equals(className)) {
										cl.type.add("decorator");
										ark.getBoat().get(targetName).type.add("component");
										for (ClassPrototype cp : ark.getBoat().values()) {
											if(cp.superName != null){	
												if (cp.superName.equals(className))
													cp.type.add("decorator");
											}
										}
										classNames.add(className);
										targetNames.add(targetName);
									}
								}
							}
						}
						
						// check if the target is a super
						// check if it has a subclass
						if (targetName.equals(superName)) {
							for (FieldPrototype f : cl.fields.values()) {
								if (f.type.equals(superName)) {
									cl.type.add("decorator");
									ark.getBoat().get(targetName).type.add("component");
									for (ClassPrototype cp : ark.getBoat().values()) {
										if(cp.superName != null){	
											if (cp.superName.equals(className))
												cp.type.add("decorator");
										}
									}
									classNames.add(className);
									targetNames.add(targetName);
								}
							}
						}
						
						for(String intfc : interfaces){
							if(intfc.contains("/")) intfc = intfc.split("/")[intfc.split("/").length-1];
							for(FieldPrototype f : cl.fields.values()){
								if(f.type.equals(intfc)){
									cl.type.add("decorator");
									if(ark.getBoat().containsKey(intfc))ark.getBoat().get(intfc).type.add("component");
									for (ClassPrototype cp : ark.getBoat().values()) {
										if(cp.superName != null){	
											if (cp.superName.equals(className))
												cp.type.add("decorator");
										}
									}
									classNames.add(className);
									targetNames.add(targetName);
								}
							}
						}
					}
				}
			}
		}
		
		
		for(int i=0; i<classNames.size(); i++){
			ark.pairs.get(classNames.get(i)).remove("$" + targetNames.get(i));
			ark.pairs.get(classNames.get(i)).add("$" + targetNames.get(i) + "+");
		}
		
		
		ClassPrototype c = this.ark.getBoat().get(cName);
		return c.type;
	}
	
	
}
