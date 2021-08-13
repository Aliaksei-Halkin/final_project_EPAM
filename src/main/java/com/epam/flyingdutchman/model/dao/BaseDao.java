package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.Entity;
import com.epam.flyingdutchman.exception.DaoException;

public interface BaseDao <K,T extends Entity> {
   // boolean update(T t) throws DaoException;
}
