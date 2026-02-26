package cz.coccinelles.gc.verificator.dao;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.googlecode.objectify.ObjectifyService;

import cz.coccinelles.gc.verificator.model.Cache;
import cz.coccinelles.gc.verificator.model.Stage;
import cz.coccinelles.gc.verificator.model.UserStage;
import cz.coccinelles.gc.verificator.model.ValidatorMessage;

@Service
public class UserStageValidator {

	@Autowired
	protected CacheDao cacheDao;

	@Autowired
	protected StageDao stageDao;

	private final String gcRE = "^GC[a-zA-Z0-9]{3,7}$";
	private final String stageRE = "^[1-9]\\d{0,1}$";
	private final String passRE = "^[a-zA-Z0-9&]{1,20}$";

	protected final Logger log = LoggerFactory.getLogger(getClass());

	public ValidatorMessage validate(UserStage userStage, BindingResult result) {
		ValidatorMessage message = new ValidatorMessage(result);
		int stageNoInt;

		// Validate GC code
		if (userStage.getCache() == null || !userStage.getCache().matches(gcRE)) {
			message.setRejectValue("cache", "required", "Should be GCxxxxx.");
			log.info("Verificator: invalid GC wpt.");
		}

		// Validate stage number (stageRE now rejects 0 directly)
		if (userStage.getStageNo() == null || !userStage.getStageNo().matches(stageRE)) {
			message.setRejectValue("stageNo", "required", "Use numbers (1, 2...) only.");
			log.info("Verificator: invalid stage.");
		}

		// Validate password format
		if (userStage.getPassword() == null || !userStage.getPassword().matches(passRE)) {
			message.setRejectValue("password", "required",
					"Use numbers, chars and few extra chars only.");
			log.info("Verificator: invalid password format.");
		}

		if (message.getResult().hasErrors())
			return message;

		// Verify cache exists
		Cache cache;
		try {
			cache = cacheDao.findByCode(userStage.getCache());
		} catch (NoSuchElementException ex) {
			message.setRejectValue("cache", "required", "Invalid cache. Please try again.");
			log.info("Verificator: nonexisting cache '{}'.", userStage.getCache());
			return message;
		}

		stageNoInt = Integer.parseInt(userStage.getStageNo());
		Long cacheId = Long.parseLong(cache.getId());

		// Batch-load current and previous stage in one Datastore query to avoid N+1
		List<Stage> stages = ObjectifyService.ofy().load().type(Stage.class)
				.filter("cacheId", cacheId)
				.filter("stageNo >=", stageNoInt == 1 ? stageNoInt : stageNoInt - 1)
				.filter("stageNo <=", stageNoInt)
				.list();

		Stage stage = stages.stream()
				.filter(s -> s.getStageNo() == stageNoInt)
				.findFirst().orElse(null);

		if (stage == null) {
			// Don't reveal how many stages exist
			message.setRejectValue("password", "invalid", "Invalid password.");
			log.info("Verificator: nonexisting stage '{}'.", userStage.getStageNo());
			return message;
		}

		String pass;
		if (stageNoInt == 1) {
			pass = stage.getPassword();
		} else {
			Stage stagePrev = stages.stream()
					.filter(s -> s.getStageNo() == stageNoInt - 1)
					.findFirst().orElse(null);
			if (stagePrev == null) {
				message.setRejectValue("password", "invalid", "Invalid password.");
				log.info("Verificator: nonexisting previous stage for '{}'.", userStage.getStageNo());
				return message;
			}
			pass = stagePrev.getPassword() + stage.getPassword();
		}

		if (!userStage.getPassword().equals(pass)) {
			message.setRejectValue("password", "invalid", "Invalid password.");
			log.info("Verificator: invalid password for '{}/{}'.",
					userStage.getCache(), userStage.getStageNo());
			return message;
		}

		log.info("Verificator: correct password for '{}/{}'.",
				userStage.getCache(), userStage.getStageNo());
		message.setMessage(stage.getMessage());

		String msgCoords = stage.getMessageCoords();
		if (msgCoords != null && !msgCoords.isEmpty()) {
			if ("+++".equals(msgCoords)) {
				// Coordinates come from the next stage — one extra read, unavoidable
				try {
					Stage stageNext = stageDao.findByStageNo(cache, stageNoInt + 1);
					message.setCoords(stageNext.getCoords());
				} catch (NoSuchElementException e) {
					log.warn("Next stage not found for coords lookup: cache={} stageNo={}", cache.getId(), stageNoInt + 1);
				}
			} else {
				message.setCoords(msgCoords);
			}
		}

		return message;
	}
}
