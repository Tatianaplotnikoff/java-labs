package ua.nure.plotnykova.usermanagement.gui;

import ua.nure.plotnykova.usermanagement.exception.DatabaseException;
import ua.nure.plotnykova.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class BrowsePanel extends JPanel implements ActionListener {
    private final MainFrame parent;
    private JPanel buttonsPanel;
    private JButton addButton;
    private JButton editButton;
    private JButton detailsButton;
    private JButton deleteButton;
    private JScrollPane tablePanel;
    private JTable userTable;

    public BrowsePanel(MainFrame mainFrame) {
        parent = mainFrame;
        initialize();
    }

    private void initialize() {
        this.setName("browsePanel");
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private Component getButtonsPanel() {
        if (Objects.isNull(buttonsPanel)) {
            buttonsPanel = new JPanel();
            buttonsPanel.add(getAddButton(), null);
            buttonsPanel.add(getEditButton(), null);
            buttonsPanel.add(getDeleteButton(), null);
            buttonsPanel.add(getDetailsButton(), null);
        }
        return buttonsPanel;
    }

    private JButton getAddButton() {
        if (Objects.isNull(addButton)) {
            addButton = new JButton();
            addButton.setText(Messages.getString("BrowsePanel.add"));
            addButton.setName("addButton");
            addButton.setActionCommand("add");
            addButton.addActionListener(this);
        }
        return addButton;
    }

    private JButton getEditButton() {
        if (Objects.isNull(editButton)) {
            editButton = new JButton();
            editButton.setText(Messages.getString("BrowsePanel.edit"));
            editButton.setName("editButton");
            editButton.setActionCommand("edit");
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JButton getDeleteButton() {
        if (Objects.isNull(deleteButton)) {
            deleteButton = new JButton();
            deleteButton.setText(Messages.getString("BrowsePanel.delete"));
            deleteButton.setName("deleteButton");
            deleteButton.setActionCommand("delete");
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getDetailsButton() {
        if (Objects.isNull(detailsButton)) {
            detailsButton = new JButton();
            detailsButton.setText(Messages.getString("BrowsePanel.details"));
            detailsButton.setName("detailsButton");
            detailsButton.setActionCommand("details");
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    private JScrollPane getTablePanel() {
        if (Objects.isNull(tablePanel)) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }

    private JTable getUserTable() {
        if (Objects.isNull(userTable)) {
            userTable = new JTable();
            userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            userTable.setName("userTable");
        }
        return userTable;
    }

    public void initTable() {
        UserTableModel model = null;
        try {
            model = new UserTableModel(parent.getDao().findAll());
        } catch (DatabaseException e) {
            model = new UserTableModel(new ArrayList<>());
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
        getUserTable().setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if ("add".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showAddPanel();
        } else if ("edit".equalsIgnoreCase(actionCommand)) {
            chooseUser("Вы не выбрали пользователя для редактирования", this::showEditUserPanel);
        } else if ("delete".equalsIgnoreCase(actionCommand)) {
            chooseUser("Вы не выбрали пользователя для удаления", this::deleteUserAction);
        } else if ("details".equalsIgnoreCase(actionCommand)) {
            chooseUser("Вы не выбрали пользователя", this::showDetailsUserPanel);
        }
    }

    private boolean isRowSelected() {
        return userTable.getSelectedRowCount() > 0;
    }

    private void chooseUser(String warningMessage, UserFunction function) {
        if (isRowSelected()) {
            function.doFunction();
        } else {
            JOptionPane.showMessageDialog(this, warningMessage, "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean isDeleteUserConfirmed(Long userId) {
        int buttonIndex = JOptionPane.showConfirmDialog(this, "Вы уверенны что хотети удалить пользователя с ID = " + userId,
                "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        return buttonIndex == 0;
    }

    private void deleteUserAction(Long userId) {
        try {
            parent.getDao().delete(userId);
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUserAction() {
        int selectedRow = userTable.getSelectedRow();
        Long userId = (Long) userTable.getValueAt(selectedRow, 0);
        if (isDeleteUserConfirmed(userId)) {
            deleteUserAction(userId);
            initTable();
        }
    }

    private void showEditUserPanel() {
        Long userId = (Long) userTable.getValueAt(userTable.getSelectedRow(), 0);
        this.setVisible(false);
        parent.showEditPanel(userId);
    }

    private void showDetailsUserPanel() {
        Long userId = (Long) userTable.getValueAt(userTable.getSelectedRow(), 0);
        this.setVisible(false);
        parent.showDetailsPanel(userId);
    }

    @FunctionalInterface
    private interface UserFunction {
        void doFunction();
    }
}

