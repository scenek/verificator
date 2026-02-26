package cz.coccinelles.gc.verificator.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cz.coccinelles.gc.verificator.dao.UserStageValidator;
import cz.coccinelles.gc.verificator.model.UserStage;
import cz.coccinelles.gc.verificator.model.ValidatorMessage;

@Controller
public class VerificatorController extends VerificatorAdmController {
	public static final String URL = "/verificator";
	private static final String FORM = "verificator";
	private static final String MODEL = "verificator";
	private static final String MSG = "message";
	private static final String RECAPTCHASITEKEYNAME = "recaptchasitekey";

	// Keys are configured in appengine-web.xml system-properties so they are
	// not committed to source control.
	private static final String RECAPTCHASITEKEY =
			System.getProperty("recaptcha.sitekey", "");
	private static final String RECAPTCHASECRETKEY =
			System.getProperty("recaptcha.secretkey", "");

	@Autowired
	UserStageValidator validator;

	@RequestMapping(URL)
	public String list(HttpServletRequest req, Model model) {
		UserStage stage = new UserStage();
		model.addAttribute(MODEL, stage);
		model.addAttribute(RECAPTCHASITEKEYNAME, RECAPTCHASITEKEY);
		return "verificator";
	}

	/**
	 * Validates Google reCAPTCHA V2 or Invisible reCAPTCHA.
	 */
	private static boolean isCaptchaValid(String secretKey, String response, String remoteIp) {
		try {
			String url = "https://www.google.com/recaptcha/api/siteverify?"
					+ "secret=" + secretKey
					+ "&response=" + response
					+ "&remoteip=" + remoteIp;
			try (BufferedReader rd = new BufferedReader(
					new InputStreamReader(new URL(url).openStream(), StandardCharsets.UTF_8))) {
				StringBuilder sb = new StringBuilder();
				int cp;
				while ((cp = rd.read()) != -1) {
					sb.append((char) cp);
				}
				JSONObject json = new JSONObject(sb.toString());
				return json.getBoolean("success");
			}
		} catch (Exception e) {
			return false;
		}
	}

	@RequestMapping(value = URL, method = RequestMethod.POST)
	public String verify(HttpServletRequest req,
			@RequestParam("g-recaptcha-response") String recaptchaResponse,
			@ModelAttribute(MODEL) UserStage stage, BindingResult result,
			Model model) {

		model.addAttribute(RECAPTCHASITEKEYNAME, RECAPTCHASITEKEY);

		String remoteAddr = req.getRemoteAddr();

		if (!isCaptchaValid(RECAPTCHASECRETKEY, recaptchaResponse, remoteAddr)) {
			result.rejectValue("captcha", "required", "Invalid captcha. Please try again.");
			log.debug("Invalid captcha.");
			return FORM;
		}

		stage.setIp(remoteAddr);
		stage.setTimeStamp();
		userStageDao.save(stage);

		ValidatorMessage message = validator.validate(stage, result);
		if (message.getResult().hasErrors()) {
			log.debug("Invalid user stage.");
			return FORM;
		}

		String publicMessage = "<h2> Stage" + stage.getStageNo() + "</h2>\n"
				+ "<b>" + message.getMessage() + "</b>\n";
		if (message.getCoords() != null)
			publicMessage = publicMessage + "<h3>" + message.getCoords() + "</h3>\n";

		model.addAttribute(MSG, publicMessage);
		return MSG;
	}
}
