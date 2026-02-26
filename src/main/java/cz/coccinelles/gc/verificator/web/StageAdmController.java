package cz.coccinelles.gc.verificator.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cz.coccinelles.gc.verificator.model.Stage;
import cz.coccinelles.gc.verificator.model.StageValidator;

@Controller
public class StageAdmController extends VerificatorAdmController {
	public static final String URL = "/stageadm.go";

	private static final String FORM = "stageadmform";
	private static final String MODEL = "stage";

	@ModelAttribute(MODEL)
	public Stage modelStage(@RequestParam(value = "addStage", required = true) String id) {
		Stage stage = new Stage();
		stage.setCache(cacheDao.get(id));
		return stage;
	}

	@RequestMapping(value = URL, method = RequestMethod.GET, params = "addStage")
	public String editStage(@RequestParam(value = "addStage", required = false) String id) {
		log.debug("Add stage");
		return FORM;
	}

	@RequestMapping(value = URL, method = RequestMethod.POST, params = "addStage")
	public String editStage(@ModelAttribute(MODEL) Stage stage, BindingResult result) {
		if (!new StageValidator().validate(stage, result)) {
			log.error(result.toString());
			log.error("Invalid stage", stage);
			return FORM;
		}
		log.debug("Save stage", stage);
		stageDao.save(stage);
		return "redirect:/cacheadm.go";
	}
}
