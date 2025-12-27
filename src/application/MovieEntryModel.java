package application;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class MovieEntryModel {
    private String id;
    private String title;
    private String actors;
    private String poster;
    private short year;
}
