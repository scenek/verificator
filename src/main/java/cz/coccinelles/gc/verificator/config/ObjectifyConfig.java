package cz.coccinelles.gc.verificator.config;

import com.googlecode.objectify.ObjectifyService;

import cz.coccinelles.gc.verificator.model.Cache;
import cz.coccinelles.gc.verificator.model.Stage;
import cz.coccinelles.gc.verificator.model.UserStage;

public class ObjectifyConfig {

	public void init() {
		ObjectifyService.init();
		ObjectifyService.register(Cache.class);
		ObjectifyService.register(Stage.class);
		ObjectifyService.register(UserStage.class);
	}
}
