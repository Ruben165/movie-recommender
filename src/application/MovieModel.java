package application;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class MovieModel {
	private String id;
	private String title;
	private short releaseYear;
	private String mainActors;
	private String posterUrl;
	private String type;
	private String synopsis;
	private String contentRating;
}
