package ua.nure.plotnykova.usermanagement.gui;

import javafx.scene.input.DataFormat;
import ua.nure.plotnykova.usermanagement.domain.User;
import ua.nure.plotnykova.usermanagement.exception.DatabaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class DetailsPanel extends JPanel implements ActionListener {

    private MainFrame parent;
    private User user;
    private JPanel labelPanel;
    private JPanel buttonPanel;
    private JButton backButton;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel dateOfBirthLabel;
    private JLabel ageLabel;

    public DetailsPanel(MainFrame parent, Long userId) {
        this.parent = parent;
        initialize(userId);
    }

    private void initialize(Long userId) {
        try {
            Optional<User> userOptional = parent.getDao().find(userId);
            userOptional.ifPresent(userData -> this.user = userData);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        this.setName("detailsPanel");
        this.setLayout(new BorderLayout());
        this.add(getLabelPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    public void setUserId(Long userId) {
        initialize(userId);
    }

    private JPanel getButtonPanel() {
        if (Objects.isNull(buttonPanel)) {
            buttonPanel = new JPanel();
            buttonPanel.add(getBackButton(), null);
        }
        return buttonPanel;
    }

    private JButton getBackButton() {
        if (Objects.isNull(backButton)) {
            backButton = new JButton();
            backButton.setName("backButton");
            backButton.setText("Назад");
            backButton.setActionCommand("back");
            backButton.addActionListener(this);
        }
        return backButton;
    }

    private JPanel getLabelPanel() {
        if (Objects.isNull(labelPanel)) {
            labelPanel = new JPanel();
            labelPanel.setLayout(new GridLayout(4, 2));
            addLabel(labelPanel, "First name", getFirstNameLabel());
            addLabel(labelPanel, "Last name", getLastNameLabel());
            addLabel(labelPanel, "Date of birth", getDateOfBirthLabel());
            addLabel(labelPanel, "Age", getAgeLabel());
        } else {
            getUserData();
        }
        return labelPanel;
    }

    private void getUserData() {
        getAgeLabel();
        getFirstNameLabel();
        getDateOfBirthLabel();
        getLastNameLabel();
    }

    private JLabel getFirstNameLabel() {
        if (Objects.isNull(firstNameLabel)) {
            firstNameLabel = new JLabel();
            firstNameLabel.setText(user.getFirstName());
        } else {
            firstNameLabel.setText(user.getFirstName());
        }
        return firstNameLabel;
    }

    private JLabel getLastNameLabel() {
        if (Objects.isNull(lastNameLabel)) {
            lastNameLabel = new JLabel();
            lastNameLabel.setText(user.getLastName());
        } else {
            lastNameLabel.setText(user.getLastName());
        }
        return lastNameLabel;
    }

    private JLabel getDateOfBirthLabel() {
        DateFormat dateFormat = DateFormat.getDateInstance();
        if (Objects.isNull(dateOfBirthLabel)) {
            dateOfBirthLabel = new JLabel();
            dateOfBirthLabel.setText(dateFormat.format(user.getDateOfBirth()));
        } else {
            dateOfBirthLabel.setText(dateFormat.format(user.getDateOfBirth()));

        }
        return dateOfBirthLabel;
    }

    private JLabel getAgeLabel() {
        if (Objects.isNull(ageLabel)) {
            ageLabel = new JLabel();
            ageLabel.setText(String.valueOf(user.getAge(new Date())));
        } else {
            ageLabel.setText(String.valueOf(user.getAge(new Date())));
        }
        return ageLabel;
    }

    private void addLabel(JPanel panel, String labelText, JLabel userData) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(userData);
        panel.add(label);
        panel.add(userData);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        clearDataLabels();
        this.setVisible(false);
        parent.showBrowsePanel();
    }

    private void clearDataLabels() {
        firstNameLabel.setText("");
        lastNameLabel.setText("");
        dateOfBirthLabel.setText("");
        ageLabel.setText("");
    }

}
