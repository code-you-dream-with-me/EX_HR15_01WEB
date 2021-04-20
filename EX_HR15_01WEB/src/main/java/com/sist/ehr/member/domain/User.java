package com.sist.ehr.member.domain;

import com.sist.ehr.cmn.DTO;

public class User extends DTO {

	private String uId; //사용자 ID
	private String name;//이름
	private String passwd;//비번
	
	/**레벨:BASIC,SILVER,GOLD*/
	private Level  level;    
	/**로그인 회수 */
	private int     login;
	/**추천 회수 */   
    private int     recommend;	
    /**EMAIL */
    private String  email;
    
    /**등록일 */
    private String  regDt;    
    
    /**레벨 to int*/
    private int intLevel;
    
    
    
	public User() {}
	
	public User(String uId, String name, String passwd, Level level, int login, int recommend, String email,
			String regDt) {
		super();
		this.uId = uId;
		this.name = name;
		this.passwd = passwd;
		this.level = level;
		this.login = login;
		this.recommend = recommend;
		this.email = email;
		this.regDt = regDt;
		
		//레벨 to int
		intLevel = this.level.getValue();
	}







	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = intLevel;
		//int to Level
		this.level=Level.valueOf(intLevel);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
		
		//Level to int
		this.intLevel = this.level.getValue();
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	@Override
	public String toString() {
		return "User [uId=" + uId + ", name=" + name + ", passwd=" + passwd + ", level=" + level + ", login=" + login
				+ ", recommend=" + recommend + ", email=" + email + ", regDt=" + regDt + ", toString()="
				+ super.toString() + "]";
	}

	public void upgradeLevel() {
		Level nextLevel = this.level.getNextLevel();
		if(null == nextLevel) {
			throw new IllegalStateException(level+"은 업그레이드가 불가능 합니다.");
		}else {
			this.level = nextLevel;
			//Level to intLevel처리 
			setLevel(this.level);
		}
		
	}
	
		
	
}
