package com.legend.springbootmybatis.controller;

import com.alibaba.fastjson.JSONObject;
import com.legend.springbootmybatis.domain.City;
import com.legend.springbootmybatis.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;

    private static Map<String, String> map = new HashMap<>(16);

    /**
     * Spring容器加载的时候初始化一部分数据
     */
    @PostConstruct
    public void init(){
        City cityById = cityService.findCityById(2L);
        map.put(cityById.getCityName(), cityById.getDescription());
    }

    /**
     * http://localhost:8083/api/city?cityName=889999998888
     *
     * @param cityName
     * @return
     */
    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        System.out.println(JSONObject.toJSONString(map));
        return cityService.findCityByName(cityName);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String test() {
        return "Hello World。。。。";
    }

    @RequestMapping(value = "/api/city6", method = RequestMethod.GET)
    public List<City> findSomeCity(City city) {
        city.setFlag(true);
        city.setIdList(Arrays.asList(3,4,5));
        return cityService.findSomeCity(city);
    }

    @RequestMapping(value = "/api/city/saveOrUpdate", method = RequestMethod.GET)
    public int saveOrUpdate(City city) {
        return cityService.saveOrUpdate(city);
    }


    @RequestMapping(value = "/api/city/batchSaveOrUpdate", method = RequestMethod.GET)
    public int batchSaveOrUpdate() {
        List<City> cityList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            City city = new City();
            city.setDescription("北京路" + i + i);
            city.setCityName("北京路" + i);
            city.setProvinceId(i + 10000L);
            city.setId(i + 10L);
            cityList.add(city);
        }
        return cityService.batchSaveOrUpdate(cityList);
    }

    @RequestMapping(value = "/api/city/batchInsert", method = RequestMethod.GET)
    public int batchInsert() {
        List<City> cityList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            City city = new City();
            city.setDescription("北京路" + i + i);
            city.setCityName("北京路" + i);
            city.setProvinceId(i + 10000L);
            city.setId(i + 10L);
            cityList.add(city);
        }
        cityService.batchInsert(cityList);
        System.out.println(cityList.stream().map(City::getId).collect(Collectors.toList()));
        return 1;
    }

    //异步线程池
    ExecutorService executor = new ThreadPoolExecutor(0, 5000,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    /**
     * 使用CompletableFuture 异步批量插入
     * http://localhost:8083/api/city/batchInsert2
     *
     * @return
     */
    @RequestMapping(value = "/api/city/batchInsert2", method = RequestMethod.GET)
    public int batchInsert2() {
        List<City> cityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            City city = new City();
            city.setDescription("CompletableFuture北京路步行街" + i + i);
            city.setCityName("CompletableFuture北京路步行街" + i);
            city.setProvinceId(i + 10000L);
            city.setId(i + 10L);
            cityList.add(city);
        }
        //异步线程插入
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000);
                cityService.batchInsert(cityList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }, executor);

        /*try {
            System.out.println("future:" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        System.out.println(cityList.stream().map(City::getId).collect(Collectors.toList()));
        return 1;
    }


    @GetMapping("/testSelect")
    public String method() {
        City city = new City();
        city.setId(5L);
        city.setCityName("889999998888");
        cityService.update(city);
        return cityService.testSelect(5L).toString();
    }

    /**
     * 测试更新后的数据返回
     *
     * @return
     */
    @GetMapping("/updateAfter")
    public String updateAfter() {
        City city = new City();
        city.setId(4L);
        city.setCityName("测试123456");
        city.setProvinceId(12L);
        return cityService.update2(city).toString();
    }

    /**
     * 批量更新第一种方式
     * http://localhost:8083/batchUpdate1
     *
     * @return
     */
    @GetMapping("/batchUpdate1")
    public String batchUpdate1() {
        System.out.println("hello");
        List<City> cityList = new ArrayList<>();
        City city = new City();
        city.setId(4L);
        city.setProvinceId(999L);
        city.setCityName("南京西路");

        City city2 = new City();
        city2.setId(5L);
        city2.setCityName("南京东路");

        cityList.add(city);
        cityList.add(city2);

        cityService.batchUpdate1(cityList);
        return "更新了 " + 0 + " 条数据";
    }

    /**
     * 批量更新第二种方式
     * http://localhost:8083/batchUpdate2
     *
     * @return
     */
    @GetMapping("/batchUpdate2")
    public String batchUpdate2() {
        List<City> cityList = new ArrayList<>();
        City city = new City();
        city.setId(4L);
        city.setProvinceId(999L);
        city.setCityName("南京1111西路");

        City city2 = new City();
        city2.setId(5L);
        city2.setCityName("南京22222东路");

        cityList.add(city);
        cityList.add(city2);

        cityService.batchUpdate2(cityList);
        return "更新了 " + 0 + " 条数据";
    }

    /**
     * 批量更新第二种方式
     * http://localhost:8083/batchUpdate3
     *
     * @return
     */
    @GetMapping("/batchUpdate3")
    public String batchUpdate3() {
        List<City> cityList = new ArrayList<>();
        City city = new City();
        city.setId(44L);
        city.setProvinceId(999L);
        city.setCityName("南京1111西路");

        City city2 = new City();
        city2.setId(55L);
        city2.setCityName("南京22222东路");

        cityList.add(city);
        cityList.add(city2);

        cityService.batchUpdate3(cityList);
        return "更新了 " + 0 + " 条数据";
    }

    public static void main(String[] args) {
        City city = new City();
        System.out.println(city.isFlag());
        city.setId(-1L);

        List<Integer> in = new ArrayList<>();
        System.out.println(Long.parseLong(String.valueOf(city.getId())));
        System.out.println(in.toString());
    }

    /**
     * request.getParameter +号变成空格
     * http://localhost:9000/uio?code=X33GFZhDSa+Dy9K9LHdaaA==
     * http://localhost:9000/uio?code=X33GFZ%2BhDSa%25Dy9K9LHdaa===7&a=1
     * http://localhost:9000/uio?code=X33GFZ%2BhDSa%25Dy9K9LHdaa7&a=1
     *
     * 有些符号在URL中是不能直接传递的，如果要在URL中传递这些特殊符号，那么就要使用他们的编码了。
     * 编码的格式为：%加字符的ASCII码，即一个百分号%，后面跟对应字符的ASCII（16进制）码值。例如 空格的编码值是"%20"。
     * 如果不使用转义字符，这些编码就会当URL中定义的特殊字符处理。
     * 下表中列出了一些URL特殊符号及编码 十六进制值
     * 1) + URL 中+号表示空格 %2B
     * 2) 空格 URL中的空格可以用+号或者编码 %20
     * 3) / 分隔目录和子目录 %2F
     * 4) ? 分隔实际的 URL 和参数 %3F
     * 5) % 指定特殊字符 %25
     * 6) # 表示书签 %23
     * 7) &amp; URL 中指定的参数间的分隔符 %26
     * 8) = URL 中指定参数的值 %3D
     *
     * @return
     */
    @GetMapping("/uio")
    public String dealRequestParam(HttpServletRequest request){
        String code = request.getParameter("code");
        String queryString = request.getQueryString();
        System.out.println(code);
        System.out.println(queryString);

        //特殊处理参数值
        Map<String, String> map = new HashMap<>(16);
        String[] split = queryString.split("&");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            String[] value = s.split("=");
            map.put(value[0], value[1]);
        }
        System.out.println("hello" + map.toString());
        return code;
    }
}
