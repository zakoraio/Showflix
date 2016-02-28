package com.showflix.app.dto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.showflix.app.dao.entity.Actors;
import com.showflix.app.dao.entity.Awards;
import com.showflix.app.dao.entity.Countries;
import com.showflix.app.dao.entity.Directors;
import com.showflix.app.dao.entity.Genere;
import com.showflix.app.dao.entity.Languages;
import com.showflix.app.dao.entity.ShowDetails;
import com.showflix.app.dao.entity.Writers;

public class Details {

	private String title;
	private String year;
	private String rated;
	private String released;
	private String runtime;
	private String genre;
	private String director;
	private String writer;
	private String actors;
	private String plot;
	private String language;
	private String country;
	private String awards;
	private String poster;
	private String metascore;
	private String imdbRating;
	private String imdbVotes;
	private String imdbID;
	private String type;
	private String showflixRating;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAwards() {
		return awards;
	}

	public void setAwards(String awards) {
		this.awards = awards;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getMetascore() {
		return metascore;
	}

	public void setMetascore(String metascore) {
		this.metascore = metascore;
	}

	public String getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getImdbVotes() {
		return imdbVotes;
	}

	public void setImdbVotes(String imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShowflixRating() {
		return showflixRating;
	}

	public void setShowflixRating(String showflixRating) {
		this.showflixRating = showflixRating;
	}

	public void fetch(ShowDetails details){
		title = details.getTitle();
		year = details.getYear();
		Calendar cal = Calendar.getInstance();
		Date date = new java.sql.Date(details.getReleased().getTime());
		cal.setTime(date);
		rated = details.getRated();
		released = cal.get(Calendar.MONTH) + " " + cal.get(Calendar.YEAR);
		runtime = details.getRuntime().toString();
		plot = details.getPlot();
		poster = details.getPoster();
		metascore = details.getMetascore().toString();
		imdbRating = details.getImdbRating().toString();
		imdbVotes = details.getImdbVotes().toString();
		imdbID = details.getImdbId();
		type = details.getType();
		showflixRating = details.getShowFlixRating().toString();
		genre = getConcatinatedString((List<Genere>)details.getGeneres(),Genere.class);
		actors = getConcatinatedString((List<Actors>)details.getActors(),Actors.class);
		director = getConcatinatedString((List<Directors>)details.getDirectors(),Directors.class);
		writer = getConcatinatedString((List<Writers>)details.getWriters(),Writers.class);
		awards = getConcatinatedString((List<Awards>)details.getAwards(),Awards.class);
		language = getConcatinatedString((List<Languages>)details.getLanguages(),Languages.class);
		country = getConcatinatedString((List<Countries>)details.getCountries(),Countries.class);
	}

	private <T> String getConcatinatedString(List<T> list, Class<T> type) {
		String s = "";
		Method m;
		try {
			m = type.getMethod("getName");
			int count = 0;
			for (T t : list) {
				if(count ==0){
				s += (String) m.invoke(t);
				count++;
				}
				else{
					s +=", " + (String) m.invoke(t);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}

		return s.trim();

	}
}
