package ua.nure.plotnykova.usermanagement.gui;

import ua.nure.plotnykova.usermanagement.domain.User;
import ua.nure.plotnykova.usermanagement.exception.DatabaseException;
import ua.nure.plotnykova.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Optional;

public abstract class ManagementPanel extends JPanel implements ActionListener {

    private JPanel fieldPanel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private Color bgColor;
    private String panelName;
    MainFrame parent;
    JTextField dateOfBirth;
    JTextField lastNameField;
    JTextField firstNameField;
    User user;

    ManagementPanel(MainFrame mainFrame, Long userId, String panelName) {
        this.parent = mainFrame;
        this.panelName = panelName;
        if (Objects.nonNull(userId)) {
            getUser(userId);
        } else {
            initialize(panelName);
        }
    }

    ManagementPanel(MainFrame parent, String panelName) {
        this(parent, null, panelName);
    }

    private void initialize(String panelName) {
        this.setName(panelName);
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    protected void getUser(Long userId) {
        try {
            Optional<User> userOptional = parent.getDao().find(userId);
            userOptional.ifPresent(user1 -> this.user = user1);
            initialize(panelName);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    protected abstract JTextField getLastNameField();

    protected abstract JTextField getDateOfBirthField();

    protected abstract JTextField getFirstNameField();

    private JPanel getButtonPanel() {
        if (Objects.isNull(buttonPanel)) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    protected JPanel getFieldPanel() {
        if (Objects.isNull(fieldPanel)) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, Messages.getString("ManagementPanel.first_name"), getFirstNameField());
            addLabeledField(fieldPanel, Messages.getString("ManagementPanel.last_name"), getLastNameField());
            addLabeledField(fieldPanel, Messages.getString("ManagementPanel.date_of_birth"), getDateOfBirthField());
        } else {
            setUserData();
        }
        return fieldPanel;
    }

    private void setUserData() {
        getFirstNameField();
        getLastNameField();
        getDateOfBirthField();
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    private JButton getOkButton() {
        if (Objects.isNull(okButton)) {
            okButton = new JButton();
            okButton.setText(Messages.getString("ManagementPanel.ok"));
            okButton.setName("okButton");
            okButton.setActionCommand("ok");
            okButton.addActionListener(this);
        }
        return okButton;
    }

    private JButton getCancelButton() {
        if (Objects.isNull(cancelButton)) {
            cancelButton = new JButton();
            cancelButton.setText(Messages.getString("ManagementPanel.cancel"));
            cancelButton.setName("cancelButton");
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    protected void clearFields() {
        getFirstNameField().setText("");
        getFirstNameField().setBackground(bgColor);

        getLastNameField().setText("");
        getLastNameField().setBackground(bgColor);

        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(bgColor);
    }
}
