package cz.coccinelles.gc.verificator.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Cache {

	@Id
	private Long id;

	/* GC code, GC***** */
	@Index
	private String code;

	/* cache name */
	@Index
	private String title;

	/* description */
	private String desc;

	/* cache url */
	private String url;

	/* stages – not stored in Datastore, loaded separately by StageDao */
	private transient List<Stage> stages = new ArrayList<>();

	public Cache() {
		super();
	}

	public String getId() {
		return id != null ? id.toString() : null;
	}

	public void setId(String id) {
		this.id = (id != null && !id.isEmpty()) ? Long.parseLong(id) : null;
	}

	public String getCode() {
		return code != null ? code.toUpperCase() : null;
	}

	public void setCode(String code) {
		this.code = code != null ? code.toUpperCase() : null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

	public void addStage(Stage stage) {
		stages.add(stage);
	}

	public void addStages(List<Stage> stages) {
		for (Stage stage : stages) {
			addStage(stage);
			stage.setCache(this);
		}
	}

	@Override
	public String toString() {
		return this.getId();
	}
}
