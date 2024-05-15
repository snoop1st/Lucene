package txtparsing;

/**
 *
 * @author Tonia Kyriakopoulou
 */
public class MyDoc {

    private String title;
    private String mesh;

    public MyDoc(String title,  String mesh) {
        this.title = title;
        this.mesh = mesh;
    }

    @Override
    public String toString() {
        String ret = "MyDoc{"
                + "\n\tTitle: " + title
                + "\n\tMesh: " + mesh;                
        return ret + "\n}";
    }

    //---- Getters & Setters definition ----
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getMesh() {
        return mesh;
    }

    public void setMesh(String mesh) {
        this.mesh = mesh;
    }
}
