package com.legend.springboot.controller;

import com.legend.springboot.entity.Goods;
import com.legend.springboot.repository.GoodsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/goods")
@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping(value = "/queryById/{id}")
    public Goods query(@PathVariable(value = "id") Integer id) {
        Goods good = goodsRepository.getOne(id);
        return good;
    }

    @PostMapping(value = "/saveOrUpdate")
    public Goods save(@RequestBody Goods good) {
        Goods save = new Goods();

        Integer id = good.getId();
        if (id == null) {
            save = goodsRepository.save(good);
        } else {
            //更新
            Goods goods = goodsRepository.findById(id).get();
            if (goods == null) {
                return null;
            }
            BeanUtils.copyProperties(good, goods);
            save = goodsRepository.save(goods);
        }
        return save;
    }

    @GetMapping(value = "/list")
    public List<Goods> list() {
        return goodsRepository.findAll();
    }

    @GetMapping(value = "/deleteById/{id}")
    public boolean update(@PathVariable(value = "id") Integer id) {
        goodsRepository.deleteById(id);
        return true;
    }


}
