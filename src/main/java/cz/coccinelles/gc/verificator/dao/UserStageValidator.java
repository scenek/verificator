package cz.coccinelles.gc.verificator.dao;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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
	private final String stageRE = "^\\d{1,2}$";
	private final String passRE = "^[a-zA-Z0-9&]{1,20}$";

	protected final Logger log = LoggerFactory.getLogger(getClass());

	// @Override
	// public void validate(Object obj, Errors errors) {
	// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cache",
	// "cache.empty");
	// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stageNo",
	// "stageNo.empty");
	// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
	// "password.empty");
	// UserStage userStage = (UserStage) obj;
	// if (!userStage.getCache().matches(gcRE)) {
	// errors.rejectValue("cache", "Should be GCxxxxx.");
	// log.info("Verificator: invalid GC wpt.");
	// }
	// if (userStage.getStageNo() == null ||
	// !userStage.getStageNo().toString().matches(gcRE)) {
	// errors.rejectValue("stageNo", "debil");
	// log.info("Verificator: invalid stage.");
	// }
	// errors.rejectValue("stageNo", "debil");
	// }

	public ValidatorMessage validate(UserStage userStage, BindingResult result) {
		ValidatorMessage message = new ValidatorMessage(result);
		int stageNoInt;

		//
		// Validate user input
		//
		// Validate GC
		if (!userStage.getCache().matches(gcRE)) {
			message.setRejectValue("cache", "required", "Should be GCxxxxx.");
			log.info("Verificator: invalid GC wpt.");
		}

		// Validate stage
		if (userStage.getStageNo() == null
				|| !userStage.getStageNo().matches(stageRE)
				|| userStage.getStageNo().equals("0")) {
			message.setRejectValue("stageNo", "required",
					"Use numbers (1, 2...) only.");
			log.info("Verificator: invalid stage.");
		}
		
		// Validate password
		if (!userStage.getPassword().matches(passRE)) {
			message.setRejectValue("password", "required",
					"Use numbers, chars and few extra chars only.");
			log.info("Verificator: invalid password.");
		}

		// Exit early
		if (message.getResult().hasErrors())
			return message;

		//
		// Verify against DB
		//
		// Verify cache
		Cache cache;
		try {
			cache = cacheDao.findByCode(userStage.getCache());
		} catch (NoResultException ex) {
			message.setRejectValue("cache", "required",
					"Invalid cache. Please try again.");
			log.info("Verificator: nonexisting cache '" + userStage.getCache()
					+ "'.");
			return message;
		}

		// Verify stage
		stageNoInt = Integer.parseInt(userStage.getStageNo());
		Stage stage;
		String pass;
		try {
			stage = stageDao.findByStageNo(cache, stageNoInt);
			if (stageNoInt != 1) {
				Stage stagePrev = stageDao.findByStageNo(cache,
						stageNoInt-1);
				pass = stagePrev.getPassword() + stage.getPassword();
			} else {
				pass = stage.getPassword();
			}
		} catch (Exception e) {
			// Don't want to let know how many stages are on this cache
			message.setRejectValue("password", "invalid", "Invalid password.");
			log.info("Verificator: nonexisting stage '"
					+ userStage.getStageNo() + "'.");
			return message;
		}

		// Verify password
		if (userStage.getPassword().equals(pass)) {
			log.info("Verificator: correct password '"
					+ userStage.getPassword() + "' for '"
					+ userStage.getCache() + "/" + userStage.getStageNo()
					+ "'.");
			message.setMessage(stage.getMessage());
			// souradnice zpravy
			if (stage.getMessageCoords() != null && !stage.getMessageCoords().isEmpty())
				message.setCoords(stage.getMessageCoords());
			// souradnice dalsi stage
			if (stage.getMessageCoords().equals("+++")) {
				Stage stageNext = stageDao.findByStageNo(cache,
						stageNoInt+1);
				message.setCoords(stageNext.getCoords());
			}
			// jinak prazdne souradnice
			return message;
		}

		message.setRejectValue("password", "invalid", "Invalid password.");
		log.info("Verificator: invalid password '" + userStage.getPassword()
				+ "'.");
		return message;
	}
}
