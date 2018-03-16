package cz.coccinelles.gc.verificator.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONObject;


@Controller
public class VerificatorController extends VerificatorAdmController {
	public static final String URL = "/verificator";
	private static final String FORM = "verificator";
	private static final String MODEL = "verificator";
	private static final String CAPTCHA = "ValidCaptcha";
	private static final String MSG = "message";
	private static final String RECAPTCHASITEKEYNAME = "recaptchasitekey";
	private static final String RECAPTCHASITEKEY = "6LeQ0UwUAAAAAJoisaMiSrkidp4P4qqIoMWd3-VF";
	private static final String RECAPTCHASECRETKEY = "6LeQ0UwUAAAAAOSlK5flrNkgtnorI-LNMY59-q68";
	private static final String CACHE = "cacheCode";

	@Autowired
	UserStageValidator validator;

	@RequestMapping(URL)
	public String list(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(true);
		UserStage stage = new UserStage();
		model.addAttribute(MODEL, stage);
		model.addAttribute(RECAPTCHASITEKEYNAME, RECAPTCHASITEKEY);
		return "verificator";
	}

	/**
	 * Validates Google reCAPTCHA V2 or Invisible reCAPTCHA.
	 * @param secretKey Secret key (key given for communication between your site and Google)
	 * @param response reCAPTCHA response from client side. (g-recaptcha-response)
	 * @param remoteIp IP of remote user
	 * @return true if validation successful, false otherwise.
	 */
	private static boolean isCaptchaValid(String secretKey, String response, String remoteIp) {
		try {
			String url = "https://www.google.com/recaptcha/api/siteverify?"
				+ "secret=" + secretKey
				+ "&response=" + response
				+ "&remoteip=" + remoteIp;
			InputStream res = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));

			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			String jsonText = sb.toString();
			res.close();

			JSONObject json = new JSONObject(jsonText);
			return json.getBoolean("success");
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
		model.addAttribute(CACHE, stage.getCache());

		String remoteAddr = req.getRemoteAddr();
		HttpSession session = req.getSession();

		if (!isCaptchaValid(RECAPTCHASECRETKEY, recaptchaResponse, remoteAddr)) {
			result.rejectValue("captcha", "required",
				"Invalid captcha. Please try again.");
			log.debug("Invalid captcha.");
			return FORM;
		}

		stage.setIp(remoteAddr);
		stage.setTimeStamp();
		userStageDao.save(stage);

		/* Validate */
		ValidatorMessage message = validator.validate(stage, result);
		if (message.getResult().hasErrors()) {
			log.debug("Invalid user stage.");
			return FORM;
		}

		/* Show message */
		String publicMessage;
		publicMessage = "<h2> Stage" + stage.getStageNo() + "</h2>\n" + "<b>" + message.getMessage() + "</b>\n";
		if (message.getCoords() != null)
			publicMessage = publicMessage + "<h3>" + message.getCoords()
					+ "</h3>\n";

		model.addAttribute(MSG, publicMessage);
		return MSG;
	}
}
