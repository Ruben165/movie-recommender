package application;

public class Movie {
	private String id;
	private String title;
	private String releaseYear;
	private String mainActors;
	private String posterUrl;
	private String type;
	private String synopsis;
	private String contentRating;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getReleaseYear() {
		return releaseYear;
	}
	
	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}
	
	public String getMainActors() {
		return mainActors;
	}
	
	public void setMainActors(String mainActors) {
		this.mainActors = mainActors;
	}
	
	public String getPosterUrl() {
		return posterUrl;
	}
	
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSynopsis() {
		return synopsis;
	}
	
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
	public String getContentRating() {
		return contentRating;
	}
	
	public void setContentRating(String contentRating) {
		this.contentRating = contentRating;
	}
	
	public Movie(String id, String title, String releaseYear, String mainActors, String posterUrl, String type,
			String synopsis, String contentRating) {
		super();
		this.id = id;
		this.title = title;
		this.releaseYear = releaseYear;
		this.mainActors = mainActors;
		this.posterUrl = posterUrl;
		this.type = type;
		this.synopsis = synopsis;
		this.contentRating = contentRating;
	}
}
