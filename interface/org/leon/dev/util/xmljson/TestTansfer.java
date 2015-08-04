package org.leon.dev.util.xmljson;

import java.util.ArrayList;

/**
 * Created by zealer on 15/6/21.
 */
public class TestTansfer {

    public static void main(String args[]) throws Exception {
        ArrayList<TestPerson> tps = new ArrayList<>();
        TestPerson tp = new TestPerson();
        ArrayList list = new ArrayList();
        list.add("篮球");
        list.add("足球");
        tp.setAge(10);
        tp.setGender("M");
        tp.setName("LeonWong");
        tp.setTalent(list);
        TestPerson tp2 = new TestPerson();
        tp2.setAge(10);
        tp2.setGender("M");
        tp2.setName("LeonWong2");
        tps.add(tp);
        tps.add(tp2);
        ArrayList<TestPerson> none = new ArrayList<>();
        TestPerson tp3 = new TestPerson();
//        System.out.println(XmlJson.object2JSON(tp3));
//        System.out.println(XmlJson.object2XML(tp3));
        System.out.println(XmlJson.list2XML(tps));
//        System.out.println(XmlJson.list2JSON(tps));
//        String resp = XmlJson.object2JSON(tp);
        String resp = XmlJson.object2XML(tp);
//        System.out.println(resp);
//        JSONObject body = JSONObject.fromObject("dasda");
//        System.out.println(body);
//        JSONObject object = new JSONObject();
//        object.put("status",200);
//        object.put(body.keySet().iterator().next().toString(),body.get(body.keySet().iterator().next().toString()));
//        System.out.println(object);
//        Document doc = DocumentHelper.parseText(resp);
//        Element element = doc.getRootElement();
//        System.out.println(element.asXML());
//        Document doc2 = DocumentHelper.createDocument(DocumentHelper.createElement("ROOT"));
//        doc2.getRootElement().setDocument(doc);
//        System.out.println(doc2.asXML());
    }
}
