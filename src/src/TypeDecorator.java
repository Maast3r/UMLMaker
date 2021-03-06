package src;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;

public abstract class TypeDecorator extends TypeDetector{
	protected HashMap<String, String> typeToColor = new HashMap<String, String>();
	protected HashMap<String, String> typeToFill = new HashMap<String, String>();
	protected HashMap<String, String> typeToName = new HashMap<String, String>();
	private TypeDetector typeDetector;
	
	public HashMap<String, String> getTypeToColor() {
		return typeToColor;
	}

	public void setTypeToColor(HashMap<String, String> typeToColor) {
		this.typeToColor = typeToColor;
	}

	public HashMap<String, String> getTypeToFill() {
		return typeToFill;
	}

	public void setTypeToFill(HashMap<String, String> typeToFill) {
		this.typeToFill = typeToFill;
	}

	public HashMap<String, String> getTypeToName() {
		return typeToName;
	}

	public void setTypeToName(HashMap<String, String> typeToName) {
		this.typeToName = typeToName;
	}

	public TypeDetector getTypeDetector() {
		return typeDetector;
	}

	public void setTypeDetector(TypeDetector typeDetector) {
		this.typeDetector = typeDetector;
	}

	public TypeDecorator(TypeDetector t){
		super(t.cName, t.ark, t.args);
		this.typeDetector = t;
		this.typeToColor.put("", "black");
		this.typeToColor.put("singleton", "blue");
		
		this.typeToFill.put("", "white");
		this.typeToFill.put("singleton", "white");
		this.typeToFill.put("decorator", "chartreuse2");
		this.typeToFill.put("component", "chartreuse2");
		this.typeToFill.put("adapter", "firebrick1");
		this.typeToFill.put("adaptee", "firebrick1");
		this.typeToFill.put("target", "firebrick1");
		this.typeToFill.put("composite component", "yellow");
		this.typeToFill.put("composite", "yellow");
		this.typeToFill.put("leaf", "yellow");
		
		this.typeToName.put("", "");
		this.typeToName.put("singleton", "\\n\\<\\<Singleton\\>\\>");
		this.typeToName.put("decorator", "\\n\\<\\<decorator\\>\\>");
		this.typeToName.put("component", "\\n\\<\\<decorator component\\>\\>");
		this.typeToName.put("adapter", "\\n\\<\\<adapter\\>\\>");
		this.typeToName.put("adaptee", "\\n\\<\\<adaptee\\>\\>");
		this.typeToName.put("target", "\\n\\<\\<target\\>\\>");
		this.typeToName.put("composite component",  "\\n\\<\\<composite component\\>\\>");
		this.typeToName.put("composite", "\\n\\<\\<composite\\>\\>");
		this.typeToName.put("leaf", "\\n\\<\\<leaf\\>\\>");
	}
	
	public String getColor() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
									InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(this.typeDetector.getType().contains("singleton")){
//			return "style = filled \n color = " + this.typeToColor.get("singleton") + "\n";
			return this.typeToColor.get("singleton")+"\n";
		}
//		return "color = black\n";
		return "black\n";
	}
	
	public String getFillColor() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
										InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(this.typeDetector.getType().size() > 0){
			HashSet<String> colors = new HashSet<String>();
			for(String color : this.typeDetector.getType()){
				colors.add(this.typeToFill.get(color));
			}
			String totalColor = "white";
			if(colors.contains("yellow")){
				totalColor = "yellow";
				if(colors.contains("firebrick1")){
					if(colors.contains("chartreuse2")){
						totalColor = "darkgoldenrod";
					} else{
						totalColor = "orange";
					}
				}
			} else{
				if(colors.contains("firebrick1")){
					totalColor = "firebrick1";
					if(colors.contains("chartreuse2")){
						totalColor = "yellow3";
					}
				}else {
					if(colors.contains("chartreuse2")){
						totalColor = "chartreuse2";
					}
				}
			}
//			return "style = filled \n fillcolor = " + totalColor + "\n";
			return totalColor+"\n";
		}
//		return "style = filled \n fillcolor = white\n";
		return "white\n";
	}
	
	public void addColor(String type, String color){
		this.typeToColor.put(type, color+"\n");
	}
	
	public void addFill(String type, String color){
		this.typeToFill.put(type, color+"\n");
	}
	
	public void addTypeToName(String type, String name){
		this.typeToName.put(type, "\\n\\<\\<"+name+"\\>\\>");
	}
	
	public HashSet<String> getType() throws NoSuchMethodException, SecurityException, ClassNotFoundException,
											InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HashSet<String> result = new HashSet<String>();
		for(String s : this.typeDetector.getType()){
			result.add(this.typeToName.get(s));
			
		}
		return result;
	}
	
	 
}
