package com.showflix.app.dao.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.showflix.app.dto.Details;

@Entity
public class ShowDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ShowId")
	private int id;
	private String title;
	private String year;
	private String rated;
	private Date released;
	private Integer runtime;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "Show_Generes", joinColumns = @JoinColumn(name = "ShowId") , inverseJoinColumns = @JoinColumn(name = "GenereId") )
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = @Column(name = "ShowGenereID") , type = @Type(type = "long") , generator = "hilo-gen")
	private Collection<Genere> generes;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "Show_Directors", joinColumns = @JoinColumn(name = "ShowId") , inverseJoinColumns = @JoinColumn(name = "DirectorId") )
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = @Column(name = "ShowDirectorID") , type = @Type(type = "long") , generator = "hilo-gen")
	private Collection<Directors> directors;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "Show_Writers", joinColumns = @JoinColumn(name = "ShowId") , inverseJoinColumns = @JoinColumn(name = "WriterId") )
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = @Column(name = "ShowWriterID") , type = @Type(type = "long") , generator = "hilo-gen")
	private Collection<Writers> writers;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "Show_Actors", joinColumns = @JoinColumn(name = "ShowId") , inverseJoinColumns = @JoinColumn(name = "ActorId") )
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = @Column(name = "ShowActorID") , type = @Type(type = "long") , generator = "hilo-gen")
	private Collection<Actors> actors;
	@Column(length = 1000)
	private String plot;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "Show_Languages", joinColumns = @JoinColumn(name = "ShowId") , inverseJoinColumns = @JoinColumn(name = "LanguageId") )
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = @Column(name = "ShowLanguageID") , type = @Type(type = "long") , generator = "hilo-gen")
	private Collection<Languages> languages;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "Show_Countries", joinColumns = @JoinColumn(name = "ShowId") , inverseJoinColumns = @JoinColumn(name = "CountriesId") )
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = @Column(name = "ShowCountrieID") , type = @Type(type = "long") , generator = "hilo-gen")
	private Collection<Countries> countries;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "Show_Awards", joinColumns = @JoinColumn(name = "ShowId") , inverseJoinColumns = @JoinColumn(name = "AwardId") )
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = @Column(name = "ShowAwardID") , type = @Type(type = "long") , generator = "hilo-gen")
	private Collection<Awards> awards;
	private String poster;
	private Integer metascore;
	private Double imdbRating;
	private Long imdbVotes;
	private String imdbId;
	private String type;
	private Double showFlixRating;

	public void fetch(Details details) throws ParseException {
		title = details.getTitle();
		year = details.getYear();
		SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
		String[] dateComponents = details.getReleased().split(" ");
		Date parsed = null;
		if(dateComponents.length==3){
		 parsed = format.parse(dateComponents[0] + " " + months.get(dateComponents[1]) + " " + dateComponents[2]);
		}
		else{
			parsed = format.parse("1" + " " + months.get(dateComponents[0]) + " " + dateComponents[1]);
		}
		java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
		rated = details.getRated();
		released = sqlDate;
		try {
			runtime = Integer.parseInt(details.getRuntime().split(" ")[0].trim());
		} catch (NumberFormatException nfe) {
			runtime = 0;
		}

		plot = details.getPlot();

		poster = details.getPoster();
		try {
			metascore = Integer.parseInt(details.getMetascore().trim());
		} catch (NumberFormatException nfe) {
			metascore = 0;
		}
		try {
			imdbRating = Double.parseDouble(details.getImdbRating().trim());
		} catch (NumberFormatException nfe) {
			imdbRating = 0.0;
		}
		try {
			imdbVotes = Long.parseLong(details.getImdbVotes().replace(",", "").trim());
		} catch (NumberFormatException nfe) {
			imdbVotes = 0L;
		}
		imdbId = details.getImdbID();
		type = details.getType();
		try {
			showFlixRating = Double.parseDouble(details.getShowflixRating().replace(",", "").trim());
		} catch (NumberFormatException nfe) {
			showFlixRating = 0.0;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Collection<Genere> getGeneres() {
		return generes;
	}

	public void setGeneres(Collection<Genere> generes) {
		this.generes = generes;
	}

	public Collection<Directors> getDirectors() {
		return directors;
	}

	public void setDirectors(Collection<Directors> directors) {
		this.directors = directors;
	}

	public Collection<Writers> getWriters() {
		return writers;
	}

	public void setWriters(Collection<Writers> writers) {
		this.writers = writers;
	}

	public Collection<Actors> getActors() {
		return actors;
	}

	public void setActors(Collection<Actors> actors) {
		this.actors = actors;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public Collection<Languages> getLanguages() {
		return languages;
	}

	public void setLanguages(Collection<Languages> languages) {
		this.languages = languages;
	}

	public Collection<Countries> getCountries() {
		return countries;
	}

	public void setCountries(Collection<Countries> countries) {
		this.countries = countries;
	}

	public Collection<Awards> getAwards() {
		return awards;
	}

	public void setAwards(Collection<Awards> awards) {
		this.awards = awards;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Date getReleased() {
		return released;
	}

	public void setReleased(Date released) {
		this.released = released;
	}

	public Integer getRuntime() {
		return runtime;
	}

	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}

	public Integer getMetascore() {
		return metascore;
	}

	public void setMetascore(Integer metascore) {
		this.metascore = metascore;
	}

	public Double getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(Double imdbRating) {
		this.imdbRating = imdbRating;
	}

	public Long getImdbVotes() {
		return imdbVotes;
	}

	public void setImdbVotes(Long imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public static Map<String, String> getMonths() {
		return months;
	}

	public static void setMonths(Map<String, String> months) {
		ShowDetails.months = months;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ShowDetails() {
		generes = new ArrayList<Genere>();
		directors = new ArrayList<Directors>();
		writers = new ArrayList<Writers>();
		actors = new ArrayList<Actors>();
		languages = new ArrayList<Languages>();
		countries = new ArrayList<Countries>();
		awards = new ArrayList<Awards>();

	}

	static Map<String, String> months;

	static {
		months = new HashMap<String, String>();
		months.put("Jan", "01");
		months.put("Feb", "02");
		months.put("Mar", "03");
		months.put("Apr", "04");
		months.put("May", "05");
		months.put("Jun", "06");
		months.put("Jul", "07");
		months.put("Aug", "08");
		months.put("Sep", "09");
		months.put("Oct", "10");
		months.put("Nov", "11");
		months.put("Dec", "12");
	}

	public int hashCode() {
		return title.hashCode() ^ Integer.valueOf(id).hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof ShowDetails) {
			if (((ShowDetails) o).getImdbId().equals(imdbId)) {
				return true;
			}
		}
		return false;
	}

	public Double getShowFlixRating() {
		return showFlixRating;
	}

	public void setShowFlixRating(Double showFlixRating) {
		this.showFlixRating = showFlixRating;
	}

}