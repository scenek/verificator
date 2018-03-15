package cz.coccinelles.gc.verificator.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.util.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class Cache {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;

	/* GC code, GC***** */
	private String code;

	/* nazev */
	private String title;

	/* popis */
	private String desc;

	/* url na cache */
	private String url;

	@OneToMany(mappedBy = "cache") //cascade = CascadeType.ALL, fetch = FetchType.LAZY, 	//@OrderBy("stageNo") 
	private List<Stage> stages; // = Collections.emptyList();

	public String getId() {
		return id != null ? KeyFactory.keyToString(id) : null;
	}

	public void setId(String id) {
		this.id = StringUtils.hasText(id) ? KeyFactory.stringToKey(id) : null;
	}
	
	public String getCode() {
		return code.toUpperCase();
	}

	public void setCode(String code) {
		this.code = code.toUpperCase();
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
