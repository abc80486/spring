package com.neo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.model.MinuteData;
import com.neo.model.User;
import com.neo.services.MinuteDataService;
import com.neo.mapper.MinuteDataMapper;
import com.neo.mapper.UserMapper;

@RestController
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
    
    @Autowired
    private MinuteDataService mds;

    @RequestMapping("/getByTimeNum")
    public  List<MinuteData> getByTimeNum(long time , int num) {
        return mds.getByTimeNum(time, num);
    }


	@RequestMapping("/getUsers")
	public List<User> getUsers() {
		List<User> users=userMapper.getAll();
		return users;
	}
	
    @RequestMapping("/getUser")
    public User getUser(Long id) {
    	User user=userMapper.getOne(id);
        return user;
    }
    
    @RequestMapping("/add")
    public void save(User user,BindingResult result) {
    	userMapper.insert(user);
    }
    
    @RequestMapping(value="update")
    public void update(User user) {
    	userMapper.update(user);
    }
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
    	userMapper.delete(id);
    }
    
    
}