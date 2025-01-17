public class PlayerStatus {
	private String nickname;
	private int score;
	private int lives;
	private int health;
	private String weaponInHand;
	private double positionX;
	private double positionY;
	private static String gameName;


	public void initPlayer (String nickname) {
		this.nickname = nickname;
		this.health = 100;
	}

	public void initPlayer (String nickname, int lives) {
		initPlayer(nickname);
		this.lives = lives;
	}

	public void initPlayer (String nickname, int lives, int score) {
		initPlayer(nickname, lives);
		this.score = score;	

	}
	private boolean isPerfect (int artifactCode) {
		int sumDiv = 0;
		for (int i = 1; i <= artifactCode / 2; i++) {
			if (artifactCode % i == 0) {
				sumDiv += i;
			}
		}
		return sumDiv == artifactCode;
	}

	private boolean isPrime (int artifactCode) {
		if (artifactCode <= 1) {
			return false;
		}
		for (int i = 2; i <= artifactCode / 2; i++) {
			if (artifactCode % i == 0) {
				return false;
			}
		}
		return true;

	}

	private boolean isEven (int artifactCode) {
		int sumDigits = 0;
		if (artifactCode <= 1) {
			return false;
		}
		if (artifactCode % 2 != 0) {
			return false;
		}
		while (artifactCode != 0) {
			sumDigits += artifactCode % 10;
			artifactCode /= 10;
		}
		return sumDigits % 3 == 0;
	}

	public void findArtifact (int artifactCode) {
		if (isPerfect(artifactCode)) {
			this.score += 5000;
			this.lives += 1;
			this.health = 100;
		} else if (isPrime(artifactCode)) {
			this.score += 1000;
			this.lives += 2;
			if (this.health + 25 <= 100) {
				this.health += 25;
			} else {
				this.health = 100;
			}		
		} else if (isEven(artifactCode)) {
			this.score -= 3000;
			if (this.health - 25 <= 0) {
				this.lives--;
				if (this.lives <= 0) {
					System.out.println("Game Over");
				}
				this.health = 100;
			} else {
				this.health -= 25;
			}
		} else {
			this.score += artifactCode;
		}
		

	}

	public boolean setWeaponInHand (String weaponInHand) {
		if (weaponInHand.equals("knife")) {
			if (this.score >= 1000) {
				this.weaponInHand = weaponInHand;
				this.score -= 1000;
				return true;
			}
		}

		if (weaponInHand.equals("sniper")) {
			if (this.score >= 10000) {
				this.weaponInHand = weaponInHand;
				this.score -= 10000;
				return true;
			}	
		}

		if (weaponInHand.equals("kalashnikov")) {
			if (this.score >= 20000) {
				this.weaponInHand = weaponInHand;
				this.score -= 20000;
				return true;
			}
		}

		return false;
	}

	public String getWeaponInHand() {
		return this.weaponInHand;
	}

	public double getPositionX() {
		return this.positionX;
	}

	public double getPositionY() {
		return this.positionY;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public static String getGameName() {
		return PlayerStatus.gameName;
	}

	static void setGameName(String gameName) {
		PlayerStatus.gameName = gameName;
	}

	public void movePlayerTo(double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}


	public String getNickname() {
		return this.nickname;
	}

	public int getHealth() {
		return this.health;
	}

	public int getScore() {
		return this.score;
	}
	

	public void setHealth(int health) {
		this.health = health;
	}

	private double distance (PlayerStatus opponent) {
		return Math.sqrt(Math.pow((this.getPositionX() - opponent.getPositionX()), 2.0) + Math.pow((this.getPositionY() - opponent.getPositionY()), 2.0));
	}

	public boolean shouldAttackOpponent(PlayerStatus opponent) {
		if (this.getWeaponInHand() == opponent.getWeaponInHand()) {
			double playerProbability = (3.0 * this.getHealth() + this.getScore() / 1000) / 4;
			double opponentProbability = (3.0 * opponent.getHealth() + opponent.getScore() / 1000) / 4;
			if (playerProbability >= opponentProbability) {
				return true;
			}
		}

		if (this.getWeaponInHand() != opponent.getWeaponInHand()) {
			if (this.distance(opponent) > 1000) {
				if (this.getWeaponInHand().equals("sniper")) {
					return true;
				} else if (this.getWeaponInHand().equals("kalashnikov") && opponent.getWeaponInHand().equals("knife")) {
					return true;
				} else if (opponent.getWeaponInHand().equals("sniper")) {
					return false;
				}
			}
			
			if (this.distance(opponent) <= 1000) {
				if (this.getWeaponInHand().equals("kalashnikov")) {
					return true;
				} else if (this.getWeaponInHand().equals("sniper") && opponent.getWeaponInHand().equals("knife")) {
					return true;
				} else if (opponent.getWeaponInHand().equals("kalashnikov")) {
					return false;
				} 
			}
		}
		return false;
	}
}