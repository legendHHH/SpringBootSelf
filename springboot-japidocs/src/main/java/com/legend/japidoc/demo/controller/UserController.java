package com.legend.japidoc.demo.controller;

import com.legend.japidoc.demo.domain.UserListForm;
import com.legend.japidoc.demo.domain.UserVO;
import org.springframework.web.bind.annotation.*;

/**
 * User API
 * @author legend
 */
@RequestMapping("/api/user/")
@RestController
public class UserController {


    /**
     * Get User List
     * @param listForm
     */
    @RequestMapping(path = "list", method = {RequestMethod.GET,  RequestMethod.POST}  )
    public ApiResult<PageResult<UserVO>> list(UserListForm listForm){
        return null;
    }

    /**
     * Save User
     * @param userForm
     */
    @PostMapping(path = "save")
    public ApiResult<UserVO> saveUser(@RequestBody UserForm userForm){
        return null;
    }

    /**
     * Delete User
     * @param userId user id
     */
    @PostMapping("delete")
    public ApiResult deleteUser(@RequestParam Long userId){
        return null;
    }
}