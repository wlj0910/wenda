package com.nowcoder;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.Question;
import com.nowcoder.model.User;
import com.nowcoder.service.FollowService;
import com.nowcoder.service.SensitiveService;
import com.nowcoder.service.WendaService;
import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.WendaUtil;
import org.apache.ibatis.annotations.Param;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {
	@Autowired
	UserDAO userDAO;
	@Autowired
	QuestionDAO questionDAO;

	@Autowired
	FollowService followService;

	@Autowired
	SensitiveService sensitiveService;

	@Autowired
	JedisAdapter jedisAdapter;


	@Test
	public void initDatabase() {
		Random random=new Random();
		jedisAdapter.getJedis().flushDB();
		for(int i=0;i<11;i++){
			User user=new User();
			//user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
			user.setHeadUrl(String.format("image%d",i));
			user.setName(String.format("USER%d",i));
			user.setPassword("");
			//user.setSalt("");
			user.setSalt(UUID.randomUUID().toString().substring(0,5));
			userDAO.addUser(user);
			//user.setPassword("WWW");
			//userDAO.updatePassword(user);
			//userDAO.updateSalt(String.format("wlj%d",i),i+1);

			for(int j=1;j<i;j++){
				followService.follow(j, EntityType.ENTITY_USER,i);
			}

			//user.setSalt(UUID.randomUUID().toString().substring(0,5));
			user.setPassword(WendaUtil.MD5("123"+user.getSalt()));

			userDAO.updatePassword(user);

			Question question=new Question();
			question.setCommentCount(i);
			Date date=new Date();
			date.setTime(date.getTime()+1000*3600*i);
			question.setCreatedDate(date);
			question.setUserId(i+1);
			question.setTitle(String.format("TITLE{%d}",i));
			question.setContent(String.format("hello wlj%d",i));

			questionDAO.addQuestion(question);
		}
		//Assert.assertEquals("newpassword",userDAO.selectById(1).getPassword());
		//userDAO.deleteById(1);
		//Assert.assertNull(userDAO.selectById(1));
		/*for(Question q:questionDAO.selectLatestQuestions(0,0,5)){
			System.out.println(q.getUserId());
		}*/
		//System.out.print(questionDAO.selectLatestQuestions(0,0,10));
	}

	@Test
	public void testSensitive(){
		String content="你好赌博";
		String result=sensitiveService.filter(content);
		System.out.println(result);
	}

}
