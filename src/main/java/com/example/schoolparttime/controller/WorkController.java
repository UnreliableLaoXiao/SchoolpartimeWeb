package com.example.schoolparttime.controller;

import com.example.schoolparttime.entity.*;
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

    @RequestMapping("/work/setliketype")
    @ResponseBody
    public ResultModel<String> setLikeType(long id,int type1,int type2,int type3){
        int rows;
        List query = jdbcTemplate.query("select * from t_user_likework where user_id = ?", new Object[]{id}, new BeanPropertyRowMapper(UserLikeWorkType.class));
        if (query.size() > 0){
            rows = jdbcTemplate.update("update t_user_likework set like_type_1 = ?,like_type_2 = ?,like_type_3 = ? where user_id = ?",type1,type2,type3, id);
        }else {
            rows = jdbcTemplate.update("insert into t_user_likework VALUES (0,?,?,?,?)", id ,type1,type2,type3);
        }

        if (rows > 0)
            return new ResultModel<String>("修改成功","","json",200);
        else
            return new ResultModel<String>("修改失败","","json",300);
    }

    @RequestMapping("/work/searchtitle")
    @ResponseBody
    public ResultModel<ArrayList<WorkInfo>> getTitleSearch(String title){
        ArrayList<WorkInfo> query = (ArrayList<WorkInfo>) jdbcTemplate.query("select * from t_work_info where work_title like ?", new Object[]{"%"+title+"%"}, new BeanPropertyRowMapper(WorkInfo.class));
        if (query.size() > 0){
            return new ResultModel<ArrayList<WorkInfo>>("搜索成功",query,"json",200);
        }else {
            return new ResultModel<ArrayList<WorkInfo>>("搜索失败",query,"json",300);
        }
    }
    @RequestMapping("/work/search")
    @ResponseBody
    public ResultModel<ArrayList<WorkInfo>> getSearch(String city,int work_type_id){
        System.out.println(" city = " + city + ", work_type_id = " + work_type_id);

        ArrayList<WorkInfo> query;

        if (!"".equals(city) && work_type_id != 0){
            System.out.println("city = " + city + " work_type_id = " + work_type_id);
            query = (ArrayList<WorkInfo>) jdbcTemplate.query("select * from t_work_info where city = ? and work_type_id = ? and work_statu = 0",
                    new Object[]{city,work_type_id}, new BeanPropertyRowMapper(WorkInfo.class));
        }else if ("".equals(city) && work_type_id != 0) {
            System.out.println("city = " + city + " work_type_id = " + work_type_id);
            query = (ArrayList<WorkInfo>) jdbcTemplate.query("select * from t_work_info where work_type_id = ? and work_statu = 0",
                    new Object[]{work_type_id}, new BeanPropertyRowMapper(WorkInfo.class));
        }else if (!"".equals(city) && work_type_id == 0){
            System.out.println("city = " + city + " work_type_id = " + work_type_id);
            query = (ArrayList<WorkInfo>) jdbcTemplate.query("select * from t_work_info where city = ? and work_statu = 0",
                    new Object[]{city}, new BeanPropertyRowMapper(WorkInfo.class));
        }else {
            System.out.println("city = " + city + " work_type_id = " + work_type_id);
            query = (ArrayList<WorkInfo>) jdbcTemplate.query(
                    "select * from t_work_info where work_statu = 0",new BeanPropertyRowMapper(WorkInfo.class));
        }

        System.out.println("size = " + query.size());

        if (query.size() > 0){
            return new ResultModel<ArrayList<WorkInfo>>("搜索成功",query,"json",200);
        }else {
            return new ResultModel<ArrayList<WorkInfo>>("搜索失败",query,"json",300);
        }
    }


    @RequestMapping("/work/collectwork")
    @ResponseBody
    public ResultModel<String> setCollect( long userid, long workid ,boolean like ){
        int rows;
        if (like){
            rows = jdbcTemplate.update("insert into t_user_collect VALUES (?,?,?)",0,userid,workid);
        }else {
            rows = jdbcTemplate.update("delete from t_user_collect where user_id = ? and work_id = ?",userid,workid);
        }
        if (rows > 0){
            return new ResultModel<String>("操作成功","","json",200);
        }else {
            return new ResultModel<String>("操作失败","","json",300);
        }
    }

    @RequestMapping("/work/getcollectwork")
    @ResponseBody
    public ResultModel<ArrayList<UserCollect>> getCollect( long userid){
        ArrayList<UserCollect> query;
        query = (ArrayList<UserCollect>) jdbcTemplate.query("select * from t_user_collect where user_id = ?",
                new Object[]{userid}, new BeanPropertyRowMapper(UserCollect.class));
        if (query.size() > 0){
            return new ResultModel<ArrayList<UserCollect>>("得到收藏兼职",query,"json",200);
        }else {
            return new ResultModel<ArrayList<UserCollect>>("得到收藏兼职",query,"json",300);
        }

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
