package services;

import config.Jdbc_connection;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entities.User;
import entities.User.Grade;
import config.Jdbc_connection;
import config.Log;

public class User_service {
    Connection cnx;

    public User_service() {
        cnx = Jdbc_connection.getInstance();
    }

    public void add(User user) {
        String sql = "insert into users(first_name,last_name,age,level,picture_data,email,hashed_password,bio,score,type)"
                + "values (?,?,?,?,?,?,?,?,?,?)";
        try {

            String level_new_commer = "NEWCOMMER";
            String user_constant = "STUDENT";
            int score = 0;
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setString(1, user.get_first_name());
            pst.setString(2, user.get_last_name());
            pst.setInt(3, user.get_age());
            pst.setString(4, level_new_commer);
            pst.setBytes(5, user.get_picture_data());
            pst.setString(6, user.get_email());
            pst.setString(7, user.get_hashed_password());
            pst.setString(8, user.get_description());
            pst.setInt(9, score);
            pst.setString(10, user_constant);
            pst.executeUpdate();
            Log.console("Student added ! <3");

        } catch (Exception e) {
            Log.console(e.getMessage());
        }

    }

    public void update(User user) {
        String sql = "UPDATE users SET `first_name`=?, `last_name`=?, `age`=?,`picture_data`=?, `hashed_password`=?, `bio`=? where user_id="
                + user.get_user_id();
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setString(1, user.get_first_name());
            pst.setString(2, user.get_last_name());
            pst.setInt(3, user.get_age());
            pst.setBytes(4, user.get_picture_data());
            pst.setString(5, user.get_hashed_password());
            pst.setString(6, user.get_description());
            pst.executeUpdate();
            Log.console("Student Updated ! <3");

        } catch (Exception e) {
            Log.console(e.getMessage());
        }
    }

    public void delete(User user) {
        String sql = "delete from users where email=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setString(1, user.get_email());
            pst.executeUpdate();
            Log.console("Student deleted ! :(( ");
        } catch (Exception e) {
            Log.console(e.getMessage());
        }
    }

    //TODO: unknown enum POURQUOI ?
    public List get_All() {
        List<User> user_list = new ArrayList<>();
        try {
            String sql = "select * from users ";
            Statement pst = cnx.createStatement();
            ResultSet rSet = pst.executeQuery(sql);
            // Grade grad = Grade.valueOf(rSet.getString("level"));
            while (rSet.next()) {
                User user = new User(rSet.getString("first_name"), rSet.getString("last_name"), rSet.getInt("age"),
                        rSet.getString("bio"), rSet.getString("email"), rSet.getString("hashed_password"),
                        rSet.getBytes("picture_data"));
                user_list.add(user);

            }
        } catch (Exception e) {
            Log.console(e.getMessage());
        }
        return user_list;
    }

    public List<User> find_by_id(int id) {
        List<User> user_list = new ArrayList<>();
        try {
            String sql = "select * from users where user_id= '" + id + "'";
            PreparedStatement pst = cnx.prepareStatement(sql);
            ResultSet rSet = pst.executeQuery();
            while (rSet.next()) {
                User user = new User();
                user.set_email(rSet.getString("email"));
                user.set_first_name(rSet.getString("first_name"));
                user.set_last_name(rSet.getString("last_name"));
                user.set_age(rSet.getInt("age"));
                user.set_description(rSet.getString("bio"));
                user.set_picture_data(rSet.getBytes("picture_data"));
                user_list.add(user);
            }
        } catch (Exception e) {
            Log.console(e.getMessage());
        }
        return user_list;

    }

    public List<User> find_by_level(String grade) {
        List<User> user_list = new ArrayList<>();
        try {
            String sql = "select * from users where level= '" + grade + "'";
            PreparedStatement pst = cnx.prepareStatement(sql);
            ResultSet rSet = pst.executeQuery();
            while (rSet.next()) {
                User user = new User();
                user.set_email(rSet.getString("email"));
                user.set_first_name(rSet.getString("first_name"));
                user.set_last_name(rSet.getString("last_name"));
                user.set_age(rSet.getInt("age"));
                user.set_description(rSet.getString("bio"));
                user.set_picture_data(rSet.getBytes("picture_data"));
                user_list.add(user);
            }
        } catch (Exception e) {
            Log.console(e.getMessage());
        }
        return user_list;

    }

    /// TODO: correct this method
    // public List<User> sort() {
    //     List<User> user_list = new ArrayList<>();
    //     user_list = get_All();
    //     user_list = user_list.stream().sorted((a, b) -> b.get_score() -
    //             a.get_score())
    //             .collect(Collectors.toList());
    //     return user_list;
    // }

}
