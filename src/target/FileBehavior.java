package target;

import java.io.File;

public interface FileBehavior extends Observer{
	public void handleDirectoryEvent(String filename);
}
