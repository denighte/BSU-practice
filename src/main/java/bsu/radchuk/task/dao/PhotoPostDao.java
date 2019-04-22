package bsu.radchuk.task.dao;

import bsu.radchuk.task.dao.framework.Executor;
import bsu.radchuk.task.model.PhotoPost;
import bsu.radchuk.task.model.User;
import lombok.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhotoPostDao implements AutoCloseable{
    /**
     * photo post id column name.
     */
    private static final String ID = "id";
    /**
     * photo post description column name.
     */
    private static final String DESCRIPTION = "description";
    /**
     * photo post author column name.
     */
    private static final String AUTHOR = "author";
    /**
     * photo post date column name.
     */
    private static final String DATE = "date";
    /**
     * photo post image link column name.
     */
    private static final String SRC = "src";
    /**
     * find photo post by id query.
     */
    private static final String FIND_POST_BY_ID
            = "SELECT * FROM posts WHERE id = ?;";
    /**
     * delete photo post by id query.
     */
    private static final String DELETE_POST_BY_ID
            = "DELETE FROM posts WHERE id = ?";
    /**
     * save photo post query.
     */
    private static final String SAVE_POST
            = "INSERT INTO posts (description, author, date, src)"
            + " VALUES (?, ?, ?, ?)";
    /**
     * Get last 10 posts.
     */
    private static final String GET_LAST_POSTS
            = "SELECT TOP ? * FROM posts ORDER BY id DESC;";
    /**
     * Get posts by date.
     */
    private static final String GET_POSTS_BY_DATE
            = "SELECT * FROM posts WHERE date = ?;";
    /**
     * Get posts by author.
     */
    private static final String GET_POSTS_BY_AUTHOR
            = "SELECT * FROM posts WHERE author = ?;";
    /**
     * Get posts by hashtags.
     */
    private static final String GET_POST_BY_HASHTAG
            = "SELECT * FROM posts WHERE description LIKE ?";
    /**
     * query executor object.
     * @see bsu.radchuk.task.dao.framework.Executor
     */
    private Executor executor = new Executor();

    /**
     * find photo post by id.
     * @param id photo post id.
     * @return PhotoPost new instance if found, otherwise null.
     * @throws DaoException if dao operation error occurred.
     */
    public PhotoPost find(final int id) throws DaoException {
        try {
            return executor.execQuery(FIND_POST_BY_ID, rs -> {
                if (rs.next()) {
                    return buildPost(rs);
                }
                return null;
            }, Integer.toString(id));
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    /**
     * get last n photo posts, n = number.
     * @param number number of posts to get.
     * @return PhotoPost collection.
     * @throws DaoException if dao operation error occurred.
     */
    public List<PhotoPost> findLast(final int number) throws DaoException {
        List<PhotoPost> list = new ArrayList<>();
        try {
            return executor.execQuery(GET_LAST_POSTS, rs -> {
                while (rs.next()) {
                    PhotoPost post = buildPost(rs);
                    if (post != null) {
                        list.add(post);
                    }
                }
                return list;
            }, Integer.toString(number));
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    /**
     * find photo posts by date.
     * @param date date to filter photo posts.
     * @return PhotoPost collection.
     * @throws DaoException if dao operation error occurred.
     */
    public List<PhotoPost> findByDate(final String date) throws DaoException {
        List<PhotoPost> list = new ArrayList<>();
        try {
            return executor.execQuery(GET_POSTS_BY_DATE, rs -> {
                while (rs.next()) {
                    PhotoPost post = buildPost(rs);
                    if (post != null) {
                        list.add(post);
                    }
                }
                return list;
            }, date);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    /**
     * find photo posts by author.
     * @param author author name to filter.
     * @return PhotoPost collection.
     * @throws DaoException if dao operation info occurred.
     */
    public List<PhotoPost> findByAuthor(final String author) throws DaoException {
        List<PhotoPost> list = new ArrayList<>();
        try {
            return executor.execQuery(GET_POSTS_BY_AUTHOR, rs -> {
                while (rs.next()) {
                    PhotoPost post = buildPost(rs);
                    if (post != null) {
                        list.add(post);
                    }
                }
                return list;
            }, author);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    /**
     * find photo posts by hashtag.
     * @param hashtag hashtag (without # !!!).
     * @return PhotoPost collection.
     * @throws DaoException if dao operation info occurred.
     */
    public List<PhotoPost> findByHashtag(final String hashtag) throws DaoException {
        List<PhotoPost> list = new ArrayList<>();
        try {
            return executor.execQuery(GET_POST_BY_HASHTAG, rs -> {
                while (rs.next()) {
                    PhotoPost post = buildPost(rs);
                    if (post != null) {
                        list.add(post);
                    }
                }
                return list;
            }, hashtag);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    /**
     * delete photo post with specified id.
     * @param id id of the photo post.
     * @return 1 if success, 0 otherwise.
     * @throws DaoException if dao operation info occurred.
     */
    public int delete(final int id) throws DaoException {
        try {
            return executor.execUpdate(DELETE_POST_BY_ID, Integer.toString(id));
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    /**
     * inserts photo post in database.
     * @param post PhotoPost instance.
     * @return id of inserted post.
     * @throws DaoException if dao operation info occurred.
     */
    public int insert(@NonNull final PhotoPost post) throws DaoException {
        try {
            return executor.execSave(SAVE_POST,
                    post.getDescription(),
                    post.getAuthor(),
                    post.getDate(),
                    post.getSrc());
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    private PhotoPost buildPost(ResultSet rs) throws SQLException {
        return PhotoPost.builder().id(rs.getInt(ID))
                .description(rs.getString(DESCRIPTION))
                .author(rs.getString(AUTHOR))
                .date(rs.getString(DATE))
                .src(rs.getString(SRC))
                .build();
    }

    /**
     * closes the executor
     * @throws DaoException if dao operation error occurred.
     */
    @Override
    public void close() throws DaoException {
        try {
            executor.close();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }
}
