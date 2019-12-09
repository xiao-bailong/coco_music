package entity;

import annotation.Column;

public class Search_history {
    private int id;
    @Column(name="history")
    private String history;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
