package org.musicbrainz.wsxml;

public abstract class Result {
    private int score;

    public int getScore() {
	return this.score;
    }

    public void setScore(int score) {
	this.score = score;
    }
}
