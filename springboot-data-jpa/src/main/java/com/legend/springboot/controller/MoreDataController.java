package com.legend.springboot.controller;

import com.legend.springboot.entity.MoreData;
import com.legend.springboot.repository.MoreDataRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/moredata")
@RestController
public class MoreDataController {

    @Autowired
    private MoreDataRepository moreDataRepository;

    @GetMapping(value = "/queryById/{id}")
    public MoreData query(@PathVariable(value = "id") Integer id) {
        MoreData moreData = moreDataRepository.getOne(id);
        return moreData;
    }

    @PostMapping(value = "/saveOrUpdate")
    public MoreData save(@RequestBody MoreData moreData) {
        MoreData save = new MoreData();

        Integer id = moreData.getId();
        if (id == null) {
            save = moreDataRepository.save(moreData);
        } else {
            //更新
            MoreData moreData1 = moreDataRepository.findById(id).get();
            if (moreData1 == null) {
                return null;
            }
            BeanUtils.copyProperties(moreData, moreData1);
            save = moreDataRepository.save(moreData1);
        }
        return save;
    }

    @GetMapping(value = "/list")
    public List<MoreData> list() {
        return moreDataRepository.findAll();
    }

    @GetMapping(value = "/deleteById/{id}")
    public boolean update(@PathVariable(value = "id") Integer id) {
        moreDataRepository.deleteById(id);
        return true;
    }


}
