package entity;

public class Songlist {
	private int songlist_id;
	private String name;
	private int songlist_PV;
	private String user_id;
	public int getSonglist_id() {
		return songlist_id;
	}
	public void setSonglist_id(int songlist_id) {
		this.songlist_id = songlist_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSonglist_PV() {
		return songlist_PV;
	}
	public void setSonglist_PV(int songlist_PV) {
		this.songlist_PV = songlist_PV;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
