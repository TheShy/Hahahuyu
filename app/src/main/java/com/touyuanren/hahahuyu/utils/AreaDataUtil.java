package com.touyuanren.hahahuyu.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 全国省份城市操作类
 * 城市数据类
 */
public class AreaDataUtil {
    /**
     * 所有的省市String
     */

    //public final String AREAS1 = "北京&&东城区&西城区&崇文区&宣武区&朝阳区&丰台区&石景山区&海淀区&门头沟区&房山区&通州区&顺义区&昌平区&大兴区&怀柔区&平谷区&密云县&延庆县&&&天津&&和平区&河东区&河西区&南开区&河北区&红桥区&塘沽区&汉沽区&大港区&东丽区&西青区&津南区&北辰区&武清区&宝坻区&宁河县&静海县&蓟县&&&河北&&石家庄&唐山&秦皇岛&邯郸&邢台&保定&张家口&承德&沧州&廊坊&衡水&&&山西&&太原&大同&阳泉&长治&晋城&朔州&晋中&运城&忻州&临汾&吕梁&&&内蒙古&&呼和浩特&包头&乌海&赤峰&通辽&鄂尔多斯&呼伦贝尔&兴安盟&锡林郭勒盟&乌兰察布盟&巴彦淖尔盟&阿拉善盟&&&辽宁&&沈阳&大连&鞍山&抚顺&本溪&丹东&锦州&营口&阜新&辽阳&盘锦&铁岭&朝阳&葫芦岛&&&吉林&&长春&吉林&四平&辽源&通化&白山&松原&白城&延边朝鲜族自治州&&&黑龙江&&哈尔滨&齐齐哈尔&鸡西&鹤岗&双鸭山&大庆&伊春&佳木斯&七台河&牡丹江&黑河&绥化&大兴安岭&&&上海&&黄浦区&卢湾区&徐汇区&长宁区&静安区&普陀区&闸北区&虹口区&杨浦区&闵行区&宝山区&嘉定区&浦东新区&金山区&松江区&青浦区&南汇区&奉贤区&崇明县&&&江苏&&南京&无锡&徐州&常州&苏州&南通&连云港&淮安&盐城&扬州&镇江&泰州&宿迁&&&浙江&&杭州&宁波&温州&嘉兴&湖州&绍兴&金华&衢州&舟山&台州&丽水&&&安徽&&合肥&芜湖&蚌埠&淮南&马鞍山&淮北&铜陵&安庆&黄山&滁州&阜阳&宿州&巢湖&六安&亳州&池州&宣城&&&福建&&福州&厦门&莆田&三明&泉州&漳州&南平&龙岩&宁德&&&江西&&南昌&景德镇&萍乡&九江&新余&鹰潭&赣州&吉安&宜春&抚州&上饶&&&山东&&济南&青岛&淄博&枣庄&东营&烟台&潍坊&济宁&泰安&威海&日照&莱芜&临沂&德州&聊城&滨州&菏泽&&&河南&&郑州&开封&洛阳&平顶山&安阳&鹤壁&新乡&焦作&濮阳&许昌&漯河&三门峡&南阳&商丘&信阳&周口&驻马店&&&湖北&&武汉&黄石&十堰&宜昌&襄樊&鄂州&荆门&孝感&荆州&黄冈&咸宁&随州&恩施土家族苗族自治州&&&湖南&&长沙&株洲&湘潭&衡阳&邵阳&岳阳&常德&张家界&益阳&郴州&永州&怀化&娄底&湘西土家族苗族自治州&&&广东&&广州&韶关&深圳&珠海&汕头&佛山&江门&湛江&茂名&肇庆&惠州&梅州&汕尾&河源&阳江&清远&东莞&中山&潮州&揭阳&云浮&&&广西&&南宁&柳州&桂林&梧州&北海&防城港&钦州&贵港&玉林&百色&贺州&河池&南宁&柳州&&&海南&&海口&三亚&其他&&&重庆&&万州区&涪陵区&渝中区&大渡口区&江北区&沙坪坝区&九龙坡区&南岸区&北碚区&万盛区&双桥区&渝北区&巴南区&黔江区&长寿区&綦江县&潼南县&铜梁县&大足县&荣昌县&璧山县&梁平县&城口县&丰都县&垫江县&武隆县&忠县&开县&云阳县&奉节县&巫山县&巫溪县&石柱土家族自治县&秀山土家族苗族自治县&酉阳土家族苗族自治县&彭水苗族土家族自治县&江津市&合川市&永川区&南川市&&&四川&&成都&自贡&攀枝花&泸州&德阳&绵阳&广元&遂宁&内江&乐山&南充&眉山&宜宾&广安&达州&雅安&巴中&资阳&阿坝&甘孜&凉山&&&贵州&&贵阳&六盘水&遵义&安顺&铜仁&黔西南&毕节&黔东南&黔南&&&云南&&昆明&曲靖&玉溪&保山&昭通&楚雄&红河&文山&思茅&西双版纳&大理&德宏&丽江&怒江&迪庆&临沧&&&西藏&&拉萨&昌都&山南&日喀则&那曲&阿里&林芝&&&陕西&&西安&铜川&宝鸡&咸阳&渭南&延安&汉中&榆林&安康&商洛&&&甘肃&&兰州&嘉峪关&金昌&白银&天水&武威&张掖&平凉&酒泉&庆阳&定西&陇南&临夏&甘南&&&青海&&西宁&海东&海北&黄南&海南&果洛&玉树&海西&&&宁夏&&银川&石嘴山&吴忠&固原&&&新疆&&乌鲁木齐&克拉玛依&吐鲁番&哈密&昌吉&博尔塔拉&巴音郭楞&阿克苏&克孜勒苏&喀什&和田&伊犁&塔城&阿勒泰&&&台湾&&台北&高雄&&&香港&&香港&&&澳门&&澳门";
    public final String AREAS = "直辖市&&北京&上海&天津&重庆&&&特别行政区&&香港&澳门&&&河北省&&石家庄&唐山&秦皇岛&邯郸&邢台&保定&张家口&承德&沧州&廊坊&衡水&&&山西省&&太原&大同&阳泉&长治&晋城&朔州&晋中&运城&忻州&临汾&吕梁&&&内蒙古自治区&&呼和浩特&包头&乌海&赤峰&通辽&鄂尔多斯&呼伦贝尔&兴安盟&锡林郭勒盟&乌兰察布盟&巴彦淖尔盟&阿拉善盟&&&辽宁省&&沈阳&大连&鞍山&抚顺&本溪&丹东&锦州&营口&阜新&辽阳&盘锦&铁岭&朝阳&葫芦岛&&&吉林省&&长春&吉林&四平&辽源&通化&白山&松原&白城&延边朝鲜族自治州&&&黑龙江省&&哈尔滨&齐齐哈尔&鸡西&鹤岗&双鸭山&大庆&伊春&佳木斯&七台河&牡丹江&黑河&绥化&大兴安岭&&&江苏省&&南京&无锡&徐州&常州&苏州&南通&连云港&淮安&盐城&扬州&镇江&泰州&宿迁&&&浙江省&&杭州&宁波&温州&嘉兴&湖州&绍兴&金华&衢州&舟山&台州&丽水&&&安徽省&&合肥&芜湖&蚌埠&淮南&马鞍山&淮北&铜陵&安庆&黄山&滁州&阜阳&宿州&巢湖&六安&亳州&池州&宣城&&&福建省&&福州&厦门&莆田&三明&泉州&漳州&南平&龙岩&宁德&&&江西省&&南昌&景德镇&萍乡&九江&新余&鹰潭&赣州&吉安&宜春&抚州&上饶&&&山东省&&济南&青岛&淄博&枣庄&东营&烟台&潍坊&济宁&泰安&威海&日照&莱芜&临沂&德州&聊城&滨州&菏泽&&&河南省&&郑州&开封&洛阳&平顶山&安阳&鹤壁&新乡&焦作&濮阳&许昌&漯河&三门峡&南阳&商丘&信阳&周口&驻马店&&&湖北省&&武汉&黄石&十堰&宜昌&襄樊&鄂州&荆门&孝感&荆州&黄冈&咸宁&随州&恩施土家族苗族自治州&&&湖南省&&长沙&株洲&湘潭&衡阳&邵阳&岳阳&常德&张家界&益阳&郴州&永州&怀化&娄底&湘西土家族苗族自治州&&&广东省&&广州&韶关&深圳&珠海&汕头&佛山&江门&湛江&茂名&肇庆&惠州&梅州&汕尾&河源&阳江&清远&东莞&中山&潮州&揭阳&云浮&&&广西自治区&&南宁&柳州&桂林&梧州&北海&防城港&钦州&贵港&玉林&百色&贺州&河池&南宁&柳州&&&海南省&&海口&三亚&&&四川省&&成都&自贡&攀枝花&泸州&德阳&绵阳&广元&遂宁&内江&乐山&南充&眉山&宜宾&广安&达州&雅安&巴中&资阳&阿坝&甘孜&凉山&&&贵州省&&贵阳&六盘水&遵义&安顺&铜仁&黔西南&毕节&黔东南&黔南&&&云南省&&昆明&曲靖&玉溪&保山&昭通&楚雄&红河&文山&思茅&西双版纳&大理&德宏&丽江&怒江&迪庆&临沧&&&西藏自治区&&拉萨&昌都&山南&日喀则&那曲&阿里&林芝&&&陕西省&&西安&铜川&宝鸡&咸阳&渭南&延安&汉中&榆林&安康&商洛&&&甘肃省&&兰州&嘉峪关&金昌&白银&天水&武威&张掖&平凉&酒泉&庆阳&定西&陇南&临夏&甘南&&&青海省&&西宁&海东&海北&黄南&海南&果洛&玉树&海西&&&宁夏自治区&&银川&石嘴山&吴忠&固原&&&新疆自治区&&乌鲁木齐&克拉玛依&吐鲁番&哈密&昌吉&博尔塔拉&巴音郭楞&阿克苏&克孜勒苏&喀什&和田&伊犁&塔城&阿勒泰&&&台湾省&&台北&高雄";

    /**
     * 一个省份对应多个城市
     */
    private String[] single_province_city;

    /**
     * 全国省市Map key:省份 |Value:城市集合
     */
    private HashMap<String, List<String>> mCityMap = new HashMap<String, List<String>>();

    public AreaDataUtil() {
        splitProvince();
        getAllCityMap();
    }

    /**
     * 将省份和对应城市分割出来
     * <p/>
     * 得到：宁夏&&银川&石嘴山&吴忠&固原
     */
    private void splitProvince() {
        single_province_city = AREAS.split("&&&");
    }

    /**
     * 获得全国省份的列表
     *
     * @return 全国省份的列表
     */
    public ArrayList<String> getProvinces() {
        ArrayList<String> provinceList = new ArrayList<String>();
        for (String str : single_province_city) {
            String province = str.split("&&")[0];
            provinceList.add(province);
        }
        return provinceList;
    }

    /**
     * 根据省份获取城市列表
     *
     * @return 城市列表
     */
    private void getAllCityMap() {
        for (String str : single_province_city) {
            // 得到省份
            String province = str.split("&&")[0];
            // 得到当前省份对应的城市
            String citys = str.split("&&")[1];
            // 分离城市放入集合
            List<String> cityList = Arrays.asList(citys.split("&"));

            // 省份和城市放入Map中
            mCityMap.put(province, cityList);

        }
    }

    /**
     * 根据省份查找对应的城市列表
     *
     * @return 城市集合
     */
    public ArrayList<String> getCitysByProvince(String provinceStr) {

        List<String> list = mCityMap.get(provinceStr);
        ArrayList<String> arrList = new ArrayList<String>();
        for (String city : list) {
            arrList.add(city);
        }
        return arrList;
    }

}