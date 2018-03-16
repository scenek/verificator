package cz.coccinelles.gc.verificator.web;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.datastore.Key;

import cz.coccinelles.gc.verificator.model.Cache;
import cz.coccinelles.gc.verificator.model.CacheValidator;

@Controller
public class CacheAdmController extends VerificatorAdmController {
	public static final String URL = "/cacheadm.go";

	private static final String FORM = "cacheadmform";
	private static final String MODEL = "cache";

	@RequestMapping(URL)
	public String list(Model model) {
		Collection<Cache> caches = cacheDao.list();
		model.addAttribute("caches", caches);
		return "cacheadm";
	}

	@ModelAttribute(MODEL)
	public Cache modelCache(@RequestParam(value="editCache", required=false) Key id) {
		return id != null ? cacheDao.get(id) : new Cache();
	}

	@RequestMapping(value=URL, method=RequestMethod.GET, params="editCache")
	public String edit(@RequestParam(value="editCache", required=false) Key id) {
		log.debug("Edit cache");
		return FORM;
	}

	@RequestMapping(value=URL, method=RequestMethod.POST)
	public String editCache(@ModelAttribute(MODEL) Cache cache, BindingResult result) {
		/* Adding new cache, is the GC code unique? */
		if (cache.getId() == null) {
			if (cacheDao.cacheExists(cache.getCode())) {
				result.rejectValue("code", "exists", "exists");
			}
		}

		/* Validate */
		if (!new CacheValidator().validate(cache, result)) {
			log.error("Invalid cache", cache);
			return FORM;
		}

		log.debug("Save cache", cache);
		cacheDao.save(cache);
		return "redirect:" + URL;
	}
}









