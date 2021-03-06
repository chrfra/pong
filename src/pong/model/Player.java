package pong.model;

import static pong.model.Const.*;

import java.util.ArrayList;

public class Player {
	private String name;
	private Paddle paddle;
	private int score = DEFAULT_STARTING_SCORE;
	private int lives = DEFAULT_AMOUNT_PLAYER_LIVES;
	private ArrayList<GameItem> goals = new ArrayList<GameItem>();
	
	
	public Player(String name, Paddle paddle) {
		this.setName(name);
		this.paddle = paddle;
	}

	public String getName() {
		return name;
	}
	
	public void addGoal(GameItem goal){
		goals.add(goal);
	}

	public ArrayList<GameItem> getGoals() {
		return goals;
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

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public Paddle getPaddle() {
		return paddle;
	}

	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}
	
}
