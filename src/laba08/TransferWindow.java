/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laba08;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author 3D-GRAF
 */
public class TransferWindow extends JDialog {
    private JPanel mainPane = new JPanel();
    private JLabel labelDescription = new JLabel("<html>Выберите с какого счёта на какой вы хотите перевести деньги, сумму платежа, пароль и нажмите кнопку Перевести.<br><b>Требуется подтвердение операции с помощью пароля.</b></html>");
    private JLabel labelFrom = new JLabel("Откуда:");
    private JComboBox selectFrom = new JComboBox();
    private JLabel labelTo = new JLabel("Куда:");
    private JComboBox selectTo = new JComboBox();
    private JLabel labelAmount = new JLabel("Сумма:");
    private JTextField amount = new JTextField();
    private JLabel labelPassword = new JLabel("Пароль:");
    private JPasswordField password = new JPasswordField();
    private JButton button = new JButton("Перевести");
    private Account[] accountsFrom = null;
    private Account[] accountsTo = null;
    
    public TransferWindow(JFrame parent) {
        super(parent, "Перевод средств со счёта на счёт");
        
        //mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        mainPane.setLayout(new GridBagLayout());
        InitializeSelects();
        GridBagConstraints c = new GridBagConstraints();
        
        mainPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        labelDescription.setPreferredSize(new Dimension(320, 60));
        
        selectFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("comboBoxChanged")) selectFromChanged();
            }
        });
        
        final JDialog f = this;
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == 1001) {
                    try {
                        float money = Float.valueOf(amount.getText().trim()).floatValue();
                        Laba08.db.transferMoney(accountsFrom[selectFrom.getSelectedIndex()].getName(), accountsTo[selectTo.getSelectedIndex()].getName(), money, new String(password.getPassword()));
                        JOptionPane.showMessageDialog(f, "Операция успешно выполнена");
                        f.dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(f, "Некорректная сумма счёта");
                    } catch (Exception ex) {
                        if (ex.getMessage().equals("Bad password")) {
                            JOptionPane.showMessageDialog(f, "Неверный пароль");
                        } else {
                            System.out.println("Exception: " + ex.getMessage());
                        }
                    }
                }
            }
        });
        
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 20, 0); 
        c.anchor = GridBagConstraints.CENTER;
        mainPane.add(labelDescription, c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.CENTER;
        mainPane.add(labelFrom, c);
        
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.CENTER;
        mainPane.add(selectFrom, c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.CENTER;
        mainPane.add(labelTo, c);
        
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.CENTER;
        mainPane.add(selectTo, c);
        
        c.gridx = 0;
        c.gridy = 3;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.CENTER;
        mainPane.add(labelAmount, c);
        
        c.gridx = 1;
        c.gridy = 3;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.CENTER;
        mainPane.add(amount, c);
        
        c.gridx = 0;
        c.gridy = 4;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.CENTER;
        mainPane.add(labelPassword, c);
        
        c.gridx = 1;
        c.gridy = 4;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.CENTER;
        mainPane.add(password, c);
        
        c.gridx = 0;
        c.gridy = 5;
        c.ipadx = 10;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 0); 
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        
        mainPane.add(button, c);
        
        this.getContentPane().add(mainPane, BorderLayout.CENTER);
        this.getRootPane().setDefaultButton(button);
        this.pack();
        //this.setSize(320, 240);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    private void InitializeSelects() {
        selectFrom.removeAllItems();
        accountsFrom = new Account[DB.accounts.length];
        
        int i = 0;
        
        for (Account a : DB.accounts) {
            selectFrom.addItem(getAccountSelectView(a));
            accountsFrom[i] = a;
            i++;
        }
        
        selectFromChanged();
    }
    
    private void selectFromChanged() {
        selectTo.removeAllItems();
        accountsTo = new Account[DB.accounts.length - 1];
        
        int i = 0;
        
        for (Account a : DB.accounts) {
            if (getAccountSelectView(a).equals(selectFrom.getSelectedItem())) continue;
            selectTo.addItem(getAccountSelectView(a));
            accountsTo[i] = a;
            i++;
        }
    }
    
    private String getAccountSelectView(Account account) {
        return account.getName() + " (" + account.getMoney() + ")";
    }
}