/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package advancedsoftware;

import java.sql.*;
import java.sql.DriverManager;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bola-fayz
 */
public class Employees extends javax.swing.JPanel {

    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet result = null;
    String url = "jdbc:sqlserver://localhost:1433;databaseName=SoftCoreDB;encrypt=true;trustServerCertificate=true";

    public Employees() {
        initComponents();
        try {

            con = DriverManager.getConnection(url, "1234", "1234");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        if (con == null) {
            return;
        }
        loadDepartments();
         loadEmployees();

        ((DefaultTableModel) Table.getModel()).setRowCount(0);
        try {
            String sql
                    = "SELECT e.employee_id, e.full_name, d.dept_name, e.job_title, "
                    + "CONVERT(VARCHAR, e.hire_date, 23) AS hire_date, e.salary "
                    + "FROM EMPLOYEES e "
                    + "JOIN DEPARTMENTS d ON e.department_id = d.department_id "
                    + "ORDER BY e.employee_id";
            pst = con.prepareStatement(sql);
            result = pst.executeQuery();
            while (result.next()) {
                ((DefaultTableModel) Table.getModel()).addRow(new Object[]{
                    result.getInt("employee_id"),
                    result.getString("full_name"),
                    result.getString("dept_name"),
                    result.getString("job_title"),
                    result.getString("hire_date"),
                    String.format("%,.0f", result.getDouble("salary"))
                });
            }
        } catch (SQLException e) {
            System.out.println("خطأ في تحميل الموظفين: " + e.getMessage());
        }
    }

    private void loadDepartments() {
        jComboBox2.removeAllItems();
        jComboBox2.addItem("All Departments");
        if (con == null) {
            return;
        }
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT dept_name FROM DEPARTMENTS ORDER BY dept_name");
            while (rs.next()) {
                jComboBox2.addItem(rs.getString("dept_name"));
            }
        } catch (SQLException e) {
            System.out.println("خطأ: " + e.getMessage());
        }
    }
    private void loadEmployees() {
    ((DefaultTableModel) Table.getModel()).setRowCount(0);
    if (con == null) return;
    try {
        String sql =
            "SELECT e.employee_id, e.full_name, d.dept_name, e.job_title, " +
            "CONVERT(VARCHAR, e.hire_date, 23) AS hire_date, e.salary " +
            "FROM EMPLOYEES e " +
            "JOIN DEPARTMENTS d ON e.department_id = d.department_id " +
            "ORDER BY e.employee_id";
        pst = con.prepareStatement(sql);
        result = pst.executeQuery();
        while (result.next()) {
            ((DefaultTableModel) Table.getModel()).addRow(new Object[]{
                result.getInt("employee_id"),
                result.getString("full_name"),
                result.getString("dept_name"),
                result.getString("job_title"),
                result.getString("hire_date"),
                String.format("%,.0f", result.getDouble("salary"))
            });
        }
    } catch (SQLException e) {
        System.out.println("خطأ في تحميل الموظفين: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        fullNameSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fullName = new javax.swing.JTextField();
        phone = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jopTitleCheckBox = new javax.swing.JComboBox<>();
        departmentCheckBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        salary = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        saveBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(375, 2980));

        jPanel2.setBackground(new java.awt.Color(236, 233, 216));
        jPanel2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setText("Search:");

        fullNameSearch.setForeground(new java.awt.Color(153, 153, 153));
        fullNameSearch.setText("Search by name...");
        fullNameSearch.addActionListener(this::fullNameSearchActionPerformed);

        jLabel1.setText("Department");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("jButton1");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(this::jComboBox2ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fullNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(488, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fullNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Table.setAutoCreateRowSorter(true);
        Table.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "FullName", "Department", "Jop Title", "Hire Date", "Salary (EGP)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Table);
        if (Table.getColumnModel().getColumnCount() > 0) {
            Table.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Full Name *");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Phone");

        email.addActionListener(this::emailActionPerformed);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Email");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Hire Date");

        jopTitleCheckBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select --", "Software", "AI & Data" }));
        jopTitleCheckBox.addActionListener(this::jopTitleCheckBoxActionPerformed);

        departmentCheckBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "AI & Data" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Jop Title *");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Department *");

        salary.addActionListener(this::salaryActionPerformed);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Salary (EGP)");

        saveBtn.setBackground(new java.awt.Color(199, 186, 186));
        saveBtn.setText("Save Changes");
        saveBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        saveBtn.addActionListener(this::saveBtnActionPerformed);

        clearBtn.setBackground(new java.awt.Color(199, 186, 186));
        clearBtn.setForeground(new java.awt.Color(255, 0, 0));
        clearBtn.setText("Clear");
        clearBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        clearBtn.addActionListener(this::clearBtnActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fullName, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(352, 352, 352))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(329, 329, 329))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jopTitleCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(departmentCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(salary, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(0, 0, 0)
                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jopTitleCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(departmentCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fullNameSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullNameSearchActionPerformed
String search = fullNameSearch.getText().trim();
    
    ((DefaultTableModel) Table.getModel()).setRowCount(0);
    try {
        String sql =
            "SELECT e.employee_id, e.full_name, d.dept_name, e.job_title, " +
            "CONVERT(VARCHAR, e.hire_date, 23) AS hire_date, e.salary " +
            "FROM EMPLOYEES e " +
            "JOIN DEPARTMENTS d ON e.department_id = d.department_id " +
            "WHERE e.full_name LIKE ? " +
            "ORDER BY e.employee_id";
        pst = con.prepareStatement(sql);
        pst.setString(1, "%" + search + "%");
        result = pst.executeQuery();
        while (result.next()) {
            ((DefaultTableModel) Table.getModel()).addRow(new Object[]{
                result.getInt("employee_id"),
                result.getString("full_name"),
                result.getString("dept_name"),
                result.getString("job_title"),
                result.getString("hire_date"),
                String.format("%,.0f", result.getDouble("salary"))
            });
        }
    } catch (SQLException e) {
        System.out.println("خطأ في البحث: " + e.getMessage());
    }
    }//GEN-LAST:event_fullNameSearchActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void salaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_salaryActionPerformed

    private void jopTitleCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jopTitleCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jopTitleCheckBoxActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        String selectedDept = (String) jComboBox2.getSelectedItem();
        if (selectedDept == null) {
            return;
        }

        ((DefaultTableModel) Table.getModel()).setRowCount(0);
        try {
            String sql;
            PreparedStatement ps;

            if (selectedDept.equals("All Departments")) {
                // عرض كل الموظفين
                sql = "SELECT e.employee_id, e.full_name, d.dept_name, e.job_title, "
                        + "CONVERT(VARCHAR, e.hire_date, 23) AS hire_date, e.salary "
                        + "FROM EMPLOYEES e "
                        + "JOIN DEPARTMENTS d ON e.department_id = d.department_id "
                        + "ORDER BY e.employee_id";
                ps = con.prepareStatement(sql);
            } else {
                // عرض موظفي القسم المختار بس
                sql = "SELECT e.employee_id, e.full_name, d.dept_name, e.job_title, "
                        + "CONVERT(VARCHAR, e.hire_date, 23) AS hire_date, e.salary "
                        + "FROM EMPLOYEES e "
                        + "JOIN DEPARTMENTS d ON e.department_id = d.department_id "
                        + "WHERE d.dept_name = ? "
                        + "ORDER BY e.employee_id";
                ps = con.prepareStatement(sql);
                ps.setString(1, selectedDept);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) Table.getModel()).addRow(new Object[]{
                    rs.getInt("employee_id"),
                    rs.getString("full_name"),
                    rs.getString("dept_name"),
                    rs.getString("job_title"),
                    rs.getString("hire_date"),
                    String.format("%,.0f", rs.getDouble("salary"))
                });
            }
        } catch (SQLException e) {
            System.out.println("خطأ في الفلتر: " + e.getMessage());
        }

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        fullName.setText("");
        phone.setText("");
        email.setText("");
        
        salary.setText("");
        departmentCheckBox.setSelectedIndex(0);
        jopTitleCheckBox.setSelectedIndex(0);
    }//GEN-LAST:event_clearBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        String fullName = this.fullName.getText();
        String phone = this.phone.getText();
        String email = this.email.getText();
        LocalDate hireDate =  LocalDate.now();
        String department = (String) departmentCheckBox.getSelectedItem();
        String salary = this.salary.getText().trim();
        String jopTitle = (String) jopTitleCheckBox.getSelectedItem();
        
            String url = "jdbc:sqlserver://localhost:1433;databaseName=SoftCoreDB;encrypt=true;trustServerCertificate=true";

        
       

       try {
            con = DriverManager.getConnection(url, "1234", "1234");
    String query = "INSERT INTO EMPLOYEES (full_name, phone, email, hire_date, job_title, salary, department_id) " +
                   "VALUES (?, ?, ?, ?, ?, ?, " +
                   "(SELECT department_id FROM DEPARTMENTS WHERE dept_name = ?))";
    
    pst = con.prepareStatement(query);
    pst.setString(1, fullName);
    pst.setString(2, phone);
    pst.setString(3, email);
    pst.setObject(4, hireDate);
    pst.setString(5, jopTitle);
    pst.setDouble(6, Double.parseDouble(salary));
    pst.setString(7, department);
    
    pst.executeUpdate();
    JOptionPane.showMessageDialog(null, "تم إضافة الموظف ✅");
    loadEmployees();
    
} catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, ex);
}
    }//GEN-LAST:event_saveBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table;
    private javax.swing.JButton clearBtn;
    private javax.swing.JComboBox<String> departmentCheckBox;
    private javax.swing.JTextField email;
    private javax.swing.JTextField fullName;
    private javax.swing.JTextField fullNameSearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jopTitleCheckBox;
    private javax.swing.JTextField phone;
    private javax.swing.JTextField salary;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables
}
