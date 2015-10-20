package com.berlizov.dataorgua.tabs;

import android.support.v4.app.Fragment;

import com.berlizov.dataorgua.JSONTable;
import com.berlizov.dataorgua.R;

/**
 * Created by 350z6_000 on 18.10.2015.
 * интерфейс наших табов
 */
public abstract class TableReaderFragment extends Fragment {
	/**
	* тут идет вызов установки JSONTable в табу, типо 
	*/
    public abstract void setTable(JSONTable table);
    /**
    * проверяем нужна ли эта таба для этих данных
    * табличная таба всегда нужна, а вот таба карты только коглда есть координаты
    */
    public abstract boolean can(JSONTable table);
    /**
    * тут берем id имени табы для тоборажения заголовка таба, типо ЭКартаЭ
    */
    public abstract int getIdName();
}
