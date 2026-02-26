package cz.coccinelles.gc.verificator.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Stage {

	@Id
	private Long id;

	/* cache parent key – Stage entities are stored as children of Cache */
	@Parent
	private Key<Cache> parent;

	/* title */
	private String title;

	/* coordinates */
	private String coords;

	/* waypoint identifier */
	@Index
	private String stageId;

	/* stage number */
	@Index
	private Integer stageNo;

	/* description */
	private String description;

	/* secret code */
	private String password;

	/* message for finder */
	private String message;

	/* coordinates for finder */
	private String messageCoords;

	public Stage() {
		super();
	}

	public Stage(Cache cache) {
		super();
		setCache(cache);
	}

	public Stage(String title) {
		super();
		this.title = title;
	}

	public Stage(Cache cache, String title, String coords, String stageId, Integer stageNo,
			String pass, String description, String message, String messageCoords) {
		super();
		setCache(cache);
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
		return id != null ? id.toString() : null;
	}

	public void setId(String id) {
		this.id = (id != null && !id.isEmpty()) ? Long.parseLong(id) : null;
	}

	/** Returns a minimal Cache proxy populated with just the ID from the parent key. */
	public Cache getCache() {
		if (parent == null) return null;
		Cache c = new Cache();
		c.setId(String.valueOf(parent.getId()));
		return c;
	}

	public void setCache(Cache cache) {
		this.parent = (cache != null && cache.getId() != null)
				? Key.create(Cache.class, Long.parseLong(cache.getId())) : null;
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
