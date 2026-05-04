import java.util.ArrayList;
import java.util.List;

// Common interface for both Files and Folders
interface AbstractFile {
    int getFileSize();
    String getName();
}

// Leaf: Represents a single file
class File implements AbstractFile {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public int getFileSize() {
        return size;
    }

    @Override
    public String getName() {
        return name;
    }
}

// Composite: Represents a folder that can contain files or other folders
class Folder implements AbstractFile {
    private String name;
    private List<AbstractFile> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(AbstractFile file) {
        children.add(file);
    }

    @Override
    public int getFileSize() {
        int totalSize = 0;
        for (AbstractFile child : children) {
            totalSize += child.getFileSize(); // Recursive summation
        }
        return totalSize;
    }

    @Override
    public String getName() {
        return name;
    }
}

// Client
class FileManager {
    public void printFileSize(AbstractFile f) {
        System.out.println("File/Folder: " + f.getName() + ", Total Size: " + f.getFileSize());
    }

    public static void main(String[] args) {
        FileManager manager = new FileManager();
        
        Folder root = new Folder("Root");
        root.add(new File("file1.txt", 100));
        root.add(new File("file2.txt", 200));
        
        Folder sub = new Folder("SubFolder");
        sub.add(new File("file3.txt", 50));
        
        root.add(sub);
        
        manager.printFileSize(root);
    }
}
