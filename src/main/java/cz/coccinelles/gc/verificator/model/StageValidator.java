package cz.coccinelles.gc.verificator.model;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import cz.coccinelles.gc.verificator.dao.StageDao;

public class StageValidator {
	public boolean validate(Stage stage, BindingResult result) {
		/* TODO: doplnit validaci stage */
		if (!StringUtils.hasText(stage.getTitle()))
			result.rejectValue("title", "required", "required");
		if (!StringUtils.hasText(stage.getStageId()))
			result.rejectValue("stageId", "required", "required");
		return !result.hasErrors(); 
	}
	
	public boolean verify(Stage stage, StageDao stageDao, BindingResult result) {
		/* TODO: doplnit verifikaci uživatelského vstupu */
		return !result.hasErrors();
	}
}
