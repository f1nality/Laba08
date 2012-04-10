package laba08;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author 3D-GRAF
 */
public class MainWindow extends JFrame {
    private JPanel mainPane = new JPanel();
    private JPanel controlsPane = new JPanel();
    private JPanel accountsPane = new JPanel();
    private JScrollPane accountsScroll = new JScrollPane(accountsPane, ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);
    private JButton transferButton = new JButton("Перевести деньги");
    private JButton newAccountButton = new JButton("Создать счёт");
    private JButton refreshButton = new JButton("Обновить");
    private JLabel accountsInfo = new JLabel();
    
    public MainWindow() {
        super("MySQL клиент");
        setSystemLookAndFeel();

        mainPane.setLayout(new MigLayout());
        mainPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        controlsPane.setBorder(new TitledBorder("Управление"));
        controlsPane.setLayout(new MigLayout("", "[][]push[]", ""));
        accountsPane.setLayout(new MigLayout("", "[][]push[]", ""));
        accountsPane.setBackground(Color.white);
        accountsPane.setBorder(new EmptyBorder(4, 4, 4, 4));
        
        final MainWindow t = this;
        
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransferWindow transfer = new TransferWindow(t);
                transfer.setModal(true);
                transfer.setVisible(true);
                
                refreshAccounts();
            }
        });
        
        newAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewAccountWindow newAccount = new NewAccountWindow(t);
                newAccount.setModal(true);
                newAccount.setVisible(true);
                
                refreshAccounts();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshAccounts();
            }
        });
        
        controlsPane.add(transferButton);
        controlsPane.add(newAccountButton);
        controlsPane.add(refreshButton);
        
        mainPane.add(controlsPane, "wrap, width 100%, gapbottom 10");
        mainPane.add(accountsScroll, "wrap, width 100%, height 100%");
        mainPane.add(accountsInfo, "wrap, gaptop 10");
        
        this.getContentPane().add(mainPane, BorderLayout.CENTER);
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setIconImage(new ImageIcon("index.ico").getImage());
        //this.setIconImage(Toolkit.getDefaultToolkit().getImage("index.ico"));
    }
    
    public void refreshAccounts() {
        accountsPane.removeAll();
        
        try {
            Account[] accounts = Laba08.db.getAccounts();
            addAccounts(accounts);
            accountsInfo.setText("Всего счетов: " + accounts.length);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        accountsPane.validate();
        accountsPane.repaint();
    }
    
    public void addAccount(Account account) {
        JLabel name = new JLabel("<html><b>" + account.getName() + "</b></html>");
        JLabel money = new JLabel("(Баланс: " + account.getMoney().toString() + ")");
        JButton button = new JButton("Удалить");
        
        accountsPane.setBackground(Color.white);
        button.setBackground(Color.white);
        
        button.addActionListener(new DeleteButtonListener(this, account));
        
        accountsPane.add(name);
        accountsPane.add(money);
        accountsPane.add(button, "wrap");
    }
    
    public void addAccounts(Account[] accounts) {
        for (Account a : accounts) {
            addAccount(a);
        }
    }
    
    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    class DeleteButtonListener implements ActionListener {
        private Account account;
        private JFrame frame;
        
        public DeleteButtonListener(JFrame frame, Account account) {
            this.account = account;
            this.frame = frame;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (JOptionPane.showConfirmDialog(this.frame, "Вы точно хотите удалить счёт " + account.getName() + "?") == 0) {
                    Laba08.db.deleteAccount(this.account.getId());
                    refreshAccounts();
                    JOptionPane.showMessageDialog(this.frame, "Операция успешно выполнена");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } catch (UnsupportedEncodingException ex) {
                System.out.println(ex.getMessage());
            } catch (NoSuchAlgorithmException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}