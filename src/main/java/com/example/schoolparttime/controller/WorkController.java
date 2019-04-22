package com.example.schoolparttime.controller;

import com.example.schoolparttime.entity.City;
import com.example.schoolparttime.entity.WorkInfo;
import com.example.schoolparttime.entity.WorkType;
import com.example.schoolparttime.entity.base.ResultModel;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WorkController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Gson gson = new Gson();



    @RequestMapping("/work/allwork")
    @ResponseBody
    public ResultModel<ArrayList<WorkInfo>> allwork(int times){
        int offset = 20 +10*(times-1);
        ArrayList<WorkInfo> userList;
        if (times == 0) {
            userList= (ArrayList<WorkInfo>) jdbcTemplate.query(
                    "select * from t_work_info where work_statu = 0 limit 20  ",new BeanPropertyRowMapper(WorkInfo.class));
        }else {
            userList= (ArrayList<WorkInfo>) jdbcTemplate.query("select * from t_work_info where work_statu = 0 limit 10 offset ? ",
                    new Object[]{ offset},new BeanPropertyRowMapper(WorkInfo.class));
        }
        return new ResultModel<ArrayList<WorkInfo>>("得到兼职信息",userList,"json",200);
    }

    @RequestMapping("/work/likework")
    @ResponseBody
    public ResultModel<ArrayList<WorkInfo>> likework(long id){
        ArrayList<WorkInfo> userList;
        if (id == 0){
            userList = (ArrayList<WorkInfo>) jdbcTemplate.query("select * from t_work_info where work_statu = 0 limit 10" ,new BeanPropertyRowMapper(WorkInfo.class));
        }else {
            userList = (ArrayList<WorkInfo>) jdbcTemplate.query("select * from t_work_info where work_statu = 0 limit 10",new BeanPropertyRowMapper(WorkInfo.class));
        }
        return new ResultModel<ArrayList<WorkInfo>>("得到推荐兼职信息",userList,"json",200);
    }

    @RequestMapping("/work/worktype")
    @ResponseBody
    public ResultModel<ArrayList<WorkType>> getWorktype(){
        ArrayList<WorkType> workTypes;
        workTypes = (ArrayList<WorkType>) jdbcTemplate.query("select * from t_work_type",new BeanPropertyRowMapper(WorkType.class));
        return new ResultModel<ArrayList<WorkType>>("得到所有兼职类型",workTypes,"json",200);
    }

    @RequestMapping("/work/mysendwork")
    @ResponseBody
    public ResultModel<ArrayList<WorkInfo>> getMySendWorks(long id){
        ArrayList<WorkInfo> workInfos;
        workInfos = (ArrayList<WorkInfo>) jdbcTemplate.query("select * from t_work_info where boss_id = ?",new Object[]{ id},new BeanPropertyRowMapper(WorkInfo.class));
        return new ResultModel<ArrayList<WorkInfo>>("得到所有我发布的兼职",workInfos,"json",200);
    }

    @RequestMapping("/work/citys")
    @ResponseBody
    public ResultModel<ArrayList<City>> getCitys(){
        ArrayList<City> cities;
        cities = (ArrayList<City>) jdbcTemplate.query("select * from t_city",new BeanPropertyRowMapper(City.class));
        return new ResultModel<ArrayList<City>>("得到城市列表",cities,"json",200);
    }

    @RequestMapping("/work/newwork")
    @ResponseBody
    public ResultModel<String> getMySendWorks(String data){
        System.out.println(data);

        WorkInfo workInfo = gson.fromJson(data,WorkInfo.class);
        int rows = jdbcTemplate.update("insert into t_work_info VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                workInfo.getId(),workInfo.getBossId(),workInfo.getWorkTypeId(),workInfo.getWorkTitle(),workInfo.getMoney(),
                workInfo.getCreateTime(),workInfo.getEnd_way(),workInfo.getWorkContext(),workInfo.getAddress(),workInfo.getCity(),
                workInfo.getContacts(),workInfo.getContactsWay(),workInfo.getWorkStatu());
        List query = jdbcTemplate.query("select * from t_city where city_name = ?", new Object[]{workInfo.getCity()}, new BeanPropertyRowMapper(City.class));
        if (query.size() == 0 || query == null)
            jdbcTemplate.update("insert into t_city VALUES (?,?)", 0,workInfo.getCity());
        if (rows > 0)
            return new ResultModel<String>("发布成功","","json",200);
        else
            return new ResultModel<String>("发布失败","","json",300);
    }




    @RequestMapping("/work/soldoutwork")
    @ResponseBody
    public ResultModel<String> soldWorkInfo(long id){
        int rows = jdbcTemplate.update("update t_work_info set work_statu = 1 where id = ?", id);
        if (rows > 0)
            return new ResultModel<String>("下架成功","","json",200);
        else
            return new ResultModel<String>("下架失败","","json",300);
    }


    /**
     * 数据库测试代码
     * @return
     */
    @RequestMapping("/work/alllist")
    @ResponseBody
    public List<WorkInfo> list(){
        List<WorkInfo> userList = jdbcTemplate.query("select * from t_work_info",new BeanPropertyRowMapper(WorkInfo.class));
        return userList;
    }

}