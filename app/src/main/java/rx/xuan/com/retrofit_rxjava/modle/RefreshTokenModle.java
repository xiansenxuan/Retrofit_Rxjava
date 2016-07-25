package rx.xuan.com.retrofit_rxjava.modle;

import java.util.HashMap;

public class RefreshTokenModle {
	public int code;
	public String message;
	public Modle data;
	public class Modle{
		public String token;
		public User user;
		public UserRights userRights;
	}
	public class User{
		public int factoryId;
		public String factoryName;
		public String userId;
		public String userName;
	}
	public class UserRights{
		public long rights;
		public HashMap<Long, String> rightsMap;
	}
	
}
