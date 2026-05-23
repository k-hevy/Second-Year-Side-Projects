package banking.dao;

import java.sql.*;
import banking.DatabaseConnection;

public class TransactionDAO {

    Connection con = DatabaseConnection.getConnection();

    public void addTransaction(int sender, int receiver, String type, double amount) {
        try {
            String sql = "INSERT INTO transactions (sender_account, receiver_account, type, amount) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sender);
            ps.setInt(2, receiver);
            ps.setString(3, type);
            ps.setDouble(4, amount);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
