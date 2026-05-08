package advancedsoftware;

import advancedsoftware.models.Department;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Departments extends javax.swing.JPanel {

    Connection con = MainFrame.con;
    DefaultTableModel model;
    int selectedDeptId = -1;

    public Departments() {
        initComponents();
        fillManagerCombo();
        fetchData();
        // add listenrer => search bar
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterData();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterData();
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterData();
            }

            public void filterData() {
                String query = txtSearch.getText().trim();
                searchInDatabase(query);
            }
        });
        setFormMode("ADD");

    }

    //for table
    public void fetchData() {
        DefaultTableModel model = (DefaultTableModel) tableDepartments.getModel();
        model.setRowCount(0);

        try {
            String query = "SELECT d.*, e.full_name FROM DEPARTMENTS d "
                    + "LEFT JOIN EMPLOYEES e ON d.manager_id = e.employee_id";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Department dept = new Department(
                        rs.getInt("department_id"),
                        rs.getString("dept_name"),
                        rs.getString("description"),
                        rs.getInt("manager_id")
                );

                String managerName = rs.getString("full_name");
                if (managerName == null) {
                    managerName = "No Manager"; 
                }
                model.addRow(new Object[]{
                    rs.getInt("department_id"),
                    rs.getString("dept_name"),
                    rs.getString("description"),
                    managerName
                });
            }
        } catch (Exception e) {
            System.err.println("Error fetching data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtDesc.setText("");
        comboManager.setSelectedIndex(0);
    }

    private void setFormMode(String mode) {
        if (mode.equals("ADD")) {
            btnSave.setVisible(false);
            btnDelete.setVisible(false);
            btnDoInsert.setVisible(true);
            btnClearForm.setVisible(true);
            formPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Add New Department"));
            clearFields();
        } else if (mode.equals("EDIT")) {
            btnSave.setVisible(true);
            btnDelete.setVisible(true);
            btnDoInsert.setVisible(false);
            btnClearForm.setVisible(true);
            formPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Edit Department"));
        }
    }

    public void fillManagerCombo() {
        try {

            String query = "SELECT full_name FROM EMPLOYEES";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            comboManager.removeAllItems();
            comboManager.addItem("-- Select Manager --");

            while (rs.next()) {
                comboManager.addItem(rs.getString("full_name"));
            }
        } catch (Exception e) {
            System.err.println("Error filling combo: " + e.getMessage());
        }
    }

    private void searchInDatabase(String searchText) {
        DefaultTableModel model = (DefaultTableModel) tableDepartments.getModel();
        model.setRowCount(0);

        try {

            String sql = "SELECT d.*, e.full_name FROM DEPARTMENTS d "
                    + "LEFT JOIN EMPLOYEES e ON d.manager_id = e.employee_id "
                    + "WHERE d.dept_name LIKE ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + searchText + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String managerName = rs.getString("full_name");
                if (managerName == null) {
                    managerName = "No Manager";
                }

                model.addRow(new Object[]{
                    rs.getInt("department_id"),
                    rs.getString("dept_name"),
                    rs.getString("description"),
                    managerName
                });
            }
        } catch (Exception e) {
            System.err.println("Search Error: " + e.getMessage());
        }
    }

    private int getManagerIdByName(String name) {
        if (name.equals("-- Select Manager --") || name.equals("No Manager")) {
            return -1;
        }

        try {
            String sql = "SELECT employee_id FROM EMPLOYEES WHERE full_name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("employee_id");
            }
        } catch (Exception e) {
            System.err.println("Error getting manager ID: " + e.getMessage());
        }
        return -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolbarPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnStartAdd = new javax.swing.JButton();
        btnClearSeach = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDepartments = new javax.swing.JTable();
        formPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        comboManager = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnDoInsert = new javax.swing.JButton();
        btnClearForm = new javax.swing.JButton();

        setBackground(new java.awt.Color(240, 240, 240));
        setToolTipText("");

        toolbarPanel.setBackground(new java.awt.Color(245, 245, 245));
        toolbarPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 10, 8, 10));
        toolbarPanel.setEnabled(false);
        toolbarPanel.setFocusCycleRoot(true);
        toolbarPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Search: ");
        toolbarPanel.add(jLabel1);

        txtSearch.setColumns(20);
        txtSearch.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txtSearch.setToolTipText("");
        txtSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        txtSearch.setPreferredSize(new java.awt.Dimension(250, 24));
        txtSearch.addActionListener(this::txtSearchActionPerformed);
        toolbarPanel.add(txtSearch);

        btnStartAdd.setBackground(new java.awt.Color(70, 130, 180));
        btnStartAdd.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        btnStartAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnStartAdd.setText("+ Add New");
        btnStartAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btnStartAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStartAdd.addActionListener(this::btnStartAddActionPerformed);
        toolbarPanel.add(btnStartAdd);

        btnClearSeach.setBackground(new java.awt.Color(150, 150, 150));
        btnClearSeach.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        btnClearSeach.setForeground(new java.awt.Color(255, 255, 255));
        btnClearSeach.setText("Clear");
        btnClearSeach.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btnClearSeach.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClearSeach.addActionListener(this::btnClearSeachActionPerformed);
        toolbarPanel.add(btnClearSeach);

        tablePanel.setLayout(new java.awt.BorderLayout());

        tableDepartments.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        tableDepartments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Department Name", "Description", "Manager"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDepartments.setGridColor(new java.awt.Color(214, 214, 214));
        tableDepartments.setInheritsPopupMenu(true);
        tableDepartments.setIntercellSpacing(new java.awt.Dimension(0, 1));
        tableDepartments.setRowHeight(30);
        tableDepartments.setSelectionBackground(new java.awt.Color(100, 149, 237));
        tableDepartments.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tableDepartments.setShowHorizontalLines(true);
        tableDepartments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDepartmentsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableDepartments);

        formPanel.setBackground(new java.awt.Color(255, 255, 255));
        formPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Department Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 1, 14), new java.awt.Color(0, 51, 153))); // NOI18N
        formPanel.setAutoscrolls(true);

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(60, 60, 60));
        jLabel2.setText("Department Name");

        txtName.setText("  ");
        txtName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtName.addActionListener(this::txtNameActionPerformed);

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(60, 60, 60));
        jLabel3.setText("Manager");

        comboManager.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboManager.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(60, 60, 60));
        jLabel4.setText("Description");

        txtDesc.setColumns(20);
        txtDesc.setRows(5);
        txtDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(txtDesc);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnSave.setBackground(new java.awt.Color(70, 130, 180));
        btnSave.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save Changes");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(this::btnSaveActionPerformed);
        jPanel1.add(btnSave);

        btnDelete.setBackground(new java.awt.Color(200, 60, 60));
        btnDelete.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete Department");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(this::btnDeleteActionPerformed);
        jPanel1.add(btnDelete);

        btnDoInsert.setBackground(new java.awt.Color(70, 130, 180));
        btnDoInsert.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        btnDoInsert.setForeground(new java.awt.Color(255, 255, 255));
        btnDoInsert.setText("Add Department");
        btnDoInsert.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDoInsert.addActionListener(this::btnDoInsertActionPerformed);
        jPanel1.add(btnDoInsert);

        btnClearForm.setBackground(new java.awt.Color(150, 150, 150));
        btnClearForm.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        btnClearForm.setForeground(new java.awt.Color(255, 255, 255));
        btnClearForm.setText("Clear");
        btnClearForm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClearForm.addActionListener(this::btnClearFormActionPerformed);
        jPanel1.add(btnClearForm);

        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(formPanelLayout.createSequentialGroup()
                            .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(29, 29, 29)
                            .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(comboManager, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jLabel4)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 805, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 31, Short.MAX_VALUE))
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbarPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(formPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void btnStartAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartAddActionPerformed
        // TODO add your handling code here:
        tableDepartments.clearSelection();
        selectedDeptId = -1;
        setFormMode("ADD");
    }//GEN-LAST:event_btnStartAddActionPerformed

    private void tableDepartmentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDepartmentsMouseClicked
        // TODO add your handling code here:

        int row = tableDepartments.getSelectedRow();

        if (row != -1) {
            String id = tableDepartments.getValueAt(row, 0).toString();
            String name = tableDepartments.getValueAt(row, 1).toString();
            String desc = tableDepartments.getValueAt(row, 2).toString();
            String manager = tableDepartments.getValueAt(row, 3).toString();

            txtName.setText(name);
            txtDesc.setText(desc);
            comboManager.setSelectedItem(manager);
            selectedDeptId = Integer.parseInt(tableDepartments.getValueAt(row, 0).toString());
            comboManager.setSelectedItem(manager);

            setFormMode("EDIT");
        }
    }//GEN-LAST:event_tableDepartmentsMouseClicked

    private void btnDoInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoInsertActionPerformed
        // TODO add your handling code here:
        // get data from fileds
        String name = txtName.getText().trim();
        String desc = txtDesc.getText().trim();
        String managerName = comboManager.getSelectedItem().toString();

        // validation
        if (name.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Department Name is required!");
            return;
        }

        try {

            int managerId = getManagerIdByName(managerName);

            String sql = "INSERT INTO DEPARTMENTS (dept_name, description, manager_id) VALUES (?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, desc);

            //no manger => null
            if (managerId == -1) {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(3, managerId);
            }

            int result = pstmt.executeUpdate();
            if (result > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "New Department added successfully!");

                fetchData();

                clearFields();
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error adding department: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDoInsertActionPerformed

    private void btnClearFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFormActionPerformed
        // TODO add your handling code here:

        clearFields();

        if (selectedDeptId != -1) {

            formPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Edit Department (Cleared)"));
        } else {
            tableDepartments.clearSelection();
            setFormMode("ADD");
        }
    }//GEN-LAST:event_btnClearFormActionPerformed

    private void btnClearSeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSeachActionPerformed
        // TODO add your handling code here:
        txtSearch.setText("");
    }//GEN-LAST:event_btnClearSeachActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if (selectedDeptId == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a department from the table first!");
            return;
        }

        String newName = txtName.getText().trim();
        String newDesc = txtDesc.getText().trim();
        String managerName = comboManager.getSelectedItem().toString();

        if (newName.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Department Name cannot be empty!");
            return;
        }

        try {
            int managerId = getManagerIdByName(managerName);

            String sql = "UPDATE DEPARTMENTS SET dept_name = ?, description = ?, manager_id = ? "
                    + "WHERE department_id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setString(2, newDesc);

            if (managerId == -1) {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(3, managerId);
            }

            pstmt.setInt(4, selectedDeptId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Department updated successfully!");

                fetchData();

                btnClearFormActionPerformed(null);
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error updating: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if (selectedDeptId == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a department to delete!");
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this department?\nThis action cannot be undone!",
                "Confirm Deletion",
                javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM DEPARTMENTS WHERE department_id = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, selectedDeptId);

                int result = pstmt.executeUpdate();
                if (result > 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Department deleted successfully!");

                    fetchData();
                    btnClearFormActionPerformed(null);
                }
            } catch (java.sql.SQLException e) {
                if (e.getMessage().contains("REFERENCE constraint")) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Cannot delete this department because it has employees assigned to it.\nTransfer the employees first!");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearForm;
    private javax.swing.JButton btnClearSeach;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDoInsert;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnStartAdd;
    private javax.swing.JComboBox<String> comboManager;
    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableDepartments;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JPanel toolbarPanel;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
