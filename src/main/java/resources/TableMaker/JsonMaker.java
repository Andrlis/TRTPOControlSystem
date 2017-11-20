package resources.TableMaker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.Student;
import data.group.SubGroup;
import data.mark.LabMark;
import data.mark.TestMark;

import java.util.*;

public class JsonMaker {
    public static String getJsonSubGroupMarks(SubGroup subGroup){
        if(subGroup == null)
            return null;

        ArrayList<Map<String,Object>> studentArray = new ArrayList<Map<String, Object>>();
        for(Student currentStudent: subGroup.getStudentsList()) {
            studentArray.add(getStudentMap(currentStudent));
        }

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("table-class", "mark-class");

        if(!studentArray.isEmpty()) {
            map.put("header", studentArray.get(0).keySet());
        }else {
            map.put("header", "");
        }

        map.put("args", studentArray);


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Student.class, new StudentConverter());
        builder.registerTypeAdapter(LabMark.class, new LabMarkConverter());
        builder.registerTypeAdapter(TestMark.class, new TestMarkConverter());
        builder.registerTypeAdapter(BonusMark.class, new BonusMarkConverter());
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.toJson(map);
    }

    private static Map<String,Object> getStudentMap(Student student){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("student", student);

        for(LabMark labMark : student.getLabMarkList()) {
            String labNum = new String("lab" + labMark.getIssuedLab().getLabDescription().getNumberOfLab());

            map.put(labNum, labMark);
        }

        for(TestMark testMark : student.getTestMarkList()) {
            String testNum = new String("test" + testMark.getTest().getTestNumber());

            map.put(testNum, testMark);
        }

        map.put("bonus", new BonusMark(student));

        return map;
    }
}