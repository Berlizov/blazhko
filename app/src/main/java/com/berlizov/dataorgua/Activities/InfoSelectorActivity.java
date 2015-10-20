package com.berlizov.dataorgua.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.berlizov.dataorgua.Info;
import com.berlizov.dataorgua.InfoArrayAdapter;
import com.berlizov.dataorgua.R;

import java.util.ArrayList;
import java.util.List;
/**
* логика окна выбора набора данных
*/
public class InfoSelectorActivity extends AppCompatActivity {
/**
* типо класс раздела, я думал, что смогу из апи получать раздела и выводить тут только набора данных определенного раздела, типо Медицина и тд...
*/
    class Section{
        String name;
        public Section(String name) {
            this.name = name;
        }
    }

    Section section;
    List<Info> infos = new ArrayList();
    /**
    *   метод ,который срабатывает перед показом окна
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_selector);
        //загрузка инфо секции
        loadSection();
        //настройка окна
        setupActivity();
    }
    /**
    * настройка окна, в котором пока только устанавливаем заголовок из секции и загружаем данны
    */
    protected void setupActivity(){
        setTitle(section.name);
        //TODO Перенести в onStart
        loadInfoList();
    }
    /**
    * типо загрузка секции, но просто ее создание
    */
    protected void loadSection(){
        section = new Section("Наявні данні");
    }
    /**
    * тут должна быть загрузка списка наборов данных из сети, но захардкодил
    */
    protected void loadInfoList(){
        //находим нужные гуи
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarInfos);
        ListView listView = (ListView) findViewById(R.id.listViewInfos);
        // показываем крутяшуюся хрень пока идет загрузка
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
        // типо загрузка, но хардкод
        synchronized (infos){
            infos.clear();
            infos.add(new Info("9a747130-1c6b-46ba-ba99-9d8af72a6884", "Навчальні заклади", "Міністерство охорони здоровья України","21.04.2014"));
            infos.add(new Info("76bb2f6d-0ec3-489d-ab00-948456291986", "Державний реєстр небезпечних факторів", "Міністерство охорони здоровья України","21.04.2014"));
            infos.add(new Info("6568c2f1-d4e3-4e3f-8046-6963d9d30adc", "Дитячі лікувальні заклади", "Міністерство охорони здоровья України","21.04.2014"));
            infos.add(new Info("808db8ab-272c-4fab-95ff-4aa43ebcaaff", "Міністерство охорони здоровья України", "Міністерство охорони здоровья України", "21.04.2014"));
        }
        // перестаем крутить фигню и показываем загруженные наб данных
        progressBar.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(new InfoArrayAdapter(this, R.id.listViewInfos, infos));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                openInfo(position);
            }
        });
    }
    /**
    * метод при выборе наборов данных
    * тут отпрываеться новое окно с подробной инфой
    */
    public void openInfo(int index){
        // класс нового окна
        Intent intent = new Intent(this, ScrollingActivity.class);
        //в новое окно передаем обьект набора данных
        intent.putExtra(getString(R.string.info_id), infos.get(index));
        //я сказал стартуем
        startActivity(intent);
    }
}
