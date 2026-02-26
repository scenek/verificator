package cz.coccinelles.gc.verificator.model;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

public class StageValidator {
	public boolean validate(Stage stage, BindingResult result) {
		if (!StringUtils.hasText(stage.getTitle()))
			result.rejectValue("title", "required", "required");
		if (!StringUtils.hasText(stage.getStageId()))
			result.rejectValue("stageId", "required", "required");
		return !result.hasErrors();
	}
}
