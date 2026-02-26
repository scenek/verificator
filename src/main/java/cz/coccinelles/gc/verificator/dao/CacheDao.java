package cz.coccinelles.gc.verificator.dao;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.ObjectifyService;

import cz.coccinelles.gc.verificator.model.Cache;

@Repository
public class CacheDao extends Dao<Cache> {

	public CacheDao() {
		super(Cache.class);
	}

	public List<Cache> list() {
		return ObjectifyService.ofy().load().type(Cache.class).order("title").list();
	}

	public Cache findByCode(String code) {
		Cache c = ObjectifyService.ofy().load().type(Cache.class)
				.filter("code", code.toUpperCase()).first().now();
		if (c == null)
			throw new NoSuchElementException(code);
		return c;
	}

	public boolean cacheExists(String code) {
		try {
			findByCode(code);
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}
}
