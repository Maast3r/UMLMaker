package lab2one;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import edu.roshulman.csse374.editor.TextEditor;

public class TextEditorApp {
	public static void main(String[] args) throws Exception {
		InputStream in = new FileInputStream("./input_output/in.txt");
		OutputStream out = new Encrypt(new FileOutputStream("./input_output/out.txt"));
		
		TextEditor editor = new TextEditor(in, out);
		editor.execute();
	}	
}
