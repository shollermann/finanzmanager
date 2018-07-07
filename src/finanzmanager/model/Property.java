package finanzmanager.model;

public class Property {

    int width;
    int height;
    String title;
    String viewPath;

    String loggingFilePath;

    public Property(String viewPath) {
        this.viewPath = viewPath;
    }

    public void setLoggingFilePath(String loggingFilePath) {
        this.loggingFilePath = loggingFilePath;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewPath() {
        return viewPath;
    }

    public String getLoggingFilePath() {
        return loggingFilePath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }
}
