package pong.model;

import java.util.ArrayList;

public class Player {
	private String name;
	private Paddle paddle;
	private int score = 0;
	private ArrayList<GameItem> goals = new ArrayList<GameItem>();
	
	
	public void addGoal(GameItem goal){
		goals.add(goal);
	}
	
	public Player(String name, Paddle paddle) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
