package cz.coccinelles.gc.verificator.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

//@xTransactional
//@aRepository
public abstract class Dao<ENTITY> {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	protected EntityManager em;
		
	protected Class<ENTITY> type;
	
	protected Dao(Class<ENTITY> type) {
		this.type = type;
		//this.type = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	protected Query query(String query) {
		return em.createQuery(query);
	}
	
	public ENTITY get(Key key) {
		ENTITY entity = em.find(type, key);
		if (entity == null)
			throw new NoResultException(KeyFactory.keyToString(key));
		return entity;
	}
	
	public ENTITY get(String key) {
		return get(KeyFactory.stringToKey(key));
	}
	
	@Transactional
	public void save(ENTITY ent) {
		em.persist(ent);
	}

}