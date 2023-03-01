package services;

import utils.Jdbc_connection;
import utils.Log;
import entities.User;
import entities.User_session;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class User_session_service {
    Connection cnx;

    static private User_service user_service = new User_service();
    static private String session_path = "server/user_requests_headers/session_id.txt";

    public User_session_service() {
        cnx = Jdbc_connection.getInstance();
    }

    private void write_session(Integer id_session) {
        String session_header = "SESSION_ID=";
        if (id_session != null)
            session_header = String.format("SESSION_ID=%s", id_session);

        try {
            FileWriter session_file_writer = new FileWriter(session_path, false);
            session_file_writer.write(session_header);
            session_file_writer.close();
        } catch (Exception e) {
            Log.file(e.getMessage());
        }
    }

    private Integer read_session() {
        Integer id_session = null;

        try {
            File session_file = new File(session_path);
            Scanner file_reader = new Scanner(session_file);
            if (file_reader.hasNextLine()) {
                String line = file_reader.nextLine();
                List<String> pairs = Arrays.asList(line.split("="));
                if (pairs.size() >= 2)
                    id_session = Integer.parseInt(pairs.get(1));
            }

            file_reader.close();
        } catch (Exception e) {
            Log.file(e.getMessage());
        }

        return id_session;
    }

    public User_session create_session(User user) {
        String sql = "INSERT INTO user_sessions (id_user, created_at, expires_at) VALUES (?, ?, ?)";
        Instant create_at = Instant.now();
        Instant expires_at = create_at.plusSeconds(240 * 60); //expires in 4 hours

        try {
            PreparedStatement stmt = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, user.get_id());
            stmt.setTimestamp(2, Timestamp.from(create_at));
            stmt.setTimestamp(3, Timestamp.from(expires_at));
            stmt.executeUpdate();

            ResultSet result = stmt.getGeneratedKeys();
            if (!result.next())
                return null;

            User_session session = new User_session();
            session.set_id(result.getInt(1));
            session.set_created_at(create_at);
            session.set_expires_at(expires_at);

            write_session(session.get_id());
        } catch (Exception e) {
            Log.file(e.getMessage());
        }

        return null;
    }

    public void delete_session() {
        Integer id_session = read_session();
        if (id_session == null)
            return;
        User_session session = new User_session();
        session.set_id(id_session);
        delete_session(session);
    }

    private void delete_session(User_session session) {
        String sql = "DELETE FROM user_sessions where id = ?";
        try {
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, session.get_id());
            stmt.executeUpdate();
            write_session(null);
        } catch (Exception e) {
            Log.file(e.getMessage());
        }
    }

    public User get_user() {
        Integer id_session = read_session();

        String sql = "SELECT * FROM user_sessions WHERE id = ?";
        try {
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, id_session);

            ResultSet result = stmt.executeQuery();
            if (!result.next())
                return null;

            User_session session = new User_session(id_session,
                    result.getTimestamp("created_at").toInstant(),
                    result.getTimestamp("expires_at").toInstant());

            if (session.is_expired()) {
                delete_session(session);
                return null;
            }

            User user = new User();
            user.set_id(result.getInt("id_user"));
            return user_service.find_by_id(user);

        } catch (Exception e) {
            Log.file(e.getMessage());
        }
        return null;
    }

}
