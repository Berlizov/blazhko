package com.berlizov.dataorgua.API;

import com.berlizov.dataorgua.Activities.GroupActivity;
import com.berlizov.dataorgua.Group;
import com.berlizov.dataorgua.Info;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoadData extends API<Group> {
    GroupActivity parent;
    String groupName;

    public LoadData(GroupActivity parent) {
        super(parent.getBaseContext());
        this.parent = parent;
    }

    @Override
    protected Group calcResult(String result) throws Exception {
        Group group = new Group();
        JSONObject obj = new JSONObject(result);
        JSONArray resultObj = obj.getJSONArray("result");
        groupName=toUa(groupName);
        for(int i=0;i<resultObj.length();i++) {
            JSONObject tempobj =resultObj.getJSONObject(i);
            Info info = new Info();
            info.setID(tempobj.getJSONArray("resources").getJSONObject(0).getString("id"));
            info.setName(tempobj.getString("title"));
            info.setCompany(tempobj.getString("maintainer"));
            info.setDate(tempobj.getString("metadata_modified"));
            JSONArray groups = tempobj.getJSONArray("groups");
            for(int j=0;j<groups.length();j++) {
                JSONObject tempGroup = groups.getJSONObject(j);
                String title = tempGroup.getString("title").toLowerCase();
                if(title.charAt(0)==' '){
                    title=title.substring(1);
                }
                title.trim();
                if(title.equalsIgnoreCase(groupName)){
                    group.setImageDisplayUrl(tempGroup.getString("image_display_url"));
                    group.setName(tempGroup.getString("title"));
                    group.setDescription(tempGroup.getString("display_name"));
                }
            }
            group.add(info);
        }
        return group;
    }

    public String toUa(String str){
        StringBuilder sb = new StringBuilder();
        str=str.toLowerCase();
        for (int i=0;i<str.length();i++) {
            switch (str.charAt(i)) {
                case 'a':sb.append('а');break;
                case 'b':sb.append('б');break;
                case 'c':
                    if(i+1!=str.length()&&str.charAt(i + 1)=='h'){
                        sb.append('ч');
                        i++;
                    }else {
                        sb.append('ц');
                    }
                    break;
                case 'd':sb.append('д');break;
                case 'e':sb.append('е');break;
                case 'f':sb.append('ф');break;
                case 'g':sb.append('г');break;
                case 'h':sb.append('х');break;
                case 'i':sb.append('і');break;
                case 'k':sb.append('к');break;
                case 'l':
                    if(i+1!=str.length()&&(str.charAt(i+1)=='n'||str.charAt(i+1)=='s')){
                        sb.append("ль");
                    }else {
                        sb.append('л');
                    }break;
                case 'm':sb.append('м');break;
                case 'n':sb.append('н');break;
                case 'o':sb.append('о');break;
                case 'p':sb.append('п');break;
                case 'q':sb.append('?');break;
                case 'r':sb.append('р');break;
                case 's':
                    if(i+1!=str.length()&&str.charAt(i + 1)=='k'){
                        sb.append("сь");
                    }else {
                        sb.append('с');
                    }break;
                case 't':sb.append('т');
                    if(i+1==str.length()&&i-1!=0&&str.charAt(i - 1)=='s')sb.append('ь');break;
                case 'v':sb.append('в');break;
                case 'x':sb.append('!');break;
                case 'y':
                    if(i+1!=str.length()&&str.charAt(i+1)=='a'){
                        if(i-1!=-1&&str.charAt(i-1)=='v'){
                            sb.append("&#039;");
                        }
                        sb.append("я");
                        i++;
                    }else {
                        sb.append('и');
                    }
                    break;
                case 'u':sb.append('у');break;
                case 'z':
                    if(i+1!=str.length()&&str.charAt(i+1)=='h'){
                        sb.append('ж');
                        i++;
                    }else {
                        sb.append('з');
                    }
                    break;
                case '-':sb.append(' ');break;
                default:sb.append(str.charAt(i));break;
            }
        }
        return sb.toString();
    }

    @Override
    protected String createQuery(String... params) {
        groupName = params[0];
        return "http://" + getSiteURI() + "/api/3/action/group_package_show?id="+groupName;
    }

    @Override
    protected void successLoad(Group s) {
        parent.successLoadGoupData(s);
    }

    @Override
    protected void ErrorLoad() {
        parent.setError();
    }
}
