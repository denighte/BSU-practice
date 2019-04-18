package bsu.radchuk.task.service;

import bsu.radchuk.task.dao.DaoException;
import bsu.radchuk.task.dao.UserDao;
import bsu.radchuk.task.model.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class LoginService {

    public User loginUser(@NonNull String login,
                          @NonNull String password)
                           throws ServiceException {
        try (UserDao dao = new UserDao()) {
            User loggedUser = dao.find(login);
            if (loggedUser == null) {
                return null;
            }
            String hashedPassword = hashPassword(password);
            if(loggedUser.getPasswordHash().equals(hashedPassword)) {
                return loggedUser;
            }
            return null;
        } catch (DaoException exception) {
            throw new ServiceException("Service is unavailable!", exception);
        }
    }

    public int registerUser(@NonNull User user) throws ServiceException {
        try (UserDao dao = new UserDao()) {
            user.setPasswordHash(hashPassword(user.getPasswordHash()));
            int id = dao.insert(user);
            user.setId(id);
            return id;
        } catch (DaoException exception) {
            throw new ServiceException("Service is unavailable!", exception);
        }
    }

    /**
     * MD5 simple hashing.
     * @param password password to hash
     * @return password md5 hash
     */
    private String hashPassword(String password) throws ServiceException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            var sb = new StringBuilder();
            //Get the hash's bytes
            //Bytes are in decimal format;
            //Convert it to hexadecimal format
            for (var b : md.digest()) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Fatal: unable to find hash algorithm.", e);
            throw new ServiceException("Fatal: unable to find hash algorithm.", e);
        }
    }
}
