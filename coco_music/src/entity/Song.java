package entity;

public class Song {
	private int song_id;
	private String name;
	private int singer_id;
	private int song_PV;
	private String address;
	public int getSong_id() {
		return song_id;
	}
	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSinger_id() {
		return singer_id;
	}
	public void setSinger_id(int singer_id) {
		this.singer_id = singer_id;
	}
	public int getSong_PV() {
		return song_PV;
	}
	public void setSong_PV(int song_PV) {
		this.song_PV = song_PV;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
