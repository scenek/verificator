package cz.coccinelles.gc.verificator.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.util.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class UserStage {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	private String timestamp;
	private String ip;
	private String cache;
	private String stageNo;
	private String password;
	private String captcha;

	public UserStage() {
		super();
	}

	public UserStage(String cache, String stageNo, String password) {
		super();
		this.cache = cache.toUpperCase();
		this.stageNo = stageNo;
		this.password = password;
	}

	public String getId() {
		return id != null ? KeyFactory.keyToString(id) : null;
	}

	public void setId(String id) {
		this.id = StringUtils.hasText(id) ? KeyFactory.stringToKey(id) : null;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache.toUpperCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public void setStageNo(String stageNo) {
		this.stageNo = stageNo;
	}

	public String getStageNo() {
		return stageNo;
	}

	public String getTimeStamp() {
		return timestamp;
	}

	public void setTimeStamp(String time) {
		this.timestamp = time;
	}

	public void setTimeStamp() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.timestamp =  sdf.format(cal.getTime());
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
