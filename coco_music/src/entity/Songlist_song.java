package entity;

public class Songlist_song {
	private int songlist_id;
	private int song_id;
	private String song_name;
	public int getSonglist_id() {
		return songlist_id;
	}
	public void setSonglist_id(int songlist_id) {
		this.songlist_id = songlist_id;
	}
	public int getSong_id() {
		return song_id;
	}
	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}
	public String getSong_name() {
		return song_name;
	}
	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}
}
