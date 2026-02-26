package cz.coccinelles.gc.verificator.dao;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.ObjectifyService;

import cz.coccinelles.gc.verificator.model.Cache;
import cz.coccinelles.gc.verificator.model.Stage;

@Repository
public class StageDao extends Dao<Stage> {

	public StageDao() {
		super(Stage.class);
	}

	public Stage findByStageId(String stageId) {
		Stage stage = ObjectifyService.ofy().load().type(Stage.class)
				.filter("stageId", stageId).first().now();
		if (stage == null)
			throw new NoSuchElementException(stageId);
		return stage;
	}

	public Stage findByStageNo(Cache cache, Integer stageNo) {
		String cacheIdStr = cache.getId();
		if (cacheIdStr == null)
			throw new NoSuchElementException("Cache has no ID");
		Long cacheId = Long.parseLong(cacheIdStr);
		Stage stage = ObjectifyService.ofy().load().type(Stage.class)
				.filter("cacheId", cacheId)
				.filter("stageNo", stageNo)
				.first().now();
		if (stage == null)
			throw new NoSuchElementException(stageNo.toString());
		return stage;
	}
}
