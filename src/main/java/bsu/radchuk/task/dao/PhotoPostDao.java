package bsu.radchuk.task.dao;

import bsu.radchuk.task.dao.framework.Executor;
import bsu.radchuk.task.model.PhotoPost;
import bsu.radchuk.task.model.User;
import lombok.NonNull;

import java.sql.SQLException;

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
     * query executor object.
     * @see bsu.radchuk.task.dao.framework.Executor
     */
    private Executor executor = new Executor();

    public PhotoPost find(final int id) throws DaoException {
        try {
            return executor.execQuery(FIND_POST_BY_ID, rs -> {
                if (rs.next()) {
                    return PhotoPost.builder().id(rs.getInt(ID))
                            .description(rs.getString(DESCRIPTION))
                            .author(rs.getString(AUTHOR))
                            .date(rs.getString(DATE))
                            .src(rs.getString(SRC))
                            .build();
                }
                return null;
            }, Integer.toString(id));
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public int delete(final int id) throws DaoException {
        try {
            return executor.execUpdate(DELETE_POST_BY_ID, Integer.toString(id));
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

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

    /**
     * closes the executor
     * @throws Exception
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
