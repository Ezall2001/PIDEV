package services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import utils.Jdbc_connection;
import utils.Log;
import entities.User;
import entities.User_session;
import java.security.Key;
import javax.crypto.KeyGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User_session_service {
    Connection cnx;
    private static User_session_service instance = null;

    public User_session_service() {
        cnx = Jdbc_connection.getInstance();
    }

    private static final String SESSIONS_TABLE = "user_sessions";

    private static Key generate_key() {
        try {
            KeyGenerator key_generator = KeyGenerator.getInstance("HmacSHA256");
            return key_generator.generateKey();
        } catch (Exception e) {
            Log.file(e.getMessage());
        }
        return null;
    }

    public User_session create_session(User user) {
        if (check_email(user)) {
            String sql = "INSERT INTO " + SESSIONS_TABLE
                    + " (email, token, created_at, expires_at) VALUES (?, ?, ?, ?)";
            Instant now = Instant.now();
            Instant expires_at = now.plusSeconds(240 * 60); //expires in 4 hours
            Date expiration = Date.from(expires_at);
            String token = generate_token(user, expiration);
            try (PreparedStatement pst = cnx.prepareStatement(sql)) {
                pst.setString(1, user.get_email());
                pst.setString(2, token);
                pst.setTimestamp(3, Timestamp.from(now));
                pst.setTimestamp(4, Timestamp.from(expires_at));
                pst.executeUpdate();
                Log.console("here");
                return new User_session(user, token, now, expires_at);
            } catch (Exception e) {
                Log.file(e.getMessage());
            }
        }
        return null;
    }

    public void delete_session_by_email(String email) {
        String sql = "DELETE FROM " + SESSIONS_TABLE + " WHERE email = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, email);
            pst.execute();
        } catch (Exception e) {

            Log.file(e.getMessage());
        }
    }

    public void delete_expired_sessions() {
        String sql = "DELETE FROM " + SESSIONS_TABLE + " WHERE  expires_at < ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setTimestamp(1, (Timestamp.from(Instant.now())));
            pst.execute();
        } catch (Exception e) {
            Log.file(e.getMessage());
        }
    }

    public User_session find_session_by_id(Integer session_id) {
        String sql = "SELECT * FROM " + SESSIONS_TABLE + " WHERE session_id = ? " +
                "AND expires_at > ? ";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, session_id);
            pst.setTimestamp(2, (Timestamp.from(Instant.now())));
            ResultSet rSet = pst.executeQuery();
            if (rSet.next()) {
                User user = new User(rSet.getString("email"));
                Instant created_at = rSet.getTimestamp("created_at").toInstant();
                Instant expires_at = rSet.getTimestamp("expires_at").toInstant();
                Date expiration = Date.from(expires_at);
                Log.console("session exists and not expired ");
                return (new User_session(session_id, user, generate_token(user, expiration), created_at, expires_at));
            }
        } catch (Exception e) {

            Log.console(e.getMessage());
        }
        return null;
    }

    public boolean find_session_by_email(String email) {
        String sql = "SELECT COUNT(*) FROM " + SESSIONS_TABLE + " WHERE email = ? " +
                "AND expires_at > ? ";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, email);
            pst.setTimestamp(2, (Timestamp.from(Instant.now())));
            ResultSet rSet = pst.executeQuery();
            rSet.next();
            int count = rSet.getInt(1);
            if (count > 0) {
                return true;
            }
        } catch (Exception e) {

            Log.file(e.getMessage());
        }
        return false;
    }

    public boolean check_email(User user) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, user.get_email());
            ResultSet rSet = pst.executeQuery();
            rSet.next();
            int count = rSet.getInt(1);
            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            Log.file(e.getMessage());
        }
        return false;
    }

    public List<User_session> get_all() {
        List<User_session> user_session_list = new ArrayList<>();
        try {
            String sql = "SELECT s.session_id, s.created_at, s.expires_at, s.token, " +
                    "u.id, u.first_name, u.last_name, u.bio, u.age, u.score, u.email, " +
                    "u.password, u.avatar_path, u.type " +
                    "FROM " + SESSIONS_TABLE + " s " +
                    "JOIN users u " +
                    "ON s.email = u.email";
            Statement pst = cnx.createStatement();
            ResultSet rSet = pst.executeQuery(sql);

            while (rSet.next()) {
                User user = new User(
                        rSet.getInt("id"),
                        rSet.getString("first_name"),
                        rSet.getString("last_name"),
                        rSet.getString("bio"),
                        rSet.getInt("age"),
                        rSet.getInt("score"),
                        rSet.getString("email"),
                        rSet.getString("avatar_path"),
                        rSet.getString("type"));
                Integer session_id = rSet.getInt("session_id");
                Instant created_at = rSet.getTimestamp("created_at").toInstant();
                Instant expires_at = rSet.getTimestamp("expires_at").toInstant();
                Date expiration = Date.from(expires_at);
                User_session user_session = new User_session(session_id, user, generate_token(user,
                        expiration), created_at, expires_at);
                user_session_list.add(user_session);
            }
        } catch (Exception e) {
            Log.file(e.getMessage());
        }
        return user_session_list;
    }

    public String generate_token(User user, Date expiration) {
        return Jwts.builder()
                .setSubject(user.get_email())
                .setExpiration(
                        expiration)
                .signWith(SignatureAlgorithm.HS256,
                        generate_key())
                .compact();
    }

    public static User_session get_user_session(User user) throws SQLException {
        Connection cnx;
        cnx = Jdbc_connection.getInstance();
        String sql = "SELECT * FROM user_sessions WHERE email = ? AND expires_at > ?";
        PreparedStatement pst = cnx.prepareStatement(sql);
        pst.setString(1, user.get_email());
        pst.setTimestamp(2, Timestamp.from(Instant.now()));
        ResultSet rSet = pst.executeQuery();
        if (rSet.next()) {
            User_session session = new User_session();
            session.set_session_id(rSet.getInt("session_id"));
            session.set_user(user);
            session.set_token(rSet.getString("token"));
            session.set_created_at(rSet.getTimestamp("created_at").toInstant());
            session.set_expires_at(rSet.getTimestamp("expires_at").toInstant());
            return session;
        } else {
            return null;
        }
    }

    public static User_session_service get_user_session_service_instance() {
        if (instance == null) {
            instance = new User_session_service();
        }
        return instance;
    }
}