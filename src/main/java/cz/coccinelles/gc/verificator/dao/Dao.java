package cz.coccinelles.gc.verificator.dao;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.ObjectifyService;

public abstract class Dao<ENTITY> {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	protected final Class<ENTITY> type;

	protected Dao(Class<ENTITY> type) {
		this.type = type;
	}

	public ENTITY get(String id) {
		ENTITY entity = ObjectifyService.ofy().load().type(type).id(Long.parseLong(id)).now();
		if (entity == null)
			throw new NoSuchElementException(id);
		return entity;
	}

	public void save(ENTITY entity) {
		ObjectifyService.ofy().save().entity(entity).now();
	}
}
