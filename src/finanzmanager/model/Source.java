package finanzmanager.model;

public class Source {

    int id;
    String name;

    public Source(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Source(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
