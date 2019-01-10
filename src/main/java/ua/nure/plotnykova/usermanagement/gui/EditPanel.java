package ua.nure.plotnykova.usermanagement.gui;

import ua.nure.plotnykova.usermanagement.domain.User;
import ua.nure.plotnykova.usermanagement.exception.DatabaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Objects;

public class EditPanel extends ManagementPanel {

    public EditPanel(MainFrame parent, Long userId) {
        super(parent, userId, "editPanel");
    }

    public void setUserId(Long userId) {
        getUser(userId);
    }

    protected JTextField getLastNameField() {
        if(Objects.isNull(lastNameField)) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField");
            lastNameField.setText(user.getLastName());
        } else {
            lastNameField.setText(user.getLastName());
        }
        return lastNameField;
    }

    protected JTextField getDateOfBirthField() {
        DateFormat format = DateFormat.getDateInstance();
        if(Objects.isNull(dateOfBirth)) {
            dateOfBirth = new JTextField();
            dateOfBirth.setName("dateOfBirthField");
            dateOfBirth.setText(format.format(user.getDateOfBirth()));
        } else {
            dateOfBirth.setText(format.format(user.getDateOfBirth()));
        }
        return dateOfBirth;
    }

    protected JTextField getFirstNameField() {
        if(Objects.isNull(firstNameField)) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField");
            firstNameField.setText(user.getFirstName());
        } else {
            firstNameField.setText(user.getFirstName());

        }
        return firstNameField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("ok".equalsIgnoreCase(e.getActionCommand())) {
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            DateFormat format = DateFormat.getDateInstance();
            try {
                user.setDateOfBirth(format.parse(getDateOfBirthField().getText()));
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getDao().update(user);
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

        clearFields();
        this.setVisible(false);
        parent.showBrowsePanel();
    }
}
