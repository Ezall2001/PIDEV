package services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import config.Jdbc_connection;
import config.Log;
import entities.User;
import entities.User_session;
import java.security.Key;
import javax.crypto.KeyGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class User_session_service {
    Connection cnx;

    public User_session_service() {
        cnx = Jdbc_connection.getInstance();
    }

    private static final String SESSIONS_TABLE = "sessions";

    private static Key generate_key() {
        try {
            KeyGenerator key_generator = KeyGenerator.getInstance("HmacSHA256");
            return key_generator.generateKey();
        } catch (Exception e) {
            Log.console(e.getMessage());
        }
        return null;
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
            Log.console(e);
        }
        return false;
    }

    public User_session create_session(User user, long expiresInMinutes) {
        if (check_email(user)) {

            String sql = "INSERT INTO " + SESSIONS_TABLE
                    + " (session_id, email, token, created_at, expires_at) VALUES (?, ?, ?, ?, ?)";
            String session_id = Jwts.builder()
                    .setSubject(user.get_email())
                    .signWith(SignatureAlgorithm.HS256, generate_key())
                    .compact();
            Instant now = Instant.now();
            Instant expires_at = now.plusSeconds(expiresInMinutes * 60);
            Date expiration = Date.from(expires_at);
            String token = generate_token(user, expiration);
            try (PreparedStatement pst = cnx.prepareStatement(sql)) {
                pst.setString(1, session_id);
                pst.setString(2, user.get_email()); //hedhy 
                pst.setString(3, token);
                pst.setTimestamp(4, Timestamp.from(now));
                pst.setTimestamp(5, Timestamp.from(expires_at));
                pst.executeUpdate();
                Log.console("session created SUCCESSFULLY <3");
                return new User_session(session_id, user, token, now, expires_at);
            } catch (Exception e) {
                Log.console(e.getMessage());
            }
        } else {
            Log.console("user doesn't exist");
        }
        return null;
    }
    ///TODO: change it with email (take armen opinion)

    public void delete_session(String session_id) {
        String sql = "DELETE FROM " + SESSIONS_TABLE + " WHERE session_id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, session_id);
            pst.execute();
            Log.console("session deleted :( )");
        } catch (Exception e) {

            Log.console(e.getMessage());
        }
    }

    public void delete_expired_sessions() {
        String sql = "DELETE FROM " + SESSIONS_TABLE + " WHERE  expires_at < ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setTimestamp(1, (Timestamp.from(Instant.now())));
            pst.execute();
            Log.console("ALL EXPIRED sessions ARE  deleted :( )");
        } catch (Exception e) {
            Log.console(e.getMessage());
        }
    }

    public Optional<User_session> get_session(String session_id) {
        String sql = "SELECT * FROM " + SESSIONS_TABLE + " WHERE session_id = ? " +
                "AND expires_at > ? ";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, session_id);
            pst.setTimestamp(2, (Timestamp.from(Instant.now())));
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                User user = new User(result.getString("email"));
                Instant created_at = result.getTimestamp("created_at").toInstant();
                Instant expires_at = result.getTimestamp("expires_at").toInstant();
                Date expiration = Date.from(expires_at);
                Log.console("session exists and not expired ");
                return Optional.of(new User_session(session_id, user, generate_token(user,
                        expiration), created_at, expires_at));
            } else {
                Log.console("session not found might be expired ");
                return Optional.empty();
            }
        } catch (Exception e) {

            Log.console(e.getMessage());
        }
        return null;
    }

    //TODO: bleda w rkeka tae getALL
    public List get_All() {
        List<User_session> user_session_list = new ArrayList<>();
        try {
            String sql = "select * from " + SESSIONS_TABLE;
            Statement pst = cnx.createStatement();
            ResultSet rSet = pst.executeQuery(sql);

            while (rSet.next()) {
                User user = new User(rSet.getString("email"));
                String session_id = rSet.getString("session_id");
                Instant created_at = rSet.getTimestamp("created_at").toInstant();
                Instant expires_at = rSet.getTimestamp("expires_at").toInstant();
                Date expiration = Date.from(expires_at);
                User_session user_session = new User_session(session_id, user, generate_token(user,
                        expiration), created_at, expires_at);
                user_session_list.add(user_session);

            }
        } catch (Exception e) {
            Log.console(e.getMessage());
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

}
