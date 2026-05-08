package advancedsoftware.models;

public class Department {
    private int departmentId;
    private String deptName;
    private String description;
    private int managerId;

    public Department(int departmentId, String deptName, String description, int managerId) {
        this.departmentId = departmentId;
        this.deptName = deptName;
        this.description = description;
        this.managerId = managerId;
    }

}