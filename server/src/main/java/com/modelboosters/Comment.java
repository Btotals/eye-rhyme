package com.modelboosters;

public class Comment {
	/*@Autowired
	UserRepository userRepository;*/
	
	private Integer comment_id;
	private Integer user_id;
	private String date_time;
	private String content;
	private Integer like_num;
	
	public Comment(Integer comment_id, Integer user_id, String date_time, String content, Integer like_num) {
		super();
		this.comment_id = comment_id;
		this.user_id = user_id;
		this.date_time = date_time;
		this.content = content;
		this.like_num = like_num;
	}
	
	public Integer getComment_id() {
		return comment_id;
	}
	public void setComment_id(Integer comment_id) {
		this.comment_id = comment_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getDate_time() {
		return date_time;
	}
	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getLike_num() {
		return like_num;
	}
	public void setLike_num(Integer like_num) {
		this.like_num = like_num;
	}
	/*public String toJsonString() {
		Map<String, String> tmap = new HashMap<String, String>();
		tmap.put(Keyword.COMMENT_ID, comment_id.toString());
        tmap.put(Keyword.USER_ID, user_id.toString());
        User user = userRepository.findUserById(user_id);
        tmap.put(Keyword.RANK, user.getRank().toString());
        tmap.put(Keyword.GENDER, user.getGender().toString());
        tmap.put(Keyword.NAME, user.getName());
        tmap.put(Keyword.CONTENT, content);
        tmap.put(Keyword.LIKE_NUM, like_num.toString());
        tmap.put(Keyword.DATE_TIME, date_time);
        tmap.put(Keyword.CONTENT, content);
        tmap.put(Keyword.LIKE_NUM, like_num.toString());
        return JSONObject.fromObject(tmap).toString();
	}*/
	
}
