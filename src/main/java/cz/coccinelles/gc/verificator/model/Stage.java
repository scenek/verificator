package cz.coccinelles.gc.verificator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.util.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class Stage {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;

	@ManyToOne
	private Cache cache;

	/* nazev */
	private String title;

	/* popis */
	private String coords;

	/* pravdepodobne pro identifikaci wpt podle GC */
	private String stageId;

	/* cislo stage */
	private Integer stageNo;

	/* popis */
	private String description;

	/* tajny kod */
	private String password;

	/* zprava pro nalezce */
	private String message;

	/* souradnice pro nalezce */
	private String messageCoords;

	public Stage() {
		super();
	}

	public Stage(Cache cache) {
		super();
		this.cache = cache;
	}

	public Stage(String title) {
		super();
		this.title = title;
	}

	public Stage(Cache cache, String title, String coords, String stageId, Integer stageNo, String pass, String description, String message, String messageCoords) {
		super();
		this.cache = cache;
		this.title = title;
		this.coords = coords;
		this.stageId = stageId;
		this.stageNo = stageNo;
		this.password = pass;
		this.description = description;
		this.message = message;
		this.messageCoords = messageCoords;
	}

	public String getId() {
		return id != null ? KeyFactory.keyToString(id) : null;
	}

	public void setId(String id) {
		this.id = StringUtils.hasText(id) ? KeyFactory.stringToKey(id) : null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoords() {
		return coords;
	}

	public void setCoords(String coords) {
		this.coords = coords;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}

	public void setStageNo(Integer stageNo) {
		this.stageNo = stageNo;
	}

	public Integer getStageNo() {
		return stageNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageCoords() {
		return messageCoords;
	}

	public void setMessageCoords(String messageCoords) {
		this.messageCoords = messageCoords;
	}
}
