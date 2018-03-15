package cz.coccinelles.gc.verificator.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import cz.coccinelles.gc.verificator.model.Cache;

@Repository
public class CacheDao extends Dao<Cache> {

	public CacheDao() {
		super(Cache.class);
	}

	@SuppressWarnings("unchecked")
	public List<Cache> list() {
		return query("select from Cache order by title").getResultList();
	}

	public Cache findByCode(String code) {
		try {
			Cache c = (Cache) query("select from Cache where code=:code")
					.setParameter("code", code).getSingleResult();
			return c;
		} catch (NoResultException e) {
			throw new NoResultException(code);
		} catch (NullPointerException e) {
			throw new NoResultException(code);
		}
	}

	public boolean cacheExists(String code) {
		try {
			findByCode(code);
		} catch (NoResultException e) {
			return false;
		}
		return true;
	}
}
