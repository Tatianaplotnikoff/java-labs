package ua.nure.plotnykova.usermanagement.gui;

import ua.nure.plotnykova.usermanagement.domain.User;
import ua.nure.plotnykova.usermanagement.exception.DatabaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Objects;

public class AddPanel extends ManagementPanel {

    AddPanel(MainFrame parent) {
        super(parent, "addPanel");
    }

    @Override
    protected JTextField getDateOfBirthField() {
        if(Objects.isNull(dateOfBirth)) {
            dateOfBirth = new JTextField();
            dateOfBirth.setName("dateOfBirthField");
        }
        return dateOfBirth;
    }

    @Override
    protected JTextField getLastNameField() {
        if(Objects.isNull(lastNameField)) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    @Override
    protected JTextField getFirstNameField() {
        if(Objects.isNull(firstNameField)) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("ok".equalsIgnoreCase(e.getActionCommand())) {
            user = new User();
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
                parent.getDao().create(user);
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

        clearFields();
        this.setVisible(false);
        parent.showBrowsePanel();
    }
}

