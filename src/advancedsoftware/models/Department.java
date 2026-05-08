package advancedsoftware.models;

public class Department {
    public int department_id;
    public String dept_name;
    public String description;
    public int manager_id;

    public Department(int id, String name, String desc, int manager) {
        this.department_id = id;
        this.dept_name = name;
        this.description = desc;
        this.manager_id = manager;
    }
}
