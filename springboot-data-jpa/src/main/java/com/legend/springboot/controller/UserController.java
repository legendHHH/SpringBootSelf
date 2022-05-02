package com.legend.springboot.controller;

import com.legend.springboot.entity.User;
import com.legend.springboot.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/queryById/{id}")
    public User query(@PathVariable(value = "id") Integer id) {
        User user = userRepository.getOne(id);
        return user;
    }

    @PostMapping(value = "/saveOrUpdate")
    public User save(User user) {
        User save = new User();

        Integer id = user.getId();
        if (id == null) {
            save = userRepository.save(user);
        } else {
            //更新
            User user1 = userRepository.findById(id).get();
            if (user1 == null) {
                return null;
            }
            BeanUtils.copyProperties(user, user1);
            save = userRepository.save(user1);
        }
        return save;
    }


    @GetMapping(value = "/deleteById/{id}")
    public boolean update(@PathVariable(value = "id") Integer id) {
        userRepository.deleteById(id);
        return true;
    }


}
