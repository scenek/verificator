package cz.coccinelles.gc.verificator.dao;

import org.springframework.stereotype.Repository;

import cz.coccinelles.gc.verificator.model.UserStage;

@Repository
public class UserStageDao extends Dao<UserStage> {
	public UserStageDao() {
		super(UserStage.class);
	}
}
