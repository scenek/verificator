package cz.coccinelles.gc.verificator.web;

//import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.datastore.Key;

import cz.coccinelles.gc.verificator.model.Stage;
import cz.coccinelles.gc.verificator.model.StageValidator;

@Controller
public class StageAdmController extends VerificatorAdmController {
	public static final String URL = "/stageadm.go";
	
	private static final String FORM = "stageadmform";
	//private static final String LIST = "stageadm";
	private static final String MODEL = "stage";
	
/*	@RequestMapping(URL)
	public String redir() {
		return "redirect:" + "/cacheadm.go";
	}*/
	
/*	@RequestMapping(URL)
	public String listStages(Model model, @RequestParam(value="cache", required=true) String cache) {
		Collection<Stage> stages = cacheDao.get(cache).getStages();
		model.addAttribute("stages", stages);
		// TODO: dodělat seznam stages
		return LIST;
	}*/
	
	@ModelAttribute(MODEL)
	public Stage modelStage(@RequestParam(value="addStage", required=true) Key id) {
		Stage stage = new Stage();
		stage.setCache(cacheDao.get(id));
		return stage;
		//return id != null ? stageDao.get(id) : new Stage();
	}
	
	@RequestMapping(value=URL, method=RequestMethod.GET, params="addStage")
	public String editStage(@RequestParam(value="addStage", required=false) Key id) {
		log.debug("Add stage");
		return FORM;
	}
	
	@RequestMapping(value=URL, method=RequestMethod.POST, params="addStage")
	public String editStage(@ModelAttribute(MODEL) Stage stage, BindingResult result) {
		/* TODO Validace stage */
		if (!new StageValidator().validate(stage, result)) {
			log.error(result.toString());
		    log.error("Invalid stage", stage);
			return FORM;
		}
		log.debug("Save stage", stage);
		stageDao.save(stage);
		/* TODO: přesměrování na seznam stage dané keše */
		/* return "redirect:" + URL + "?cache=" + stage.getCache(); */
		return "redirect:/cacheadm.go";
	}
}
