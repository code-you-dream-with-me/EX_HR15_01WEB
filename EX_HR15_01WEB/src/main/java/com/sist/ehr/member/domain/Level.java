package com.sist.ehr.member.domain;

/**
 * 사용자 레벨은 : BASIC,SILVER,GOLD
 * @author sist  
 *
 */
public enum Level {  
	//BASIC(1),SILVER(2),GOLD(3);
	//현재값, nextLevel   
	GOLD(3,null),SILVER(2,GOLD),BASIC(1,SILVER) ;
	
	private final int value ;
	private final Level nextLevel;
	
	
	Level(int value,Level nextLevel){
		this.value = value;
		this.nextLevel = nextLevel;
	}

	public int getValue() {
		return value;
	}
	
	//다음 Level 추출
	public Level getNextLevel() {
		return nextLevel;
	}

	public static Level valueOf(int value) {
		switch(value) {
			case 1: return BASIC;
			case 2: return SILVER;
			case 3: return GOLD;
			default:
					throw new AssertionError("Unknown value:"+value);
		}
	}
	
}
