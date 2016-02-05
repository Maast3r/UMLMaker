package src;

import java.util.HashSet;

public class CompositeDetector extends AbstractDetector{

	public CompositeDetector(NoahsArk ark) {
		super(ark);
	}

	@Override
	public HashSet<String> getType(String cName) {
		for(ClassPrototype c : super.ark.getBoat().values()){
//			c = super.ark.getBoat().get(cName);
			// look at super, check assos arrow
			// if this has list of components, super is the component, this is the composite
			// all other classes that extend the component is a leaf (if it has no subclasses)
			String thisSuperName = c.getSuperName();
			if(thisSuperName != null){
				if(ark.pairs.containsKey(c.name)){
					if(ark.pairs.get(c.name).contains("$" + thisSuperName)){
						for(FieldPrototype f : c.fields.values()){
							if(f.type.equals("List") && f.sig.equals(thisSuperName)){
								c.type.add("composite");
								ark.getBoat().get(thisSuperName).type.add("composite component");
								for (ClassPrototype cp : super.ark.getBoat().values()) {
									if(cp.superName.equals(thisSuperName) && !cp.name.equals(c.name)) cp.type.add("leaf");
								}
							}
						}
							
					}
				}
			}
			
			for(String iface : c.getInterfaces()){
				if(iface.contains("/")){
					String[] temp = iface.split("/");
					iface = temp[temp.length-1];
				}
				if(ark.pairs.containsKey(c.name)){
					if(ark.pairs.get(c.name).contains("$" + iface)){
						for(FieldPrototype f : c.fields.values()){
							if(f.type.equals("List") && f.sig.equals(iface)){
								c.type.add("composite");
								ark.getBoat().get(iface).type.add("composite component");
								for (ClassPrototype cp : super.ark.getBoat().values()) {
									for(String i : cp.getInterfaces()){
										if(i.contains("/")){
											String[] temp = i.split("/");
											i = temp[temp.length-1];
										}
										if(i.equals(iface)){
											if(!cp.name.equals(c.name))cp.type.add("leaf");
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
//		for(ClassPrototype cl : this.ark.getBoat().values()){
//			String className = cl.getName();
//			String superName = cl.getSuperName();
//		}
//		
		ClassPrototype cl = super.ark.getBoat().get(cName);
		return cl.type;
	}

}
