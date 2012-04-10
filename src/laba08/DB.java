package laba08;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 *
 * @author 3D-GRAF
 */
public class DB {
    private Connection connection;
    public static Account[] accounts;
    
    public DB() throws ClassNotFoundException, SQLException {
        InitializeConnection();
    }
    
    public void transferMoney(String fromName, String toName, Float amount, String password) throws Exception {
        Account from = findAccountByName(fromName);
        Account to = findAccountByName(toName);
        
        if (from == null) throw new Exception("Cant find 'from' account");
        else if (to == null) throw new Exception("Cant find 'to' account");
        else if (from.getMoney() < amount) throw new Exception("Not enough money");
        else if (from.getMoney() < amount) throw new Exception("Not enough money");
        else if (from.getPassword().equals(Account.cryptPassword(password)) == false) throw new Exception("Bad password");
        
        Statement stmt;
        
        stmt = this.connection.createStatement();
        stmt.executeUpdate("UPDATE accounts SET money = money - " + amount + " WHERE id = " + from.getId());
        from.setMoney(from.getMoney() - amount);
        
        stmt = this.connection.createStatement();
        stmt.executeUpdate("UPDATE accounts SET money = money + " + amount + " WHERE id = " + to.getId());
        to.setMoney(to.getMoney() + amount);
    }
    
    private Account findAccountByName(String name) {
        Account result = null;
        
        for (Account a : accounts) {
            if (a.getName().equals(name)) {
                result = a;
                break;
            }
        }
        
        return result;
    }
    
    public int addAccount(String name, String password, Float money) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException, Exception {
        Statement stmt = null;
        ResultSet rs = null;
        
        stmt = this.connection.createStatement();
        rs = stmt.executeQuery("SELECT count(*) FROM accounts WHERE name = '" + name + "'");
        rs.first();
        
        if (rs.getInt(1) != 0) {
            throw new Exception("Account exists");
        }
        
        return stmt.executeUpdate("INSERT INTO accounts (name, password, money) VALUES ('" + name + "', '" + Account.cryptPassword(password) + "', " + money + ")");
    }
    
    public int deleteAccount(int id) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        Statement stmt = this.connection.createStatement();
        return stmt.executeUpdate("DELETE FROM accounts WHERE id = " + id);
    }
    
    public Account[] getAccounts() throws SQLException {
        accounts = null;
        
        Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM accounts");
        
        int i = 0;
        rs.last();
        accounts = new Account[rs.getRow()];
        rs.beforeFirst();

        while (rs.next()) {
            accounts[i] = new Account(rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getFloat("money"));
            i++;
        }
        
        return accounts;
    }
    
    private void InitializeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        //String connectionUrl = "jdbc:mysql://fgcl.ru/iu8?user=iu8&password=qwerty";
        String connectionUrl = "jdbc:mysql://localhost/iu8?user=root&password=";
        this.connection = DriverManager.getConnection(connectionUrl);
    }
    
    public void CloseConnection() throws SQLException {
        this.connection.close();
    }
}
