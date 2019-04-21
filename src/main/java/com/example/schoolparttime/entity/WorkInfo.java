package com.example.schoolparttime.entity;


/**
 * 商家发布的兼职信息
 */
public class WorkInfo {
    private long id;   //工作信息编号
    private long bossId;   //商家编号，外键
    private int workTypeId;   //兼职类型
    private String workTitle;   //招聘信息标题
    private String money;   //薪水
    private String createTime;   //发布时间
    private String end_way;   //薪水结算方式
    private String workContext;   //工作职责描述
    private String address;   //详细地址
    private String city;   //所在城市(市)
    private String contacts;   //联系人
    private String contactsWay;   //联系人方式
    private int workStatu;  //兼职状态

    public WorkInfo(long id, long bossId, int workTypeId, String workTitle, String money, String createTime, String end_way, String workContext, String address, String city, String contacts, String contactsWay, int workStatu) {
        this.id = id;
        this.bossId = bossId;
        this.workTypeId = workTypeId;
        this.workTitle = workTitle;
        this.money = money;
        this.createTime = createTime;
        this.end_way = end_way;
        this.workContext = workContext;
        this.address = address;
        this.city = city;
        this.contacts = contacts;
        this.contactsWay = contactsWay;
        this.workStatu = workStatu;
    }

    public WorkInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBossId() {
        return bossId;
    }

    public void setBossId(long bossId) {
        this.bossId = bossId;
    }

    public int getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(int workTypeId) {
        this.workTypeId = workTypeId;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEnd_way() {
        return end_way;
    }

    public void setEnd_way(String end_way) {
        this.end_way = end_way;
    }

    public String getWorkContext() {
        return workContext;
    }

    public void setWorkContext(String workContext) {
        this.workContext = workContext;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsWay() {
        return contactsWay;
    }

    public void setContactsWay(String contactsWay) {
        this.contactsWay = contactsWay;
    }

    public int getWorkStatu() {
        return workStatu;
    }

    public void setWorkStatu(int workStatu) {
        this.workStatu = workStatu;
    }

    @Override
    public String toString() {
        return "WorkInfo{" +
                "id=" + id +
                ", bossId=" + bossId +
                ", workTypeId=" + workTypeId +
                ", workTitle='" + workTitle + '\'' +
                ", money='" + money + '\'' +
                ", createTime='" + createTime + '\'' +
                ", end_way='" + end_way + '\'' +
                ", workContext='" + workContext + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactsWay='" + contactsWay + '\'' +
                ", workStatu=" + workStatu +
                '}';
    }
}