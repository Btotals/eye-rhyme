package com;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.jdbc.core.JdbcTemplate;

import com.models.Account;
import com.models.Comment;
import com.models.MovieActivity;
import com.models.MovieArticle;
import com.models.MovieDescription;
import com.models.MovieGrade;
import com.models.MovieProduct;
import com.models.MovieTicket;
import com.models.Theater;
import com.models.TheaterGrade;
import com.models.User;
import com.repositories.AccountRepository;
import com.repositories.MovieActivityRepository;
import com.repositories.MovieArticleRepository;
import com.repositories.MovieDescriptionRepository;
import com.repositories.MovieProductRepository;
import com.repositories.MovieTicketRepository;
import com.repositories.TheaterRepository;
import com.repositories.UserRepository;
import com.utils.Values;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	@Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MovieDescriptionRepository movieDescriptionRepository;
    
    @Autowired
    private TheaterRepository theaterRepository;
    
    @Autowired
    private MovieProductRepository movieProductRepository;
    
    @Autowired
    private MovieTicketRepository movieTicketRepository;
    
    @Autowired
    private MovieActivityRepository movieActivityRepository;
    
    @Autowired
    private MovieArticleRepository movieArticleRepository;
	
    @Autowired
    JdbcTemplate jdbcTemplate;
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
    
    public void run(String... strings) throws Exception {
    	try {
    		createUser();
    		createMovieDescription(25);
    		createTheaters();
    		createMovieProducts();
    		createMovieTicket();
    		createMovieActivity();
    		joinActivity();
    		createMovieArticle();
    		createComment();
    		joinActivity();
    		createMovieGrade();
    		createTheaterGrade();
    		// learnJDBC();
    		// createMovieProducts();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void learnJDBC() throws Exception {
/*    	jdbcTemplate.execute("DROP TABLE IF EXISTS customers");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");*/
    	jdbcTemplate.execute("delete from join_activity where user_id = 1 and activity_id = 1");
    }
    
    public void createComment() throws Exception {
    	MovieArticle movieArticle = movieArticleRepository.findMovieArticleById(1);
    	User user = userRepository.findOne(2);
    	movieArticle.getComments().add(new Comment(user, "Oh, my God.."));
    	movieArticle.getComments().add(new Comment(user, "Oh, Shit.."));
    	movieArticleRepository.save(movieArticle);
    }
    
    public void createMovieArticle() throws Exception {
    	User user = userRepository.findUserById(2);
		MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(1);
		String title = "Life is tough";
		String content = "Life is half spent before we know what it is.";
		String contentPic = "a.png";
    	movieArticleRepository.save(new MovieArticle(user, movieDescription, title, content, contentPic));
    }
    
    public void createUser() throws Exception {
    	String name = "thomas young";
		Account account = accountRepository.save(new Account("13246817678", "1234"));
    	Integer id = account.getId();
    	userRepository.save(new User(id, name, account.getPhone()));
    	
    	String name1 = "young lee";
		Account account1 = accountRepository.save(new Account("13246817677", "1234"));
    	Integer id1 = account1.getId();
    	userRepository.save(new User(id1, name1, account1.getPhone()));
    }
    
    public void createMovieDescription(int n) throws Exception {
    	for (int i = 1; i <= n; i++) {
	    	String name = "Captain America: Civil War " + String.valueOf(i);
			Integer type = i%5 + 1;
			Integer duration = 120 + i;
			String directors = "乔·卢素   安东尼·卢素";
			directors = new String(directors.getBytes(), "utf-8");
			String actors = "克里斯·埃文斯/小罗伯特·唐尼";
			actors = new String(actors.getBytes(), "utf-8");
			String description = "5月6日，由美国漫威影业公司出品的超级英雄大片《美国队长3》将登陆中国约290家IMAX影院。作为《复仇者联盟2》之后最强阵容的漫威超级英雄集结，再加上火热的映前造势和首轮提前场爆出的超高口碑都让《美国队长3》成为整个5月最引人关注的IMAX超级大片。";
			description = new String(description.getBytes(), "utf-8");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date releaseDate = sdf.parse(String.format("2016-05-%d", i));
			
			movieDescriptionRepository.save(new MovieDescription(name, type, releaseDate, duration, 
					directors, actors, description));
    	}
    }
    
    public void createTheaters() throws Exception {
    	String [][]locations = {
	    	{"23.064107","113.453293"},
	    	{"23.053333","113.437914"},
	    	{"23.148006","113.364468"},
	    	{"23.169537","113.354120"},
	    	{"23.117564","113.471690"},
	    	{"23.165151","113.463785"},
	    	{"22.980954","113.317325"},
	    	{"23.006504","113.286567"},
	    	{"22.969109","113.212475"},
	    	{"22.668504","113.637481"},
	    	{"22.989072","113.340968"}
    	};
    	for (int i = 1; i <= 11; i++) {
	    	String name = "广州飞扬影城-正佳店" + String.valueOf(i);
			String location = "天河区天河路228号正佳广场7"+ String.valueOf(i) + "楼";
			String contact = "6067096" + String.valueOf(i);
			String description = "九个豪华影厅的五星级飞扬影城，在此时一定能满足众多影迷的需求。5厅全新升级为天幕厅：全广州首家杜比全景声、中国巨幕。广受白领、情侣欢迎的飞扬影城在2005和2006年度凭着优异的票房成绩，坐上了中国第一影城的宝座。顶天立地的大屏幕，真正实现空中飘飘欲仙看电影的神奇感受，体验国际最先进的杜比SRD和DTS数码环音系统，比利时Baroo卫星数码电影带给您惊心动魄的真正大片神奇之旅。五星级的电影配套设计融合飞扬智慧的创造，使您以最好的方式看电影！";
			BigDecimal longitude = new BigDecimal(locations[i - 1][1]);
			BigDecimal latitude = new BigDecimal(locations[i - 1][0]);
			theaterRepository.save(new Theater(name, location, contact, description, longitude, latitude));
    	}
    }
    
    public void createMovieProducts() throws Exception {
    	Theater theater = theaterRepository.findOne(1);
    	MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(25);
		for (int j = 1; j <= 5; j++) {
	    	for (int i = 15; i <= 31; i++) {
				Integer type = j;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(String.format("2016-05-%d", i));
				Integer round = j;
				Integer hall = j;
				Integer price = (30 + i);
				Integer discount = i;
				MovieProduct movieProduct = new MovieProduct(movieDescription, theater, type, date, round, hall, price, discount);
				movieProductRepository.save(movieProduct);
			}
		}
    }
    
    public void createMovieDescriptionAndCreateRelativeProducts(Integer movie_id) throws Exception {
    	int ExpireTime = 3600*24*1000;
    	int TotalDay = 30*3;
    	Long today = new Date().getTime();
    	Integer price = 30 + (int)(Math.random()*30);
    	Integer discount = 5 + (int)(Math.random()*10);
    	MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(movie_id);
    	// get all theaters and create relative products
    	ArrayList<Theater> theaters = (ArrayList<Theater>) theaterRepository.findAll();
    	
    	for (Theater theater: theaters) {
    		// round and hall..
    		for (int j = 1; j <= 5; j++) {
    	    	for (int i = 0; i <= TotalDay; i++) {
    	    		Date date = new Date(today + i*ExpireTime);
    	    		Integer round = j;
    				Integer hall = j;
    				// type is between 1 and 3.
    				Integer type = 1 + (int)(Math.random()*3);
    				MovieProduct movieProduct = new MovieProduct(movieDescription, theater, type, date, round, hall, price, discount);
    				movieProductRepository.save(movieProduct);
    	    	}
    		}
    	}
    }
    
    public void createTheaterAndCreateRelativeProducts(Integer theater_id) throws Exception {
    	int ExpireTime = 3600*24*1000;
    	int TotalDay = 30*3;
    	Long today = new Date().getTime();
    	Integer price = 30 + (int)(Math.random()*30);
    	Integer discount = 5 + (int)(Math.random()*10);
    	Theater theater = theaterRepository.findOne(theater_id);
    	// get all movies and create relative products
    	ArrayList<MovieDescription> movieDescriptions = (ArrayList<MovieDescription>) movieDescriptionRepository.findAll();
    	for (MovieDescription movieDescription: movieDescriptions) {
    		// round and hall..
    		for (int j = 1; j <= 5; j++) {
    	    	for (int i = 0; i <= TotalDay; i++) {
    	    		Date date = new Date(today + i*ExpireTime);
    	    		Integer round = j;
    				Integer hall = j;
    				// type is between 1 and 3.
    				Integer type = 1 + (int)(Math.random()*3);
    				MovieProduct movieProduct = new MovieProduct(movieDescription, theater, type, date, round, hall, price, discount);
    				movieProductRepository.save(movieProduct);
    	    	}
    		}
    	}
    }
    
    
    public void createMovieGrade() throws Exception {
    	User []user = {userRepository.findUserById(1), userRepository.findUserById(2)};
    	Random random = new Random();
    	for (int j = 0; j < 2; j++) {
	    	for (int i = 1; i < 12; i++) {
				MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(i);
				Integer value = random.nextInt(5);
				String content = "i love it.";
				MovieGrade movieGrade = new MovieGrade(user[j], value, content);
				
				movieDescription.getGrades().add(movieGrade);
				movieDescriptionRepository.save(movieDescription);
	    	}
    	}
    }
    
    public void createTheaterGrade() throws Exception {
    	User []user = {userRepository.findUserById(1), userRepository.findUserById(2)};
    	Random random = new Random();
    	for (int j = 0; j < 2; j++) {
	    	for (int i = 1; i < 12; i++) {
				Theater theater = theaterRepository.findTheaterById(i);
				Integer value = random.nextInt(5);
				String content = "i love it.";
				TheaterGrade theaterGrade = new TheaterGrade(user[j], value, content);
				theater.getGrades().add(theaterGrade);
				theaterRepository.save(theater);
	    	}
    	}
    }
    
    public void createMovieTicket() throws Exception {
    	MovieProduct movieProduct = movieProductRepository.findMovieProductById(1);
		for (int i=10; i <= 20; i++) {
			movieProduct.getSeats().add(i);
			User user = userRepository.findOne(i%2 + 1);
			Integer type = i%5 + 1;
			Integer status = Values.UNPAID;
			MovieTicket movieTicket = new MovieTicket(user, movieProduct, i, type, status, "");
			movieTicketRepository.save(movieTicket);
		}
		
		movieProductRepository.save(movieProduct);
    }
    
    public void createMovieActivity() throws Exception {
    	User user = userRepository.findOne(1);
		MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTime = sdf.parse("2016-04-30");
		BigDecimal longitude = new BigDecimal("123.2343242");
		BigDecimal latitude = new BigDecimal("23.7777773");
		String place = "Broad way";
		String contact = "510006";
		String content = "今晚约吗？";
		Integer join_num = 10;
		MovieActivity movieActivity = new MovieActivity(dateTime, place, content, longitude, latitude, contact, join_num);
		movieActivity.setMovieDescription(movieDescription);
		movieActivity.setLauncher(user);
		movieActivityRepository.save(movieActivity);
    }
    
    public void joinActivity() throws Exception {
    	MovieActivity movieActivity = movieActivityRepository.findMovieActivityById(1);
		logger.info(String.valueOf(movieActivity.getParticipants().size()));
    	User user = userRepository.findUserById(1);
		User user1 = userRepository.findUserById(2);
		
    	logger.info(String.valueOf(user1.getJoins().size()));
    	logger.info(String.valueOf(user1.getJoins().contains(movieActivity)));
		
    	user.getJoins().add(movieActivity);
		user1.getJoins().add(movieActivity);
		
		/*Set<User> t = movieActivity.getParticipants();
		t.remove(user);
		movieActivity.setParticipants(t);*/
		
		//movieActivity.getParticipants().remove(user);
		//movieActivityRepository.save(movieActivity);
		userRepository.save(user);
		userRepository.save(user1);
    }
    
}
