package ua.nure.plotnykova.usermanagement.gui;

import ua.nure.plotnykova.usermanagement.db.DaoFactory;
import ua.nure.plotnykova.usermanagement.db.UserDao;
import ua.nure.plotnykova.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Objects;

public class MainFrame extends JFrame {

    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_WIDTH = 800;

    private JPanel contentPanel;
    private JPanel browsePanel;
    private AddPanel addPanel;
    private EditPanel editPanel;
    private DetailsPanel detailsPanel;
    private UserDao dao;

    public MainFrame() throws HeadlessException {
        dao = DaoFactory.getInstance().getUserDao();
        initialize();
    }

    public UserDao getDao() {
        return dao;
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(Messages.getString("MainFrame.user_management"));
        this.setContentPane(getContentPanel());
    }

    private JPanel getContentPanel() {
        if (Objects.isNull(contentPanel)) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    private JPanel getBrowsePanel() {
        if (Objects.isNull(browsePanel)) {
            browsePanel = new BrowsePanel(this);
        }
        ((BrowsePanel)browsePanel).initTable();
        return browsePanel;
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);

    }

    public void showAddPanel() {
        showPanel(getAddPanel());
    }

    public void showEditPanel(Long userId) {
        showPanel(getEditPanel(userId));
    }

    public void showDetailsPanel(Long userId) {
        showPanel(getDetailsPanel(userId));
    }

    public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

    private JPanel getDetailsPanel(Long userId) {
        if(Objects.isNull(detailsPanel)) {
            detailsPanel = new DetailsPanel(this, userId);
        } else {
            detailsPanel.setUserId(userId);
        }
        return detailsPanel;
    }

    private ManagementPanel getEditPanel(Long userId) {
        if(Objects.isNull(editPanel)) {
            editPanel = new EditPanel(this, userId);
        } else {
            editPanel.setUserId(userId);
        }
        return editPanel;
    }

    private ManagementPanel getAddPanel() {
        if(Objects.isNull(addPanel)) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    private void showPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

}
