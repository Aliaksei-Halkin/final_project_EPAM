package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.Entity;
import com.epam.flyingdutchman.exception.DaoException;
/**
 * The interface represents base dao  for other dao
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public interface BaseDao<T extends Entity> {
    boolean update(T t) throws DaoException;

}
