package cz.coccinelles.gc.verificator.dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import cz.coccinelles.gc.verificator.model.Cache;
import cz.coccinelles.gc.verificator.model.Stage;

@Repository
public class StageDao extends Dao<Stage> {

	public StageDao() {
		super(Stage.class);
	}

	/*public void deleteItems(Cache cache) {
		for (Stage i : cache.getStages())
			em.remove(i);
	}*/

	public Stage findByStageId(String stageId) {
		try {
			Stage stage = (Stage) em
					.createQuery("select from Stage where stageId=:stageId")
					.setParameter("stageId", stageId).getSingleResult();
			return stage;
		} catch (NoResultException e) {
			throw new NoResultException(stageId);
		} catch (NullPointerException e) {
			throw new NoResultException(stageId);
		}
	}
	
	public Stage findByStageNo(Cache cache, Integer stageNo) {
		try {
			Stage stage = (Stage) em
					.createQuery("select from Stage where cache=:cache and stageNo=:stageNo")
					.setParameter("cache", cache).setParameter("stageNo", stageNo).getSingleResult();
			return stage;
		} catch (NoResultException e) {
			throw new NoResultException(stageNo.toString());
		} catch (NullPointerException e) {
			throw new NoResultException(stageNo.toString());
		}
	}
}
