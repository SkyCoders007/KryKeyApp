package com.mxi.krykeyapp.bean;




public class FindStationBy implements Comparable <FindStationBy>{

	public String id,freq,radio,genre,city,rtmp_url,rtmpmic;
	
	public String getRtmpmic() {
		return rtmpmic;
	}

	public void setRtmpmic(String rtmpmic) {
		this.rtmpmic = rtmpmic;
	}

	public String getRtmp_url() {
		return rtmp_url;
	}

	public void setRtmp_url(String rtmp_url) {
		this.rtmp_url = rtmp_url;
	}

	public int ranking;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	@Override
	public int compareTo(FindStationBy another) {
		// TODO Auto-generated method stub
		int compareQuantity = ((FindStationBy) another).getRanking(); 
		 
		//ascending order
		return  compareQuantity - this.ranking;
	}

	
}
