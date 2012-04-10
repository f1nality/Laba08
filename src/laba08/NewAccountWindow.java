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
public class NewAccountWindow extends JDialog {
    private JPanel mainPane = new JPanel();
    private JLabel labelDescription = new JLabel("<html>Укажите имя счёта, пароль, начальное количество средств на счёте и нажмите кнопку Создать.</html>");
    private JLabel labelName = new JLabel("Имя:");
    private JTextField name = new JTextField();
    private JLabel labelPassword = new JLabel("Пароль:");
    private JPasswordField password = new JPasswordField();
    private JLabel labelAmount = new JLabel("Сумма на счёте:");
    private JTextField amount = new JTextField();
    private JButton button = new JButton("Создать");
    
    public NewAccountWindow(JFrame parent) {
        super(parent, "Создание счёта");
        
        //mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        mainPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        mainPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        labelDescription.setPreferredSize(new Dimension(320, 30));
        
        final JDialog f = this;
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == 1001) {
                    try {
                        float money = Float.valueOf(amount.getText().trim()).floatValue();
                        Laba08.db.addAccount(name.getText(), new String(password.getPassword()), money);
                        JOptionPane.showMessageDialog(f, "Операция успешно выполнена");
                        f.dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(f, "Некорректная сумма счёта");
                    } catch (Exception ex) {
                        if (ex.getMessage().equals("Account exists")) {
                            JOptionPane.showMessageDialog(f, "Счёт с таким именем уже существует");
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
        c.anchor = GridBagConstraints.WEST;
        mainPane.add(labelDescription, c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.WEST;
        mainPane.add(labelName, c);
        
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.WEST;
        mainPane.add(name, c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.WEST;
        mainPane.add(labelPassword, c);
        
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.WEST;
        mainPane.add(password, c);
        
        c.gridx = 0;
        c.gridy = 3;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.WEST;
        mainPane.add(labelAmount, c);
        
        c.gridx = 1;
        c.gridy = 3;
        c.ipadx = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 6, 0); 
        c.anchor = GridBagConstraints.WEST;
        mainPane.add(amount, c);
        
        c.gridx = 0;
        c.gridy = 4;
        c.ipadx = 10;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 0, 0); 
        c.anchor = GridBagConstraints.CENTER;
        mainPane.add(button, c);
        
        this.getContentPane().add(mainPane, BorderLayout.CENTER);
        this.getRootPane().setDefaultButton(button);
        this.pack();
        //this.setSize(320, 240);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}